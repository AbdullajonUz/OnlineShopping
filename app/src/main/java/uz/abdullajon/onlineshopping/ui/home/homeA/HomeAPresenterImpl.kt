package uz.abdullajon.onlineshopping.ui.home.homeA

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.model.Brand
import uz.abdullajon.onlineshopping.data.model.Product

class HomeAPresenterImpl(private val viewPresenter: HomeAContract.HomeFragmentView) :
    HomeAContract.HomeFragmentPresenter {

    private val cd = CompositeDisposable()
    private val databaseRef = FirebaseDatabase.getInstance().reference
    override fun loadData(categoryId: Int) {
        viewPresenter.showProgress()

        cd.add(
            Observable.zip(
                getBrands(categoryId),
                getProducts(categoryId),
                BiFunction<List<Brand>, List<Product>, Pair<List<Brand>, List<Product>>> { brands, products ->
                    Pair(brands, products)
                })
                .subscribe({
                    viewPresenter.onResponse(it.first, it.second)
                    viewPresenter.hideProgress()
                }, { viewPresenter.hideProgress() })
        )
    }


    private fun getBrands(categoryId: Int): Observable<List<Brand>> {
        return Observable.create<List<Brand>> {
            val brandList = mutableListOf<Brand>()
            val brandRef = databaseRef.child("Brands")
            brandRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (dataSnapshot1 in p0.children) {
                        val brandId = dataSnapshot1.key
                        val brandName = dataSnapshot1.child("BrandName").value as String
                        val brandImage = dataSnapshot1.child("BrandImage").value as String
                        if (brandId != null && brandName.isNotEmpty() && brandImage.isNotEmpty()) {
                            val brand = Brand(brandId.toInt(), brandName, brandImage)
                            brandList.add(brand)
                        }
                    }
                    it.onNext(brandList)
                }
            })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getProducts(categoryId: Int): Observable<List<Product>> {
        return Observable.create<List<Product>> {
            val productList = mutableListOf<Product>()
            val productRef = if (categoryId == 0) {
                databaseRef.child("Products")
            } else {
                databaseRef.child("Products").orderByChild("categoryId")
                    .equalTo(categoryId.toDouble())
            }
            productRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (dataSnapshot1 in p0.children) {
                        val productId = dataSnapshot1.key
                        val productName =
                            dataSnapshot1.child("name").value as String
                        val productPrice = dataSnapshot1.child("price").value as Long
                        val productRating = dataSnapshot1.child("rating").value as Long
                        val productStock = dataSnapshot1.child("stock").value as Long
                        val brandID = dataSnapshot1.child("brandId").value as Long
                        val date = dataSnapshot1.child("date").value as String
                        val categoryID = dataSnapshot1.child("categoryId").value as Long
                        val description = dataSnapshot1.child("description").value as String
                        val imageUrl = dataSnapshot1.child("imageUrl").value as String

                        if (productId != null) {
                            val product = Product(
                                productId.toInt(),
                                productName,
                                productPrice,
                                description,
                                productRating.toFloat(),
                                productStock.toFloat(),
                                date,
                                categoryID.toInt(),
                                brandID.toInt(),
                                imageUrl
                            )
                            productList.add(product)
                        }
                    }
                    it.onNext(productList)
                }

            })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun cancel() {
        cd.dispose()
    }

}
