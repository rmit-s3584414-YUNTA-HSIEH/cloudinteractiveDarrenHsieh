package com.example.cloudinteractivedarrenhsieh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivedarrenhsieh.loader.ImageLoader


class ItemFragment : Fragment() {

    private var position: Int? = null
    private val viewModel: ShareViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get data from recyclerview
        position = arguments?.getInt("position")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: TextView = view.findViewById(R.id.idText)
        val title: TextView = view.findViewById(R.id.titleText)
        val image: ImageView = view.findViewById(R.id.largeImageView)

        //back to grid view when clicking on the screen
        image.setOnClickListener {
            this.findNavController().navigate(R.id.action_itemFragment_to_gridFragment)
        }


        //binding data to the layout
        if(position != null){
            val item = viewModel.albumInfo.value!![position!!]
            id.text = item.id.toString()
            title.text = item.title
            viewModel.setImage(position!!,image,this.requireContext())
        }



    }

    override fun onDestroy() {
        ImageLoader.cancelScope()
        super.onDestroy()
    }

}