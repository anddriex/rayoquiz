package com.eapp.rayoquiz.ui.pictures

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.eapp.rayoquiz.DataManager
import com.eapp.rayoquiz.PictureInfo
import com.eapp.rayoquiz.R
import com.eapp.rayoquiz.RayoQuizViewModel
import kotlinx.android.synthetic.main.fragment_picture.*
import kotlinx.android.synthetic.main.fragment_picture_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PictureListFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel : RayoQuizViewModel by activityViewModels()
        viewModel.getPictures().observe(viewLifecycleOwner, Observer<List<PictureInfo>>{ pics ->
            listPictures.apply {
                layoutManager = GridLayoutManager(this.context, resources.getInteger(R.integer.pictures_span))
                adapter = PictureListAdapter(requireContext(), pics)
            }
        })
    }
}