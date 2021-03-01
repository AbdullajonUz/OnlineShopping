package uz.abdullajon.onlineshopping.ui.account.profile

import uz.abdullajon.onlineshopping.data.model.Customer

interface EditProfileContract {

    interface EditProfileView {
        fun getResponse()
        fun onFail(message: String) {

        }
    }

    interface EditProfilePresenter {
        fun editProfile(username: String, email: String, phone: String)
        fun onCancel()
    }
}