package uz.abdullajon.onlineshopping.ui.cart.fullCheckout

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.database.ShopRoomDatabase
import uz.abdullajon.onlineshopping.data.model.Order

class OrderSummaryPresenterImpl(
    private val viewPresenter: OrderSummaryContract.CartView,
    context: Context
) :
    OrderSummaryContract.CartPresenter {

    private val roomDatabase = ShopRoomDatabase.getInstance(context)
    private val cd = CompositeDisposable()
    private val cartDao = roomDatabase.getCartDao()

    override fun getCartProduct() {
        cd.add(
            Observable.just(cartDao.getCartProducts())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewPresenter.getResponse(it)
                }, {})
        )
    }

    override fun setOrder(order: Order) {
        viewPresenter.showProgress()
        val reference =
            FirebaseDatabase.getInstance().getReference("Orders").push()

        reference.setValue(order).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewPresenter.hideProgress()
                viewPresenter.successAuth()
            } else {
                viewPresenter.onFail("${task.exception!!.message}")
            }
        }
    }

    override fun onCancel() {
        cd.dispose()
    }
}

