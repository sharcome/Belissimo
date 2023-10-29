package com.example.belissimo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.belissimo.R
import com.example.belissimo.ShowProductActivity
import com.example.belissimo.models.ProductsItemModel

class ProductsAdapter(private val list: List<ProductsItemModel>):
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val categories: TextView = itemView.findViewById(R.id.textView)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDesc: TextView = itemView.findViewById(R.id.product_descr)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val button_product: ConstraintLayout = itemView.findViewById(R.id.button_product)


        fun bind(productsModel: ProductsItemModel){
            categories.text = productsModel.categoryName
            productsModel.productImage?.let { image.setImageResource(it) }
            productName.text = productsModel.productName
            productDesc.text = productsModel.productDescription
            productPrice.text = productsModel.productPrice.toString()
            button_product.setOnClickListener{
                val intent = Intent(itemView.context,ShowProductActivity:: class.java)
                intent.putExtra("product", productsModel)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_products,parent,false)
        return ProductViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (position > 0){
            if (list[position].categoryName == list [position-1].categoryName){
                holder.categories.visibility = View.GONE
            }
        }

        holder.bind(list[position])

    }

}