package uz.abdullajon.onlineshopping.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.brand_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Brand
import uz.abdullajon.onlineshopping.utils.PATH_URL

class BrandAdapter(val context: Context, private val listener: BrandListener) :
    ListAdapter<Brand, BrandAdapter.VH>(ItemDifferBrand()) {

    private val inflater = LayoutInflater.from(context)
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.brand_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bindData(brand: Brand) {
            tv_brand_name.text = brand.brandName
            Glide.with(context)
                .load(PATH_URL + brand.brandImage)
                .into(brand_image)

            if (adapterPosition == selectedPosition) {
                containerView.setBackgroundColor(Color.parseColor("#C0C0C0"))
            } else {
                containerView.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            containerView.setOnClickListener {
                listener.itemClick(adapterPosition)
                containerView.setBackgroundColor(Color.parseColor("#C0C0C0"))
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                }
            }
        }
    }
}

class ItemDifferBrand : DiffUtil.ItemCallback<Brand>() {
    override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean =
        oldItem.brandID == newItem.brandID

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean =
        oldItem == newItem
}

interface BrandListener {
    fun itemClick(position: Int)
}
