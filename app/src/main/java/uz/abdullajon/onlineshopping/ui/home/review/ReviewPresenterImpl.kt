package uz.abdullajon.onlineshopping.ui.home.review

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.model.Review

class ReviewPresenterImpl(private val viewPresenter: ReviewMVP.ReviewView) :
    ReviewMVP.ReviewPresenter {
    private var disposable: Disposable? = null
    private val ref = FirebaseDatabase.getInstance().reference

    override fun initData(title: String, rating: Float, productId: Int) {
        viewPresenter.showProgress()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        disposable = getUserName(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val review = Review(null, productId, title, rating, it)
                getReview(review)
            }, {})
    }

    private fun getUserName(userId: String?): Observable<String> {
        return Observable.create {
            val userRef = ref.child("Users").orderByKey().equalTo(userId).limitToFirst(1)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (ds in p0.children) {
                        val username = ds.child("username").value as String
                        it.onNext(username)
                    }
                }
            })
        }
    }


    private fun getReview(review: Review) {
        val reviewRef = ref.child("Reviews").push()
        reviewRef.setValue(review).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewPresenter.hideProgress()
                viewPresenter.onResponse()
            } else {
                viewPresenter.hideProgress()
                viewPresenter.onFail()
            }

        }
    }

    override fun cancel() {
        disposable?.dispose()
    }

}