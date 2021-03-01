package uz.abdullajon.onlineshopping.ui.account.card.eidtCard

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.database.ShopRoomDatabase
import uz.abdullajon.onlineshopping.data.model.CustomerCard

class EditCardPresenterImpl(
    private val viewPresenter: EditCardContract.EditCardView,
    val context: Context
) :
    EditCardContract.EditCardPresenter {
    private val cd = CompositeDisposable()

    override fun addCard(customerCard: CustomerCard) {
        val roomDatabase: ShopRoomDatabase = ShopRoomDatabase.getInstance(context)
        cd.add(
            Observable.just(roomDatabase.getCardDao().addCard(customerCard))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewPresenter.getResponse()
                }, {})
        )
    }

    override fun onCancel() {
        cd.dispose()
    }
}
