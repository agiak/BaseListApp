package gr.baseapps.baselistapp.product.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gr.baseapps.baselistapp.R
import gr.baseapps.baselistapp.databinding.ItemProductBinding
import gr.baseapps.baselistapp.product.Product

class ProductAdapter(
    private var products: List<Product> = emptyList(),
    private val onClick: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val product = products[position]

            Glide.with(holder.itemView)
                .load(product.thumbnail)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_product_placeholder))
                .into(binding.image)
            binding.title.text = product.name
            binding.cost.text = product.price

            binding.root.setOnClickListener {
                onClick(product)
            }
            binding.lineIndicator.isVisible = position != products.size-1
        }
    }

    override fun getItemCount() = products.size

    fun updateItems(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}
