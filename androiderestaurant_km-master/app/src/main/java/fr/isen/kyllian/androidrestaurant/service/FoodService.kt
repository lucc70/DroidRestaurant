package fr.isen.kyllian.androidrestaurant.service

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.google.gson.Gson
import fr.isen.kyllian.androidrestaurant.model.FoodDataJSON
import fr.isen.kyllian.androidrestaurant.model.FoodJSON
import org.json.JSONObject
import java.util.*

lateinit var foodService :FoodService

class FoodService{
    lateinit var cache : DiskBasedCache
    val network : BasicNetwork = BasicNetwork(HurlStack())
    var queue : RequestQueue
    val params = JSONObject()
    val url = "http://test.api.catering.bluecodegames.com/menu"

    var food_data : FoodDataJSON? = null
    var last_refresh : Date? = null

    constructor(context : Context){
        queue = Volley.newRequestQueue(context).apply { start() }
        params.put("id_shop", "1")
        update()
    }

    fun ping(){}

    fun update() {
        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url,params,
            Response.Listener<JSONObject> { response ->
                Log.i("json","Response is: $response")
                val gson = Gson()
                val json = gson.fromJson(response.toString(), FoodDataJSON::class.java)
                food_data = json
                last_refresh = Date()
            },
            Response.ErrorListener {
                Log.i("test","request failed")
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun ingredientsText(food : FoodJSON) : String{
        var ret = ""
        for (ingredient in food.ingredients){
            ret += ingredient.name_fr.capitalize() + "\n"
        }
        return ret
    }

    fun getFoodById(id : Number) : FoodJSON{
        for(category in food_data?.data!!){
            for(item in category.items){
                if(item.id == id)
                    return item
            }
        }
        return FoodJSON(
            id = -1,
            name_fr = "Salade de Null a l'estragon",
            name_en = "Null food",
            id_category = 0,
            categ_name_fr = "jsp",
            categ_name_en = "no idea",
            images = emptyList(),
            ingredients = emptyList(),
            prices = emptyList()
        );
    }
}