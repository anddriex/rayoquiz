package com.eapp.rayoquiz.ui.pictures

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eapp.rayoquiz.PictureInfo
import com.eapp.rayoquiz.R

class PictureListAdapter(private val context: Context, private val pictures: List<PictureInfo>) : RecyclerView.Adapter<PictureListAdapter.PicturesViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView?>(R.id.imageView)
        var picturePosition = 0

        init {
            itemView.setOnClickListener{
                val action = PictureListFragmentDirections.actionPictureListFragmentToPictureFragment(picturePosition)
                itemView.findNavController ().navigate(action)
            }
        }
    }

    override fun getItemCount() = pictures.size

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val picture = pictures[position]
        val bitmap = BitmapFactory.decodeStream(context.assets.open(picture.imageName))
        holder.imageView?.setImageBitmap(bitmap)
        holder.picturePosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_picture_list, parent, false)
        return PicturesViewHolder(itemView)
    }
}