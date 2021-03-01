package uz.abdullajon.onlineshopping.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.main.MainActivity
import uz.abdullajon.onlineshopping.ui.start.StartActivity

class SplashActivity : AppCompatActivity(), SplashNavigator {

    private var timer: CountDownTimer? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        getTimer()
    }

    private fun getTimer() {
        timer = object : CountDownTimer(3_000, 1000) {
            override fun onFinish() {
                if (auth.currentUser == null) {
                    onLoginActivity()
                } else {
                    onMainActivity()
                }
            }

            override fun onTick(p0: Long) {}
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onLoginActivity() {
        startActivity(Intent(this@SplashActivity, StartActivity::class.java))
        finish()
    }

    override fun onMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}
