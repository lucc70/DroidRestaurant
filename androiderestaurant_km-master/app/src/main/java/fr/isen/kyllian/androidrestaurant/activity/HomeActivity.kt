package fr.isen.kyllian.androidrestaurant.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import fr.isen.kyllian.androidrestaurant.databinding.ActivityHomeBinding
import fr.isen.kyllian.androidrestaurant.service.FoodService
import fr.isen.kyllian.androidrestaurant.service.cartService
import fr.isen.kyllian.androidrestaurant.service.foodService
import java.io.File

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivityWMenuBar() {

    private lateinit var btnEntree : android.widget.Button;
    private lateinit var btnPlat : android.widget.Button;
    private lateinit var btnDessert : android.widget.Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        btnEntree  = initMenuButton(binding.btnEntree );
        btnPlat    = initMenuButton(binding.btnPlat   );
        btnDessert = initMenuButton(binding.btnDessert);
        foodService = FoodService(this)
        cartService.sourceFile =  File(cacheDir.absolutePath  + "/../shoppingcart.json")
        cartService.load()
        setContentView(binding.root)
    }

    private fun initMenuButton(btn : android.widget.Button): Button {
        val activity_class = MenuListActivity::class.java;
        btn.setOnClickListener {
            Log.i("Logs","Clicked On '" + btn.text + "', opening view '" + activity_class.canonicalName + "'");
            val intent = Intent(this,activity_class)
            intent.putExtra("category",btn.text)
            startActivity(intent)
        }
        return btn;
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Logs","L'activité home s'est faite detruire :'(")
    }
}