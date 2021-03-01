package uz.abdullajon.onlineshopping.ui.account.order

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.abdullajon.onlineshopping.data.model.Order
import uz.abdullajon.onlineshopping.data.model.OrderDetail
import uz.abdullajon.onlineshopping.data.model.Payment

class OrderContractPresenterImpl(private val viewPresenter: OrderContract.OrderView) :
    OrderContract.OrderPresenter {

    private val cd = CompositeDisposable()

    override fun loadData() {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.uid

        val reference = FirebaseDatabase.getInstance().reference
        cd.add(
            Observable.create<List<Order>> {
                reference.child("Orders").orderByChild("customerID")
                    .equalTo(userId)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            viewPresenter.onFail(p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val list = mutableListOf<Order>()
                            for (childDataSnapshot in p0.children) {
                                val id = childDataSnapshot.key
                                val address = childDataSnapshot.child("address").value as String
                                val amount = childDataSnapshot.child("amount").value as Long
                                val customerID =
                                    childDataSnapshot.child("customerID").value as String
                                val orderDate = childDataSnapshot.child("orderDate").value as String
                                val orderId = childDataSnapshot.child("orderId").value as String
                                val orderDetailList = mutableListOf<OrderDetail>()
                                val orderListSnapshot =
                                    childDataSnapshot.child("orderList").children
                                for (orderDetail in orderListSnapshot.iterator()) {
                                    val productID =
                                        orderDetail.child("productID").value as Long
                                    val quantity =
                                        orderDetail.child("quantity").value as Long
                                    val productName =
                                        orderDetail.child("productName").value as String
                                    val price = orderDetail.child("price").value as Long
                                    val orderDetailItem = OrderDetail(
                                        productID,
                                        productName,
                                        price,
                                        quantity
                                    )
                                    orderDetailList.add(orderDetailItem)
                                }
                                val shipDate = childDataSnapshot.child("shipDate").value as String
                                val state = childDataSnapshot.child("state").value as String
                                val paymentType = childDataSnapshot.child("payment")
                                    .child("paymentType").value as String
                                val paid =
                                    childDataSnapshot.child("payment")
                                        .child("paid").value as Boolean
                                val cardNumber = childDataSnapshot.child("payment")
                                    .child("cardNumber").value as String
                                val cardName = childDataSnapshot.child("payment")
                                    .child("cardName").value as String
                                val payment = Payment(paymentType, paid, cardNumber, cardName)
                                val order: Order = Order(
                                    customerID,
                                    amount,
                                    state,
                                    orderDate,
                                    shipDate,
                                    payment,
                                    orderDetailList,
                                    address,
                                    orderId
                                )
                                order.orderId = id!!
                                list.add(order)
                            }
                            it.onNext(list)
                        }
                    })
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewPresenter.getResponse(it)
                }, { viewPresenter.onFail("${it.message}") })
        )
    }

    override fun onCancel() {

    }
}