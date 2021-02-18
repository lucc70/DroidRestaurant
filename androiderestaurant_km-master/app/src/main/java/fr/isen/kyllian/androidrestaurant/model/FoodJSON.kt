package fr.isen.kyllian.androidrestaurant.model
import java.io.Serializable

data class FoodJSON(
    val id : Int,
    val name_fr: String,
    val name_en: String,
    val id_category:Number,
    val categ_name_fr:String,
    val categ_name_en:String,
    val images: List<String>,
    val ingredients: List<IngredientsJSON>,
    val prices: List<PriceJSON>
) : Serializable