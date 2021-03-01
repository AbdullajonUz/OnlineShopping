package uz.abdullajon.onlineshopping.ui.cart

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.database.ShopRoomDatabase

class CartPresenterImpl(private val viewPresenter: CartContract.CartView, context: Context) :
    CartContract.CartPresenter {

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

    override fun deleteById(id: Long) {
        cd.add(
            Observable.just(cartDao.deleteItemById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {})
        )
    }

    override fun quantityClick(quantity: Int, id: Int) {
        cd.add(
            Observable.just(cartDao.updateQuantityProduct(quantity, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {})
        )
    }

    override fun onCancel() {
        cd.dispose()
    }
}

