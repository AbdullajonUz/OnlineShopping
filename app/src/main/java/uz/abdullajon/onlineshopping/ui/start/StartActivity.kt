package uz.abdullajon.onlineshopping.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_start.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.main.MainActivity
import uz.abdullajon.onlineshopping.ui.start.signup.SignUpFragment
import uz.abdullajon.onlineshopping.ui.start.verifyPhone.VerifyPhoneFragment

class StartActivity : AppCompatActivity(), StartClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if (savedInstanceState == null) {
            addFragment(SignUpFragment())
        }
    }

    private fun addFragment(newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(layout_container.id, newFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        supportFragmentManager.popBackStack()
        if (count == 1){
            super.onBackPressed()
        }
    }

    override fun signUpClick(phoneNumber: String) {
        addFragment(VerifyPhoneFragment.instance(phoneNumber))
    }

    override fun registerClick() {
        val intent =
            Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
        startActivity(intent)
    }
}

