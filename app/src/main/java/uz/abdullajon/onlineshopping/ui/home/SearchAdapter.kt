package uz.abdullajon.onlineshopping.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import uz.abdullajon.onlineshopping.data.model.Product

class SearchAdapter(
    context: Context,
    @LayoutRes
    private val layoutRes: Int,
    private val allList: List<Product>
) :
    ArrayAdapter<Product>(context, layoutRes, allList) {
    private var mProductList: List<Product> = allList

    override fun getCount(): Int {
        return mProductList.size
    }

    override fun getItem(p0: Int): Product? {
        return mProductList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return mProductList[p0].productID.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: AppCompatTextView =
            convertView as AppCompatTextView? ?: LayoutInflater.from(context)
                .inflate(layoutRes, parent, false) as AppCompatTextView
        view.text = "${mProductList[position].productName} "
        return view
    }
}