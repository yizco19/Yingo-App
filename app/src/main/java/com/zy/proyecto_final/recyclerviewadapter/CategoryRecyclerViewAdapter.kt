package com.zy.proyecto_final.recyclerviewadapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.zy.proyecto_final.databinding.FragmentItemcategoryBinding
import com.zy.proyecto_final.pojo.Category

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CategoryRecyclerViewAdapter(
    private var values: MutableList<Category>
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {
    var content_click : ((position: Int, item: Category) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemcategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
        holder.contentView.setOnClickListener {
            this.content_click?.let { it -> it(position, item) }
        }
    }

    inner class ViewHolder(binding: FragmentItemcategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        var content_click :TextView = binding.content
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
    public fun setValues(v:MutableList<Category>){
        this.values=v;
        this.notifyDataSetChanged()
    }

}