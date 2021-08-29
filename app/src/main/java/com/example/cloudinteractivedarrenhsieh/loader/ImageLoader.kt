package com.example.cloudinteractivedarrenhsieh.loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

//image loader -- singleton object
object ImageLoader {
    private var imageCache: LruCache<String, Bitmap>
    private val scope = MainScope()

    //initialize cache -- singleton object
    init {
        val maxMemory: Long = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (maxMemory / 4).toInt()
        imageCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return (bitmap?.rowBytes ?: 0) * (bitmap?.height ?: 0) / 1024
            }
        }
    }

    fun showImage(url: String, imageView: ImageView,context: Context){
        var bitmap: Bitmap? = imageCache.get(url)
        if(bitmap != null) imageView.setImageBitmap(bitmap)
        else{
            scope.launch {
                bitmap = downloadImage(url,context)
                imageView.setImageBitmap(bitmap)
            }
        }
    }


    private suspend fun downloadImage(url: String, context: Context): Bitmap?{
        //get the width of each grid item
        val reqDimension: Int = calculateViewSize(context)
        var bitmap: Bitmap? = null
        val option = BitmapFactory.Options()
        withContext(Dispatchers.IO){
            val innerUrl = URL(url)
            val conn: HttpURLConnection = innerUrl.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")

            //configure the new size of image
            option.inSampleSize = calculateInSampleSize(150,150,reqDimension,reqDimension)
            option.inPreferredConfig = Bitmap.Config.RGB_565
            try {
                bitmap = BitmapFactory.decodeStream(conn.inputStream,null, option)
                if(bitmap != null) imageCache.put(url, bitmap)
                conn.disconnect()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return bitmap
    }


    private fun calculateInSampleSize(oriHeight: Int, oriWidth: Int, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        //val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (oriHeight > reqHeight || oriWidth > reqWidth) {

            val halfHeight: Int = oriHeight / 2
            val halfWidth: Int = oriWidth / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    //get the size of each grid item
    private fun calculateViewSize(context: Context): Int{
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels

        return (dpWidth/4)
        //four grids per row
    }

    fun cancelScope(){
        this.scope.cancel()
    }

}