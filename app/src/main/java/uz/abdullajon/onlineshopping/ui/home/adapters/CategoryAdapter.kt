package uz.abdullajon.onlineshopping.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.category_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Category
import uz.abdullajon.onlineshopping.ui.home.SharedViewModel
import uz.abdullajon.onlineshopping.utils.PATH_URL

class CategoryAdapter(
    private val context: Context,
    private val onItemClick: (Int) -> Unit
) :
    ListAdapter<Category, CategoryAdapter.VH>(ItemDiffer()) {

    private val sharedViewModel = SharedViewModel()
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.category_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(category: Category) {
            tv_category.text = category.categoryName
            Glide
                .with(context)
                .load(PATH_URL + category.categoryImage)
                .into(category_image)

            containerView.setOnClickListener {
                sharedViewModel.setPrice(category.categoryID)
                onItemClick(category.categoryID)
            }
        }
    }


}

class ItemDiffer : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = true
    //oldItem.categoryID == newItem.categoryID

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem == newItem

}
