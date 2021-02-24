package fr.isen.kyllian.androidrestaurant.model
import java.io.Serializable

data class PriceJSON(
    val id:Number,
    val id_pizza:Number,
    val id_size:Number,
    val price:Float,
    val create_date:String,
    val update_date:String,
    val size:String
) : Serializable