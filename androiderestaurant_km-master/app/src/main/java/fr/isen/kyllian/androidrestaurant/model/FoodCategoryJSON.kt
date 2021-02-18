package fr.isen.kyllian.androidrestaurant.model
import java.io.Serializable

data class FoodCategoryJSON(
    val name_fr : String,
    val name_en : String,
    val items : List<FoodJSON>
) : Serializable