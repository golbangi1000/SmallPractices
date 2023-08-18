package com.example.chapter88

import android.net.Uri
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : ListAdapter<ImageItems, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<ImageItems>(){
        /*
        액자 UI 그리기 (1) 06:30
        listadapter와 recyclerViewAdapter의 큰차이는 변경사항에 대한 처리
         */
        override fun areItemsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            return oldItem == newItem
        }

    }
){
    override fun getItemCount(): Int {

        val originSize = currentList.size

        return if(originSize == 0){
            0
        } else {
            originSize.inc()
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if(itemCount.dec() == position) ITEM_LOAD_MORE else ITEM_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        const val ITEM_IMAGE = 0
        const val ITEM_LOAD_MORE = 1
    }

}


sealed class ImageItems{
    data class Image(
        val uri : Uri,

        ) : ImageItems()

    object LoadMore : ImageItems()
}
