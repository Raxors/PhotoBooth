package com.raxors.photobooth.utils

import java.math.BigInteger
import java.security.MessageDigest

object Security {

    //fun to encrypt password to sha256
    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(this.toByteArray())
        val hexString = BigInteger(1, digest).toString(16)
        return hexString.padStart(32, '0')
    }

}