package fr.isen.kyllian.androidrestaurant.model
import java.io.Serializable

data class FoodDataJSON(
    val data : List<FoodCategoryJSON>
) : Serializable
