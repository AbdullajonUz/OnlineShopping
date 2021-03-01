package uz.abdullajon.onlineshopping.ui.home.homeB

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.model.Category

class HomeBPresenterImpl(private val viewPresenter: HomeBContract.HomeFragmentView) :
    HomeBContract.HomeFragmentPresenter {

    private val cd = CompositeDisposable()
    private val databaseRef = FirebaseDatabase.getInstance().reference
    override fun loadData() {
        Log.d("sasaa", "load")
        cd.add(Observable.create<List<Category>> {
            val categoryList = mutableListOf<Category>()
            val categoryRef = databaseRef.child("Categories")
            categoryRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (dataSnapshot1 in p0.children) {
                        val categoryId = dataSnapshot1.key
                        val categoryName = dataSnapshot1.child("categoryName").value as String
                        Log.d("sasaa", categoryName)
                        val categoryImage = dataSnapshot1.child("categoryImage").value as String
                        if (categoryId != null && categoryName.isNotEmpty()) {
                            val category = Category(categoryId.toInt(), categoryName, categoryImage)
                            categoryList.add(category)
                        }
                    }
                    it.onNext(categoryList)
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
