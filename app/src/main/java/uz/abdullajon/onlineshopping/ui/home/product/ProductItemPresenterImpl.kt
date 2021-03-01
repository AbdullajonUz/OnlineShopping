package uz.abdullajon.onlineshopping.ui.home.product

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.database.ShopRoomDatabase
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.data.model.Review

class ProductItemPresenterImpl(private val viewPresenter: ProductItemContract.ProductItemView) :
    ProductItemContract.ProductItemPresenter {
    private val cd = CompositeDisposable()
    private val databaseRef = FirebaseDatabase.getInstance().reference


    override fun loadData(id: Int) {
        viewPresenter.showProgress()
        Observable.zip(
            getProducts(id),
            getReviews(id),
            BiFunction<Product?, List<Review>, Unit> { product, reviews ->
                viewPresenter.hideProgress()
                viewPresenter.onResponse(product, reviews)
            }).subscribe()
    }

    override fun cancel() {
        cd.dispose()
    }

    private fun getProducts(id: Int): Observable<Product> {
        return Observable.create<Product> {
            var product: Product
            val productRef = databaseRef.child("Products")

            productRef.orderByKey()
                .equalTo(id.toString())
                .limitToFirst(1)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val price = ds.child("price").value as Long
                            val date = ds.child("date").value as String
                            val description = ds.child("description").value as String
                            val name = ds.child("name").value as String
                            val rating = ds.child("rating").value as Long
                            val stock = ds.child("stock").value as Long
                            val imageUrl = ds.child("imageUrl").value as String


                            product = Product(
                                id,
                                name,
                                price,
                                description,
                                rating.toFloat(),
                                stock.toFloat(),
                                date,
                                0,
                                0,
                                imageUrl
                            )
                            it.onNext(product)
                        }
                    }
                })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getReviews(id: Int): Observable<List<Review>> {

        return Observable.create<List<Review>> {

            val reviewList = mutableListOf<Review>()

            val reviewRef = databaseRef.child("Reviews").orderByChild("productId")
                .equalTo(id.toDouble())

            reviewRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(ds: DataSnapshot) {
                    for (reviews in ds.children) {
                        val idReviews = reviews.key
                        val proRating = reviews.child("rating").value
                        val title = reviews.child("title").value as String
                        val userName = reviews.child("customerName").value as String
                        if (idReviews != null) {
                            val reviewItem =
                                Review(
                                    idReviews,
                                    id,
                                    title,
                                    proRating.toString().toFloat(),
                                    userName
                                )
                            reviewList.add(reviewItem)
                        }


                    }
                    it.onNext(reviewList)
                }

            })
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addCartProduct(cartModel: CartModel, context: Context) {
        val roomDatabase: ShopRoomDatabase = ShopRoomDatabase.getInstance(context)
        cd.add(
            Observable.just(roomDatabase.getCartDao().addCartProduct(cartModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewPresenter.successAddProduct()
                }, {})
        )
    }

}