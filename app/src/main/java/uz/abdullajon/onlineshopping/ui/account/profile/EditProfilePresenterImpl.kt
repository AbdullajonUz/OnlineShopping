package uz.abdullajon.onlineshopping.ui.account.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import uz.abdullajon.onlineshopping.data.model.Customer

class EditProfilePresenterImpl(private val viewPresenter: EditProfileContract.EditProfileView) :
    EditProfileContract.EditProfilePresenter {

    private val cd = CompositeDisposable()
    private val auth = FirebaseAuth.getInstance()

    override fun editProfile(username: String, email: String, phone: String) {
        val userId = auth.uid
        if (userId != null) {
            val reference =
                FirebaseDatabase.getInstance().getReference("Users").child(userId)

            val user =
                Customer(userId, username, email, phone)
            reference.setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewPresenter.getResponse()
                } else {
                    viewPresenter.onFail("${task.exception!!.message}")
                }
            }
        }
    }

    override fun onCancel() {
        cd.clear()
    }

}