package uz.abdullajon.onlineshopping.ui.account.card.cardList

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.database.ShopRoomDatabase

class CardPresenterImpl(private val viewPresenter: CardContract.CardView, context: Context) :
    CardContract.CardPresenter {

    private val roomDatabase = ShopRoomDatabase.getInstance(context)
    private val cd = CompositeDisposable()
    private val cardDao = roomDatabase.getCardDao()




    override fun getCard() {
        cd.add(
            Observable.just(cardDao.getGard())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewPresenter.getResponse(it)
                }, { viewPresenter.onFail("${it.message}") })
        )
    }

    override fun onCancel() {
        cd.dispose()
    }

}