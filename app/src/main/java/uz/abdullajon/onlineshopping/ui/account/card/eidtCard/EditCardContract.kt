package uz.abdullajon.onlineshopping.ui.account.card.eidtCard

import uz.abdullajon.onlineshopping.data.model.CustomerCard

interface EditCardContract {
    interface EditCardView {
        fun getResponse()
    }

    interface EditCardPresenter {
        fun addCard(customerCard: CustomerCard)
        fun onCancel()
    }
}