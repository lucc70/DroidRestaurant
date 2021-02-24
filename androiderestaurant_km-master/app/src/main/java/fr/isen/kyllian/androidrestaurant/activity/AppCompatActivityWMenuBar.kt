package fr.isen.kyllian.androidrestaurant.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import fr.isen.kyllian.androidrestaurant.R
import fr.isen.kyllian.androidrestaurant.service.cartService

abstract class AppCompatActivityWMenuBar : AppCompatActivity() {
    var top_menu : Menu? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_button,menu)
        top_menu = menu
        refreshCart()
        return true;
    }

    fun refreshCart(){
        if(top_menu != null) {
            top_menu!!.findItem(R.id.num).title = cartService.getNumItems().toString()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.goto_cart -> {
                val panierActivity = Intent(this, CartActivity::class.java)
                startActivity(panierActivity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}