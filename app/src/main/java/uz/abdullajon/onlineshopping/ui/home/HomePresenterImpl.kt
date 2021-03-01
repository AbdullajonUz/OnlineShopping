package uz.abdullajon.onlineshopping.ui.home

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.model.Product

class HomePresenterImpl(private val viewPresenter: HomeContract.HomeFragmentView) :
    HomeContract.HomeFragmentPresenter {

    private val cd = CompositeDisposable()
    private val databaseRef = FirebaseDatabase.getInstance().reference
    override fun loadData(text: String) {
        cd.add(Observable.create<List<Product>> {
            val productList = mutableListOf<Product>()
            val productRef = databaseRef.child("Products").orderByChild("name")
                .startAt(text).endAt(text + "\uf8ff")
            productRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (dataSnapshot1 in p0.children) {
                        val productId = dataSnapshot1.key
                        val productName =
                            dataSnapshot1.child("name").value as String
                        val product = Product(
                            productId!!.toInt(),
                            productName
                        )
                        productList.add(product)
                    }
                    it.onNext(productList)
                }
            })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewPresenter.onResponse(it)
                },
                {
                    viewPresenter.onFail(it.message.toString())
                })
        )
    }


    override fun cancel() {
        cd.dispose()
    }

}
