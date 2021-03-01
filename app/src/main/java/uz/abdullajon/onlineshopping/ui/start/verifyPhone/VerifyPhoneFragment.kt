package uz.abdullajon.onlineshopping.ui.start.verifyPhone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_verify_phone.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.start.StartClickListener
import java.util.concurrent.TimeUnit

class VerifyPhoneFragment : Fragment(R.layout.fragment_verify_phone), SignUpMVP.SignUpFragmentView {
    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var startClickListener: StartClickListener
    private var phoneNumber = ""
    private var timer: CountDownTimer? = null
    private var presenter: SignUpPresenterImpl? = null
    private var dialog: AlertDialog? = null

    companion object {
        fun instance(phoneNumber: String): VerifyPhoneFragment {
            val verifyPhoneFragment = VerifyPhoneFragment()
            val bundle = Bundle()
            bundle.putString("phone", phoneNumber)
            verifyPhoneFragment.arguments = bundle
            return verifyPhoneFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is StartClickListener) {
            startClickListener = context as StartClickListener
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SignUpPresenterImpl(this)
        mAuth = FirebaseAuth.getInstance()
        phoneNumber = requireArguments().getString("phone", "")
        sendVerificationCode(phoneNumber)

        et_password.setOnPinEnteredListener {
            verifyCode(it.toString())
        }
        getTimer()

    }

    private fun verifyCode(code: String) {
        if (verificationId != null) {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            signInWithCredential(credential)
        } else {
            Toast.makeText(requireContext(), "code error!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTimer() {
        timer = object : CountDownTimer(60_000, 1000) {
            override fun onFinish() {
                childFragmentManager.popBackStack()
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                timer_register.text = "" + String.format(
                    "%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(p0),
                    TimeUnit.MILLISECONDS.toSeconds(p0)
                )
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    presenter!!.onSignUpClick(requireContext())
                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun sendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBack
        )
    }

    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    et_password!!.setText(code)
                    verifyCode(code)
                } else {
                    signInWithCredential(phoneAuthCredential)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }

    override fun onFail(exception: String) {
        Snackbar.make(it_username, exception, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun hideProgress() {
        dialog?.hide()
    }

    override fun showProgress() {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_progress)
            .setCancelable(true)
            .setOnDismissListener { dialog -> dialog.dismiss() }
        dialog = builder.create()
        dialog?.show()
    }

    override fun successAuth() {
        startClickListener.registerClick()
    }

}