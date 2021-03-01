package uz.abdullajon.onlineshopping.ui.account.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.base.BaseFragment
import uz.abdullajon.onlineshopping.utils.PREF_NAME

class EditProfileFragment : BaseFragment(), EditProfileContract.EditProfileView {

    override fun layoutRes(): Int = R.layout.fragment_edit_profile
    lateinit var presenter: EditProfilePresenterImpl
    private val cd = CompositeDisposable()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EditProfilePresenterImpl(this)
        checkValidation()
        val preference = requireContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val phone = preference.getString("phoneNumber", "")
        et_username.setText(preference.getString("username",""))
        et_email.setText(preference.getString("email",""))

        save_button.setOnClickListener {
            presenter.editProfile(et_username.text.toString(), et_email.text.toString(), phone!!)
        }
        cancel_edit.setOnClickListener { findNavController().popBackStack() }
    }

    private fun checkValidation() {
        val disposable =
            Observable.combineLatest(
                et_username.textChanges()
                    .map { it.isNotEmpty() }
                    .skip(1)
                    .doOnNext {
                        if (!it) {
                            it_username.error = "field is required"
                        } else {
                            it_username.error = null
                        }
                    },
                et_email.textChanges()
                    .map { it.isNotEmpty() }
                    .skip(1)
                    .doOnNext {
                        if (!it) {
                            it_email.error = "field is required"
                        } else {
                            it_email.error = null
                        }
                    },
                BiFunction<Boolean, Boolean, Boolean> { t1, t2 ->
                    t1 && t2
                })
                .doOnNext {
                    save_button.isEnabled = it
                }
                .subscribe()

        cd.add(disposable)
    }

    override fun getResponse() {
        //todo
        findNavController().popBackStack()
    }

    override fun onStop() {
        super.onStop()
        cd.clear()
        presenter.onCancel()
    }

}
