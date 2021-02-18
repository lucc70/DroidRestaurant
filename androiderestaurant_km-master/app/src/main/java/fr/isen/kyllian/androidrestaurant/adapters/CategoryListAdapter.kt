package fr.isen.kyllian.androidrestaurant.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import fr.isen.kyllian.androidrestaurant.activity.ItemActivity
import fr.isen.kyllian.androidrestaurant.databinding.CategoryCellBinding
import fr.isen.kyllian.androidrestaurant.model.FoodJSON
import fr.isen.kyllian.androidrestaurant.service.cartService

class CategoryListAdapter(private val foods : List<FoodJSON>) : RecyclerView.Adapter<CategoryListAdapter.CategoryHolder>() {
    private var context : Context? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ):CategoryHolder {
        this.context = parent.context;
        val itemBinding = CategoryCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(itemBinding)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val food = foods[position]
        firebaseAnalytics = Firebase.analytics

        holder.title.text = food.name_fr
        holder.prix.text = "${food.prices[0].price} €"
        holder.image.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("id",foods[position].id)
            context?.startActivity(intent)

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_NAME, food.name_fr)
                param("ITEM_PRICES",food.prices[0].price.toString())
            }
        }
        if(food.images.size > 0 && food.images[0].isNotBlank())
            Picasso.get().load(food.images[0]).into(holder.image)

        val qtt = cartService.howManyOf(food.id)
        if (qtt > 0 )
            holder.qtt.text = "x" + qtt
        else
            holder.qtt.text = ""
    }

    class CategoryHolder(binding : CategoryCellBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.nomPlat
        val image = binding.imagePlat
        val prix = binding.prixPlat
        val qtt = binding.qtt
    }

}