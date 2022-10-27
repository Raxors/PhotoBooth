package com.raxors.photobooth.data.api

import com.raxors.photobooth.base.annotation.RequireAuthorization
import com.raxors.photobooth.data.model.request.AddFriendRequest
import com.raxors.photobooth.data.model.request.PhotoRequest
import com.raxors.photobooth.data.model.request.RemoveFriendRequest
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.data.model.response.ProfileResponse
import retrofit2.http.*

interface PhotoBoothApi {

    //Profile region
    @GET("profile")
    @RequireAuthorization
    suspend fun getProfile(): ProfileResponse

    @GET("profile/{userId}")
    @RequireAuthorization
    suspend fun getProfileById(@Path("userId") userId: String): ProfileResponse

    @GET("profile/search/{username}")
    @RequireAuthorization
    suspend fun searchUser(@Path("username") username: String): ProfileResponse

    //Friends region
    @POST("friends/manage/add")
    @RequireAuthorization
    suspend fun addFriend(@Body addFriendRequest: AddFriendRequest)

    @GET("friends")
    @RequireAuthorization
    suspend fun getFriends(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProfileResponse>

    @GET("friends/requests/incoming")
    @RequireAuthorization
    suspend fun getIncomingRequests(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProfileResponse>

    @GET("friends/requests/outgoing")
    @RequireAuthorization
    suspend fun getOutgoingRequests(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProfileResponse>

    @POST("/api/v1/friends/manage/delete")
    @RequireAuthorization
    suspend fun removeFriend(
        @Body removeRequest: RemoveFriendRequest
    )

    //Photo region
    @POST("image/photo")
    @RequireAuthorization
    suspend fun sendPhoto(@Body photoRequest: PhotoRequest)

    @GET("image/all")
    @RequireAuthorization
    suspend fun getAllPhotos(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<PhotoResponse>

    @GET("image/{imageId}")
    @RequireAuthorization
    suspend fun getPhotoById(
        @Path("imageId") imageId: String
    ): String

    @GET("image/last")
    @RequireAuthorization
    suspend fun getLastImage(): PhotoResponse

}