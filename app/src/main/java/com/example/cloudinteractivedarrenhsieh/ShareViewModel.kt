package com.example.cloudinteractivedarrenhsieh

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudinteractivedarrenhsieh.data.Album
import com.example.cloudinteractivedarrenhsieh.loader.ImageLoader
import com.example.cloudinteractivedarrenhsieh.network.AlbumApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareViewModel : ViewModel() {
    private var _albumInfo = MutableLiveData<List<Album>>()
    val albumInfo: LiveData<List<Album>> = _albumInfo

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state


    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = AlbumApi.retrofitService.getAlbum()
                if(response.isSuccessful){
                    _albumInfo.postValue(response.body())
                }
            }catch (e: Exception){
                _state.postValue("Couldn't fetch the data!!")
            }
        }
    }


    fun setImage(position: Int, image: ImageView,context: Context){

        ImageLoader.showImage(_albumInfo.value?.get(position)?.thumbnailUrl!!,image,context)
/*
        var bitmap = ImageLoader.getCacheBitmap(_albumInfo.value?.get(position)?.thumbnailUrl!!)
        Log.i("bitmap","width"+bitmap?.width+" high"+bitmap?.height)
        if(bitmap != null) ImageLoader.displayImage(image, bitmap)
        else{
            viewModelScope.launch {
                bitmap = ImageLoader.downloadImage(_albumInfo.value?.get(position)?.thumbnailUrl!!,context)
                ImageLoader.displayImage(image,bitmap!!)
            }
        }

 */

/*
        val headerRequest =
            HeaderRequest(
                _albumInfo.value?.get(position)?.thumbnailUrl!!,
                { bitmap ->
                    image.setImageBitmap(bitmap)
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP, // image scale type
                Bitmap.Config.ARGB_8888, // decode config)
                {   // error listener
                })

        VolleySingleton.getInstance(context)
            .addToRequestQueue(headerRequest)


 */
    }

}