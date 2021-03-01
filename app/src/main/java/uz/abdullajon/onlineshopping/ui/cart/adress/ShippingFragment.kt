package uz.abdullajon.onlineshopping.ui.cart.adress

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_shipping.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.utils.PREF_NAME


class ShippingFragment : Fragment() {

    companion object {
        fun newInstance() =
            ShippingFragment()
    }

    private val region =
        arrayOf(
            "Surxondaryo",
            "Andijon",
            "Qashqadaryo",
            "Toshkent",
            "Buxoro",
            "Namangan",
            "Farg'ona"
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = requireContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val mBottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetView: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        mBottomSheetDialog.setContentView(sheetView)


        next_btn.setOnClickListener {
            if (!et_address_1.text.isNullOrEmpty() && !et_address_2.text.isNullOrEmpty()) {
                val edit = preference.edit()
                edit.putString(
                    "address", region[region_spinner.selectedItemId.toInt()] +
                            " ${et_address_1.text.toString()}" +
                            " ${et_address_2.text.toString()}"
                )
                edit.apply()
                mBottomSheetDialog.show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "please full complete all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        back_btn.setOnClickListener {
            findNavController().popBackStack()
        }

        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, region)
        region_spinner.adapter = adapter
        sheetView.pay_handle.setOnClickListener {
            val bundle = bundleOf("paymentId" to 0)
            findNavController().navigate(
                R.id.action_shippingFragment_to_orderSummaryFragment,
                bundle
            )
            mBottomSheetDialog.dismiss()
        }
        sheetView.edit_cart_btn.setOnClickListener {
            mBottomSheetDialog.dismiss()
            findNavController().navigate(R.id.action_shippingFragment_to_cardCheckoutFragment)
        }
        sheetView.save_cart_btn.setOnClickListener {
            mBottomSheetDialog.dismiss()
            val bundle = bundleOf("flag" to 1)
            findNavController().navigate(R.id.action_shippingFragment_to_cardFragment, bundle)
        }
    }
}