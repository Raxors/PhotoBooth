package com.raxors.photobooth.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.Image
import android.os.Build
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentCameraBinding
import com.raxors.photobooth.ui.main.MainFragmentViewModel
import com.raxors.photobooth.ui.main.Navigate
import com.raxors.photobooth.ui.photos.PhotoListFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment: BaseFragment<MainFragmentViewModel, FragmentCameraBinding>(
    FragmentCameraBinding::inflate
) {

    override val viewModel by activityViewModels<MainFragmentViewModel>()

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    private fun flipCamera() {
        if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) cameraSelector =
            CameraSelector.DEFAULT_BACK_CAMERA else if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) cameraSelector =
            CameraSelector.DEFAULT_FRONT_CAMERA
        startCamera()
    }

    override fun initView() {
        binding.let {
            it.btnFlipCamera.setOnClickListener {
                flipCamera()
            }
            /*it.btnPhotoList.setOnClickListener {
                val photosDialog = PhotoListFragment()
                photosDialog.show(parentFragmentManager, "TAG")
            }*/
        }

//        viewModel.getFriends()
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            navigate.observe(viewLifecycleOwner) {
                when (it) {
                    is Navigate.ToPhotoSend -> {
                        findNavController().navigate(R.id.photo_send_dest)
                    }
                }
                navigate.postValue(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetSending()
        // Request camera permissions
        if (allPermissionsGranted()) {
//            if (binding != null)
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listeners for take photo and video capture buttons
        binding.imageCaptureButton.setOnClickListener { takePhoto() }
//        viewBinding.videoCaptureButton.setOnClickListener { captureVideo() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            MyImageCapturedCallback()
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.photoView.surfaceProvider)
                }
            val viewport = binding.photoView.viewPort

            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(1000, 1000))
                .build()

            val useCaseGroup = viewport?.let {
                UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(imageCapture!!)
                    .setViewPort(it)
                    .build()
            }
            /*val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }*/

            try {
                cameraProvider.unbindAll()
                if (useCaseGroup != null) {
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, useCaseGroup)
                }

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }

    private inner class MyImageCapturedCallback(): OnImageCapturedCallback() {
        @SuppressLint("UnsafeOptInUsageError")
        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            val bytes = image.image?.toByteArray()
            bytes?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size).rotateBitmap(cameraSelector)
                val diff = (bitmap.height - bitmap.width) / 2
                val square = Bitmap.createBitmap(bitmap, 0, diff, bitmap.width, bitmap.width)
                val stream = ByteArrayOutputStream()
                square.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val byteArray = stream.toByteArray()
                val encodedImage: String = encodeToString(byteArray, Base64.NO_WRAP)
                viewModel.openPhotoSend(square, encodedImage)
            }
        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)

        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()

        fun Image.toByteArray(): ByteArray {
            val buffer = planes[0].buffer
            buffer.rewind()
            val bytes = ByteArray(buffer.capacity())
            buffer.get(bytes)
            return bytes
        }

        fun Bitmap.rotateBitmap(selector: CameraSelector): Bitmap {
            var degrees = -90f
            var isMirror = true
            if (selector != CameraSelector.DEFAULT_FRONT_CAMERA) {
                degrees = 90f
                isMirror = false
            }
            val matrix = Matrix()
            matrix.postRotate(degrees)
            val cx = this.width / 2f
            val cy = this.height / 2f
            if (isMirror) matrix.postScale(-1f, 1f, cx, cy)
            return Bitmap.createBitmap(
                this, 0, 0, this.width, this.height, matrix, true
            )
        }
    }

}