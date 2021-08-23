package com.example.cloudinteractivedarrenhsieh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudinteractivedarrenhsieh.adapter.AlbumAdapter


class GridFragment : Fragment() {

    private val viewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview: RecyclerView = view.findViewById(R.id.recyclerView)

        //get data through Api
        viewModel.fetchData()

        viewModel.albumInfo.observe(viewLifecycleOwner,{
            recyclerview.layoutManager = GridLayoutManager(context,4)
            recyclerview.adapter = AlbumAdapter(viewModel.albumInfo.value!!,this.requireContext())
        })

        //couldn't load the data
        viewModel.state.observe(viewLifecycleOwner,{
            Toast.makeText(context,viewModel.state.value, Toast.LENGTH_LONG).show()
        })

        }



}