package com.example.belissimo.adapters


import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.belissimo.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belissimo.`interface`.BottomInterface
import com.example.belissimo.models.BottomItemModel

class BottomAdapters(val list: List<BottomItemModel>, val  bottomInterface: BottomInterface) : RecyclerView.Adapter<BottomAdapters.BottomViewHolder>() {

    inner class BottomViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)

        private var selectedPosition =  0

        fun bind(bottomItemModel: BottomItemModel, position: Int) {
            if (selectedPosition == position){
                title.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.red)))
                image.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.red))
            }else{
                title.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.unselected_text)))
                image.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.unselected_image))
            }

            itemView.setOnClickListener {
                val previousSelected: Int = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)

                bottomInterface.clickBottom(bottomItemModel.title)
            }
            title.text = bottomItemModel.title
            image.setImageResource(bottomItemModel.image)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bottom,parent,false)
        return BottomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}