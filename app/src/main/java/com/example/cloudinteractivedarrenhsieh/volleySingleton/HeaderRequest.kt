package com.example.cloudinteractivedarrenhsieh.volleySingleton

import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest

class HeaderRequest(url:String,
                    listener:Response.Listener<Bitmap>,
                    maxWidth: Int,
                    maxHeight: Int,
                    scaleType: ImageView.ScaleType,
                    decodeConfig: Bitmap.Config,
                    errorListener:Response.ErrorListener
    ): ImageRequest(
    url,
    listener,
    maxWidth,
    maxHeight,
    scaleType,
    decodeConfig,
    errorListener
    ){
    //add headers to every request for letting placeholder.com service know request info. without this info, image cannot show properly
    override fun getHeaders(): MutableMap<String, String> {
        val map = mutableMapOf<String,String>()
        map["User-Agent"] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36"
        return map
    }
}