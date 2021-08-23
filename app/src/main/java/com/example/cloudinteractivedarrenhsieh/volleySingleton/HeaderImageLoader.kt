package com.example.cloudinteractivedarrenhsieh.volleySingleton

import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader

class HeaderImageLoader(queue: RequestQueue, imageCache:ImageCache ):ImageLoader(queue,imageCache) {

    override fun makeImageRequest(
        requestUrl: String?,
        maxWidth: Int,
        maxHeight: Int,
        scaleType: ImageView.ScaleType?,
        cacheKey: String?
    ): Request<Bitmap> {
        return HeaderRequest(requestUrl!!,
            { response -> onGetImageSuccess(cacheKey, response) },
            maxWidth,
            maxHeight,
            scaleType!!,
            Bitmap.Config.RGB_565,
            { error -> onGetImageError(cacheKey, error) })
    }

}