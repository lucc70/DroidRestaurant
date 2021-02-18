package fr.isen.kyllian.androidrestaurant.model
import java.io.Serializable

data class IngredientsJSON(
    val id:Number,
    val id_shop:Number,
    val name_fr:String,
    val name_en:String,
    val create_date:String,
    val update_date:String,
    val id_pizza:Number
) : Serializable