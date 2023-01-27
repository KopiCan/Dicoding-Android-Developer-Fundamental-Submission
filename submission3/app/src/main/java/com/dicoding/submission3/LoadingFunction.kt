package com.dicoding.submission3

import android.content.Context
import android.view.View
import android.widget.Toast

class LoadingFunction {

    fun loadingState(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    fun activeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
