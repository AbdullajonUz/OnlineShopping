package uz.abdullajon.onlineshopping.ui.account.card.cardList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_card.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CustomerCard
import uz.abdullajon.onlineshopping.ui.base.BaseFragment

class CardFragment : BaseFragment(), View.OnClickListener, CardContract.CardView, CardListener {


    override fun layoutRes(): Int = R.layout.fragment_card

    lateinit var presenter: CardPresenterImpl
    lateinit var adapter: CardAdapter
    private val cardList = mutableListOf<CustomerCard>()
    var flag: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CardAdapter(requireContext(), this)
        card_list.adapter = adapter
        presenter = CardPresenterImpl(this, requireContext())
        presenter.getCard()
        flag = requireArguments().getInt("flag", 0)
        if (flag == 1) {
            add_fab.visibility = View.GONE
        }
        add_fab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        findNavController().navigate(R.id.action_navigation_cards_to_editCardFragment)
    }

    override fun getResponse(list: List<CustomerCard>) {
        cardList.clear()
        cardList.addAll(list)
        adapter.submitList(list)
    }

    override fun onFail(message: String) {
        //todo
    }

    override fun itemClick(position: Int) {
        if (flag == 1) {
            val bundle = bundleOf("card" to cardList[position], "paymentId" to 1)
            findNavController().navigate(R.id.action_cardFragment_to_orderSummaryFragment, bundle)
        }
    }

}
