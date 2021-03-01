package uz.abdullajon.onlineshopping.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.review_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Review

class ReviewAdapter(val context: Context) :
    ListAdapter<Review, ReviewAdapter.VH>(ItemDifferReview()) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.review_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bindData(review: Review) {
            client_name.text = review.customerName
            rating_bar.rating = review.rating!!
            review_title.text = review.title
//            Glide.with(context)
//                .load(brand.brandImage)
//                .into(brand_image)
        }
    }


}

class ItemDifferReview : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
        oldItem.reviewId == newItem.reviewId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
        oldItem == newItem

}
