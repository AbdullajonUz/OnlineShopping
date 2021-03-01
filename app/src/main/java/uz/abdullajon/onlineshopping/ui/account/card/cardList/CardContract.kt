package uz.abdullajon.onlineshopping.ui.account.card.cardList

import uz.abdullajon.onlineshopping.data.model.CustomerCard

interface CardContract {

    interface CardView {
        fun getResponse(list: List<CustomerCard>)
        fun onFail(message:String)
    }

    interface CardPresenter {
        fun getCard()
        fun onCancel()
    }
}