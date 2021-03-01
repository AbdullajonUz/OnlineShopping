package uz.abdullajon.onlineshopping.ui.start.verifyPhone

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import uz.abdullajon.onlineshopping.data.model.Customer
import uz.abdullajon.onlineshopping.utils.PREF_NAME

class SignUpPresenterImpl(
    private val viewPresenter: SignUpMVP.SignUpFragmentView
) : SignUpMVP.SignUpFragmentPresenter {

    private val auth = FirebaseAuth.getInstance()

    override fun onSignUpClick(context: Context) {
        viewPresenter.showProgress()
        val userId = auth.uid
        val preference = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val number = preference.getString("phoneNumber", "")
        val email = preference.getString("email", "")
        val username = preference.getString("username", "")
        if (userId != null) {
            val reference =
                FirebaseDatabase.getInstance().getReference("Users").child(userId)

            val user =
                Customer(userId, username, email, number)
            reference.setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewPresenter.hideProgress()
                    viewPresenter.successAuth()
                } else {
                    viewPresenter.onFail("${task.exception!!.message}")
                }
            }

        }
    }

    override fun onCancel() {
    }

}