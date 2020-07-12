package com.assignment.paytm.utiles

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageUtils {
    companion object {
        fun newInstance() = ImageUtils()
    }

    fun downloadImage(pUrl: String, pView: View) {
        when (pView) {
            is ImageView -> {
                setImageToView(
                    pUrl = pUrl,
                    pView = pView
                )
            }
        }
    }

    private fun setImageToView(pUrl: String, pView: ImageView) {
        Glide.with(pView.context)
            .load(pUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(pView);
    }
}