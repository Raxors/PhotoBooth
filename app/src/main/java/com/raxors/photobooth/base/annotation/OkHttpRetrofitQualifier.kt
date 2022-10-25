package com.raxors.photobooth.base.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClientQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CommonOkHttpClientQualifier