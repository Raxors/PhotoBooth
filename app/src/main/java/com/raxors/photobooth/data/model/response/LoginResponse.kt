package com.raxors.photobooth.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.raxors.photobooth.data.model.response.LoginResponse.Companion.getRefreshDate
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("tokenType")
    val tokenType: String,
    @SerializedName("expiresAt")
    val expiresAt: String
): Parcelable {

    companion object {
        fun LoginResponse.getRefreshDate(): Date {
            val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS", Locale.getDefault())
            return df.parse(expiresAt) as Date
        }
    }

}

val LoginResponse?.isValid: Boolean
    get() = this != null && accessToken.isNotBlank() && refreshToken.isNotBlank() && Calendar.getInstance().time.before(this.getRefreshDate())