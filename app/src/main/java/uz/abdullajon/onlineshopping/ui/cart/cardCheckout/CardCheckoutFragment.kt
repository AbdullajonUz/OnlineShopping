package uz.abdullajon.onlineshopping.ui.cart.cardCheckout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import kotlinx.android.synthetic.main.fragment_card_checkout.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CustomerCard

class CardCheckoutFragment : Fragment(R.layout.fragment_card_checkout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPrefixSample()
        setupSuffixSample()
        next_btn.setOnClickListener {
            if (!et_name_card.text.isNullOrEmpty() && et_card_number.text.toString().length == 19 && et_expiry.text.toString().length == 5) {
                val card = CustomerCard(
                    et_name_card.text.toString(),
                    et_card_number.text.toString(),
                    et_expiry.text.toString()
                )
                val bundle = bundleOf("card" to card, "paymentId" to 1)
                findNavController().navigate(
                    R.id.action_cardCheckoutFragment_to_orderSummaryFragment,
                    bundle
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "please full complete all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        back_btn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupPrefixSample() {
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("[0000] [0000] [0000] [0000]")

        val listener =
            MaskedTextChangedListener.installOn(
                et_card_number,
                "[0000] [0000] [0000] [0000]",
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

    }

    private fun setupSuffixSample() {
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("[00]{/}[00]")
        val listener =
            MaskedTextChangedListener.installOn(
                et_expiry,
                "[00]{/}[00]",
                affineFormats, AffinityCalculationStrategy.WHOLE_STRING,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {
                    }
                }
            )
    }
}