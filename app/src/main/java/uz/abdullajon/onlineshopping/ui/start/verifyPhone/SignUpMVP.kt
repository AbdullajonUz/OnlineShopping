package uz.abdullajon.onlineshopping.ui.start.verifyPhone

interface SignUpMVP {
    interface SignUpFragmentView {
        fun showProgress()
        fun onFail(exception: String)
        fun hideProgress()
        fun successAuth()
    }

    interface SignUpFragmentPresenter {
        fun onSignUpClick(context: android.content.Context)
        fun onCancel()
    }
}