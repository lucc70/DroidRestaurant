package fr.isen.kyllian.androidrestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.kyllian.androidrestaurant.adapters.CartListAdapter
import fr.isen.kyllian.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.kyllian.androidrestaurant.service.cartService

private lateinit var binding: ActivityCartBinding
class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        if(cartService.lots != null)
            binding.recycler.adapter = CartListAdapter(cartService.lots.items)
        setContentView(binding.root)
    }
}
