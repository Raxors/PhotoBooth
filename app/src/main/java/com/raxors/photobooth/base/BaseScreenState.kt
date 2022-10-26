package com.raxors.photobooth.base

import java.lang.Exception

sealed class BaseScreenState {
    object Loading : BaseScreenState()
    class Error(e: Exception) : BaseScreenState()
    object Logout : BaseScreenState()
}