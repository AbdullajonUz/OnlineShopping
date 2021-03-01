package uz.abdullajon.onlineshopping.ui.account.card.cardList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CustomerCard

class CardAdapter(
    private val context: Context,
    private val cardListener: CardListener
) :
    ListAdapter<CustomerCard, CardAdapter.VH>(
        ItemDifferCard()
    ) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.card_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(itemCard: CustomerCard?) {
            if (itemCard != null) {
                tv_card_name.text = itemCard.cardName
                tv_card_number.text = itemCard.cardNumber
                tv_expiry_date.text = itemCard.expiryDate
            }
            containerView.setOnClickListener {
                cardListener.itemClick(adapterPosition)
            }
        }
    }

    class ItemDifferCard : DiffUtil.ItemCallback<CustomerCard>() {
        override fun areItemsTheSame(oldItem: CustomerCard, newItem: CustomerCard): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CustomerCard, newItem: CustomerCard): Boolean =
            oldItem.id == newItem.id
    }
}

interface CardListener {
    fun itemClick(position: Int)
}

