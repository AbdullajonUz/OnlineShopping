package uz.abdullajon.onlineshopping.ui.account

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.base.BaseFragment

class AccountFragment : BaseFragment(), View.OnClickListener {

    override fun layoutRes(): Int = R.layout.fragment_account
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        edit_profile.setOnClickListener(this)
        cards.setOnClickListener(this)
        order_history.setOnClickListener(this)
        log_out.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val bundle = bundleOf("flag" to 0)
        when (v!!.id) {
            edit_profile.id ->
                findNavController().navigate(R.id.action_navigation_account_to_navigation_edit_profile)
            cards.id ->
                findNavController().navigate(
                    R.id.action_navigation_account_to_navigation_cards,
                    bundle
                )
            order_history.id ->
                findNavController().navigate(R.id.action_navigation_account_to_navigation_order_history)
            log_out.id -> {
                auth.signOut()
                requireActivity().finishAndRemoveTask()
            }
        }
    }
}