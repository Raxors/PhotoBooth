package com.raxors.photobooth.di

import com.raxors.photobooth.data.repository.auth.AuthRepository
import com.raxors.photobooth.data.repository.auth.AuthRepositoryImpl
import com.raxors.photobooth.data.repository.friends.FriendsRepository
import com.raxors.photobooth.data.repository.friends.FriendsRepositoryImpl
import com.raxors.photobooth.data.repository.image.PhotoListRepository
import com.raxors.photobooth.data.repository.image.PhotoListRepositoryImpl
import com.raxors.photobooth.data.repository.image.PhotoRepository
import com.raxors.photobooth.data.repository.image.PhotoRepositoryImpl
import com.raxors.photobooth.data.repository.prefs.PrefsRepository
import com.raxors.photobooth.data.repository.prefs.PrefsRepositoryImpl
import com.raxors.photobooth.data.repository.profile.ProfileRepository
import com.raxors.photobooth.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun providePhotoListRepository(repository: PhotoListRepositoryImpl): PhotoListRepository

    @Binds
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun providePhotoRepository(repository: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun providePrefsRepository(repository: PrefsRepositoryImpl): PrefsRepository

    @Binds
    fun provideFriendsRepository(repository: FriendsRepositoryImpl): FriendsRepository
}
