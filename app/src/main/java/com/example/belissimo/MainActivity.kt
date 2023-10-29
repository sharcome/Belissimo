package com.example.belissimo

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.allenliu.badgeview.BadgeFactory
import com.allenliu.badgeview.BadgeView
import com.example.belissimo.adapters.BottomAdapters
import com.example.belissimo.adapters.ProductsAdapter
import com.example.belissimo.data.PreferenceManager
import com.example.belissimo.databinding.ActivityMainBinding
import com.example.belissimo.`interface`.BottomInterface
import com.example.belissimo.models.BottomItemModel
import com.example.belissimo.models.ProductSize
import com.example.belissimo.models.ProductsItemModel
import com.example.belissimo.utils.LanguageUtils
import com.google.firebase.database.*
import java.util.*

class MainActivity : AppCompatActivity(),BottomInterface {

    val list = arrayListOf<BottomItemModel>()
    lateinit var preferenceManager : PreferenceManager
    lateinit var databaseReference: DatabaseReference
    val save_order = arrayListOf<ProductsItemModel>()

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            save_order.clear()
            for (ds in  snapshot.children){
                val model = ds.getValue(ProductsItemModel::class.java)
                model?.let { save_order.add(it) }
            }

            BadgeFactory.create(this@MainActivity)
                .setTextColor(Color.WHITE)
                .setWidthAndHeight(20, 20)
                .setBadgeBackground(Color.BLUE)
                .setTextSize(10)
                .setBadgeGravity(Gravity.END or Gravity.TOP)
                .setBadgeCount(save_order.size)
                .setShape(BadgeView.SHAPE_CIRCLE)
                .setSpace(5, 5)
                .bind(viewBinding.buttonShop)

        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    val productsList = arrayListOf<ProductsItemModel>()
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        preferenceManager = PreferenceManager(this@MainActivity)
        preferenceManager.getLanguage()?.let { setLocale(this, it) }
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)



        viewBinding.btnMenu.setOnClickListener {
            viewBinding.layout.open()
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("order")

        databaseReference.addValueEventListener(valueEventListener)

        settList()


        viewBinding.recyclerView.adapter = BottomAdapters(list,this)
        val layoutManager = LinearLayoutManager(this)
        viewBinding.productRecyclerview.layoutManager = layoutManager
        viewBinding.btnRu.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnRu.setBackgroundResource(R.drawable.background_shape_selector)

        if (preferenceManager.getLanguage() == "uz"){

            viewBinding.btnUzb.isSelected = true
        }else{
            viewBinding.btnRu.isSelected = true

        }
        viewBinding.btnUzb.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnUzb.setBackgroundResource(R.drawable.background_shape_selector)

        viewBinding.btnUzb.setOnClickListener {
            setLanguage("uz")
            it.isSelected = !it.isSelected
            viewBinding.btnRu.isSelected = false
        }
        viewBinding.btnRu.setOnClickListener {
            setLanguage("ru")

            it.isSelected = !it.isSelected
            viewBinding.btnUzb.isSelected = false
        }



// Replace this with the number entered by the user
        val positionToScrollTo = 5

        viewBinding.productRecyclerview.adapter = ProductsAdapter(productsList)


    }

    private fun setLanguage(s: String) {
        LanguageUtils.setLocale(this,s, preferenceManager = preferenceManager)


    }
    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)

        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )
    }

    private fun settList() {
        list.add(BottomItemModel(getString(R.string.pitsa), R.drawable.img_4))
        list.add(BottomItemModel(getString(R.string.gazaklar), R.drawable.img_1))
        list.add(BottomItemModel(getString(R.string.ichimliklar), R.drawable.img))
        list.add(BottomItemModel(getString(R.string.salatlar), R.drawable.img_3))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.img_3))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.img_3))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.img_3))


        productsList.add(
            ProductsItemModel(
                "To'vuqli donar",
                R.drawable.tovuqli_donar,
                60000,
                "Pitsa",
                "Yumshoq xamir, tovuq donar go'shti, ayzerbek karami, pishloq piyoz pomidor",
                ProductSize(true, 60000, "Kichik"),
                ProductSize(true, 72000, "O'rtacha"),
                ProductSize(true, 85000, "Katta")
            )
        )
        productsList.add(
            ProductsItemModel(
                "Pishloqli pitsa",
                R.drawable.pishloqli_pitsa,
                39000,
                "Pitsa",
                "Haqiqiy motsarella firmenniy va alfreddo sousi bilan uyg'unlikda",
                ProductSize(true, 39000, "Kichik"),
                ProductSize(true, 65000, "O'rtacha"),
                ProductSize(true, 95000, "Katta")
            )
        )
        productsList.add(
            ProductsItemModel(
                "Kartoshka fri",
                R.drawable.img_8,
                16000,
                "Gazaklar",
                "Pechdan yangi uzilgan, qarsildoq kartoshka fri",
                ProductSize(true, 16000, "Kichik"),
                ProductSize(false, 0, "O'rtacha"),
                ProductSize(false, 0, "Katta")
            )
        )

        productsList.add(
            ProductsItemModel(
                "Po-Derevenski kartoshkasi",
                R.drawable.img_9,
                16000,
                "Gazaklar",
                "Pechdan yangi uzilgan, qarsildoq kartoshka fri",
                ProductSize(true, 16000, "Kichik"),
                ProductSize(false, 0, "O'rtacha"),
                ProductSize(false, 0, "Katta")
            )
        )

        productsList.add(
            ProductsItemModel(
                "Coca-Cola", R.drawable.img_10, 8000, "Ichimliklar", "",
                ProductSize(true, 8000, "Kichik"),
                ProductSize(true, 11000, "O'rtacha"),
                ProductSize(true, 18000, "Katta")
            )
        )
        productsList.add(
            ProductsItemModel(
                "Fanta", R.drawable.img_11, 8000, "Ichimliklar", "",
                ProductSize(true, 8000, "Kichik"),
                ProductSize(true, 11000, "O'rtacha"),
                ProductSize(true, 18000, "Katta")
            )
        )
    }

    override fun clickBottom(categories: String) {
        val index = productsList.indexOfFirst { it.categoryName == categories }
        if (index>= 0){

            viewBinding.productRecyclerview.smoothScrollToPosition(index)
        }
    }

}

