package com.wednesdayassignment.ui.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wednesdayassignment.BR
import com.wednesdayassignment.R
import com.wednesdayassignment.data.model.ResultItems
import com.wednesdayassignment.databinding.ArtistItemRowBinding
import io.reactivex.annotations.NonNull

class ArtistListAdapter(
    private val list: List<ResultItems>,
    val listener: ArtistClickListener
) : RecyclerView.Adapter<ArtistListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistListAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.artist_item_row, parent, false
        )
        return ViewHolder(binding as ArtistItemRowBinding)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val imageModelList = list[position]
        holder.bind(imageModelList)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(private val artistItemRowBinding: ArtistItemRowBinding) :
        RecyclerView.ViewHolder(artistItemRowBinding.root) {
        fun bind(obj: Any) {
            artistItemRowBinding.setVariable(BR.model, obj)
        }
    }

    interface ArtistClickListener {
        fun onItemClicked(pos: Int)
    }

}