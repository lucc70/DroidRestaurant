package fr.isen.kyllian.androidrestaurant.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import fr.isen.kyllian.androidrestaurant.adapters.DetailViewAdapter
import fr.isen.kyllian.androidrestaurant.databinding.ActivityItemBinding
import fr.isen.kyllian.androidrestaurant.model.FoodJSON
import fr.isen.kyllian.androidrestaurant.service.cartService
import fr.isen.kyllian.androidrestaurant.service.foodService

private lateinit var binding: ActivityItemBinding;
private lateinit var firebaseAnalytics: FirebaseAnalytics

class ItemActivity : AppCompatActivityWMenuBar() {
    private lateinit var item : FoodJSON

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        firebaseAnalytics = Firebase.analytics
        item = foodService.getFoodById(intent.getIntExtra(("id"),-1))

        binding.itemName.text = item.name_fr
        if(item.images.size > 0 && item.images[0].isNotBlank()) {
            Picasso.get().load(item.images[0]).into(binding.foodImage)
            var it = item.images
            binding.vierwpager.adapter = DetailViewAdapter(this, it)
        }
        binding.price.text = "${item.prices[0].price}€"
        binding.totalsum.text = "0€"
        binding.categoryName.text = item.categ_name_fr
        binding.itemDesc.text = foodService.ingredientsText(item)
        binding.btnBuy.setOnClickListener {
            if(binding.qttInput.text.isNotBlank()) {
                val nb = binding.qttInput.text.toString().toInt()
                cartService.add(item, nb)
                Log.i("logs", "added $nb of ${item.name_fr} to cart, cart total is now ${cartService.getFullPrice()} euros")
                refreshCart()
                setQttMessage()
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                    param(FirebaseAnalytics.Param.ITEM_NAME,item.name_fr)
                    param("ITEM_COUNT",nb.toString())
                    param("ITEM_TYPE",item.categ_name_fr)
                    param("ITEM_PRICE",item.prices[0].price.toString())
                    param("ITEM_TTPRICE","${item.prices[0].price*nb}")
                }
            }
        }
        setQttMessage()

        binding.qttInput.setOnKeyListener { _: View, _: Int, _: KeyEvent ->
            try {
                val op = item.prices[0].price.toFloat() * binding.qttInput.text.toString().toFloat()
                binding.totalsum.text = "$op €"
            } catch (e: NumberFormatException) {
                //Not a number inputed
                binding.totalsum.text = "0€"
            }
            false
        }

        setContentView(binding.root);
    }

    fun setQttMessage(){
        val nb = cartService.howManyOf(item.id)
        if(nb > 0){
            binding.warning.text = "⚠️Deja $nb exemplaires dans le panier ⚠️"
        }
        else
            binding.warning.text = ""
    }
}

