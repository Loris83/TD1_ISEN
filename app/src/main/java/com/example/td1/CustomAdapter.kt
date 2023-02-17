package com.example.td1

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.td1.databinding.ActivityMenuBinding
import com.example.td1.databinding.CellCustomBinding
import com.squareup.picasso.Picasso
import network.Plate

class CustomAdapter(val items: List<Plate>, val clickListener: (Plate) -> Unit) : RecyclerView.Adapter<CustomAdapter.CellViewHolder>() {
    class CellViewHolder(binding: CellCustomBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.itemName
        val root: ConstraintLayout = binding.root
        val imageView: ImageView = binding.itemPicture
        val priceTextView = binding.priceView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = CellCustomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        /*binding.itemName.setOnClickListener{
            Log.d("item", "click sur item ")
        }*/
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.textView.text = items[position].name
        Picasso.get().load(getThumbnail(items[position])).resize(200, 200).centerCrop().into(holder.imageView)
            holder.priceTextView.text = items[position].prices[0].price + "€"
        holder.root.setOnClickListener {
            Log.d("item", "click on " + position)
            clickListener(items[position])
        }
    }

    private fun getThumbnail(plate: Plate): String?{
        return if (plate.images.isNotEmpty() && plate.images.firstOrNull()?.isNotEmpty() == true) {
            plate.images.firstOrNull()
        }else {
            null
        }
    }
}
