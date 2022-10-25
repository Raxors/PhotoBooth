package com.raxors.photobooth.base

import android.view.View

class Extensions {
    companion object {

        fun View.hide() {
            if (this.visibility != View.GONE)
                this.visibility = View.GONE
        }

        fun View.show() {
            if (this.visibility != View.VISIBLE)
                this.visibility = View.VISIBLE
        }

    }
}