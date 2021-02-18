package fr.isen.kyllian.androidrestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import fr.isen.kyllian.androidrestaurant.adapters.CartListAdapter
import fr.isen.kyllian.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.kyllian.androidrestaurant.service.cartService
import fr.isen.kyllian.androidrestaurant.service.foodService

private lateinit var binding: ActivityCartBinding
private lateinit var firebaseAnalytics: FirebaseAnalytics

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        if(cartService.lots != null)
            binding.recycler.adapter = CartListAdapter(cartService.lots.items)

        setContentView(binding.root)
        binding.btnBuy.setOnClickListener {
            var ttPrice = 0.0
            for (e in cartService.lots.items){
                val food = foodService.getFoodById(e.food_id)
                ttPrice += food.prices[0].price*e.qtt.toDouble()
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_NAME, "OrderButton")
                param("ITEM_TTPRICE",ttPrice)
            }
        }
    }
}
