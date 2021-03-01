package uz.abdullajon.onlineshopping.ui.account.card.eidtCard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding3.widget.textChanges
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import com.redmadrobot.inputmask.MaskedTextChangedListener.ValueListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.card_item.*
import kotlinx.android.synthetic.main.fragment_edit_card.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CustomerCard
import uz.abdullajon.onlineshopping.ui.base.BaseFragment


class EditCardFragment : BaseFragment(), EditCardContract.EditCardView {
    override fun layoutRes(): Int = R.layout.fragment_edit_card

    private lateinit var presenter: EditCardPresenterImpl
    private val cd = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EditCardPresenterImpl(this, requireContext())
        checkValidation()
        setupPrefixSample()
        setupSuffixSample()
        save_btn.setOnClickListener {
            presenter.addCard(
                CustomerCard(
                    et_name_card.text.toString(),
                    et_card_number.text.toString(),
                    et_expiry.text.toString()
                )
            )
        }
        cancel_btn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupPrefixSample() {
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("[0000] [0000] [0000] [0000]")

        val listener =
            installOn(
                et_card_number,
                "[0000] [0000] [0000] [0000]",
                affineFormats, AffinityCalculationStrategy.PREFIX,
                object : ValueListener {
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
            installOn(
                et_expiry,
                "[00]{/}[00]",
                affineFormats, AffinityCalculationStrategy.WHOLE_STRING,
                object : ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {
                    }
                }
            )
    }

    @SuppressLint("SetTextI18n")
    private fun checkValidation() {
        cd.add(et_name_card.textChanges()
            .skipInitialValue()
            .subscribe { tv_card_name.text = it })

        cd.add(et_card_number.textChanges()
            .skipInitialValue()
            .doOnNext {
                tv_card_number.text = it
            }
            .subscribe())

        cd.add(et_expiry.textChanges()
            .skipInitialValue()
            .doOnNext {
                tv_expiry_date.text = it
            }
            .subscribe())
    }

    override fun getResponse() {
        findNavController().popBackStack(R.id.navigation_account, false)
    }

    override fun onStop() {
        super.onStop()
        cd.clear()
    }
}