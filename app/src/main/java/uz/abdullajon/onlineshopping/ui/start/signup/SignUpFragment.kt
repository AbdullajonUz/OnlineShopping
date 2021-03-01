package uz.abdullajon.onlineshopping.ui.start.signup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sign_up.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.start.StartClickListener
import uz.abdullajon.onlineshopping.utils.PREF_NAME

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val cd = CompositeDisposable()
    private lateinit var startClickListener: StartClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is StartClickListener) {
            startClickListener = context as StartClickListener
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkValidation() {
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("+998 ([00]) [000] [00] [00]")

        val listener =
            MaskedTextChangedListener.installOn(
                et_number,
                "+998 ([00]) [000] [00] [00]",
                affineFormats, AffinityCalculationStrategy.PREFIX,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {
                    }
                }
            )
//        val disposable =
//            Observable.combineLatest(
//                et_username.textChanges()
//                    .map { it.isNotEmpty() }
//                    .skip(1)
//                    .doOnNext {
//                        if (!it) {
//                            it_username.error = "field is required"
//                        } else {
//                            it_username.error = null
//                        }
//                    },
//                et_email.textChanges()
//                    .map { it.isNotEmpty() }
//                    .skip(1)
//                    .doOnNext {
//                        if (!it) {
//                            it_email.error = "field is required"
//                        } else {
//                            it_email.error = null
//                        }
//                    },
//                BiFunction<Boolean, Boolean, Boolean> { t1, t2 ->
//                    t1 && t2
//                })
//                .doOnNext {
//                    get_code.isEnabled = it
//                }
//                .subscribe()
//
//        cd.add(disposable)
//        et_number.addTextChangedListener(PhoneNumberFormattingTextWatcher("+998"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkValidation()
        get_code.setOnClickListener { signUpClick() }
    }

    private fun signUpClick() {

        if (Patterns.PHONE.matcher(et_number.text.toString().trim())
                .matches() && !et_username.text.isNullOrEmpty() && !et_email.text.isNullOrEmpty()
        ) {
            val preference = requireContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            val edit = preference.edit()
            edit.putString("phoneNumber", et_number.text.toString())
            edit.putString("email", et_email.text.toString())
            edit.putString("username", et_username.text.toString())
            edit.apply()
            startClickListener.signUpClick(et_number.text.toString())
        } else {
            Toast.makeText(
                requireContext(),
                "please correct complete fields",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onStop() {
        super.onStop()
        cd.dispose()
    }


}