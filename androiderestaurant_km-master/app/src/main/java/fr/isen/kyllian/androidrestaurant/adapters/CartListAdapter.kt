package fr.isen.kyllian.androidrestaurant.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.kyllian.androidrestaurant.databinding.CartCellBinding
import fr.isen.kyllian.androidrestaurant.service.CartService
import fr.isen.kyllian.androidrestaurant.service.cartService
import fr.isen.kyllian.androidrestaurant.service.foodService

class CartListAdapter(private val lots : List<CartService.ItemLot>) : RecyclerView.Adapter<CartListAdapter.CategoryHolder>() {
    private var context : Context? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ):CategoryHolder {
        this.context = parent.context;
        val itemBinding = CartCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(itemBinding)
    }

    override fun getItemCount(): Int = lots.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val lot = lots[position]
        val food = foodService.getFoodById(lot.food_id)
        holder.title.text = food.name_fr
        holder.qtt.text = lot.qtt.toString()
        holder.prix.text = "${lot.qtt * food.prices[0].price} €"
        holder.image.setOnClickListener {
            /*val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("id",food.id)
            context?.startActivity(intent)*/
        }
        holder.btn_remove.setOnClickListener {
            Log.i("logs","removing item ${food.name_fr} from cart")
            cartService.remove(lot.food_id,lot.qtt)
            notifyDataSetChanged()
            Log.i("logs","removed item ${food.name_fr} from cart")
        }
        if(food.images.size > 0 && food.images[0].isNotBlank())
            Picasso.get().load(food.images[0]).into(holder.image)
    }

    class CategoryHolder(binding : CartCellBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.title
        val image = binding.image
        val prix = binding.price
        val qtt = binding.qtt
        val btn_remove = binding.btnRemove
    }

}