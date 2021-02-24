package fr.isen.kyllian.androidrestaurant.service
import android.util.Log
import com.google.gson.Gson
import fr.isen.kyllian.androidrestaurant.model.FoodJSON
import java.io.File

val cartService :CartService = CartService();
class CartService {
    data class ItemLot(val food_id : Int, var qtt : Int);
    class ItemLots(){
        var items : ArrayList<ItemLot> =  ArrayList()
    }

    var lots : ItemLots = ItemLots()
    var sourceFile : File? = null

    fun add(food: FoodJSON,qtt: Int){
        var added = false
        for (lot in lots.items){
            if(food.id == lot.food_id){
                lot.qtt += qtt
                added = true
                break;
            }
        }
        if(!added)
            lots.items.add(ItemLot(food.id,qtt))
        save()
    }

    fun remove(id : Int,qtt : Int){
        for (lot in lots.items){
            if(id == lot.food_id){
                if(qtt >= lot.qtt){
                    lots.items.remove(lot)
                }
                else{
                    lot.qtt = lot.qtt - qtt
                }
                break;
            }
        }
        save()
    }

    fun getFullPrice() : Float{
        var tot : Float = 0F;
        for (item in lots.items){
            tot += foodService.getFoodById(item.food_id).prices[0].price.toFloat() * item.qtt;
        }
        return tot;
    }

    fun save(){
        Log.i("logs","saving cart to " + (sourceFile?.absoluteFile ?: "null"))
        sourceFile?.writeText(Gson().toJson(lots))
    }

    fun load(){
        if(sourceFile?.exists()!!) {
            lots = Gson().fromJson(sourceFile?.readText(), ItemLots::class.java)
            Log.i("logs","loaded " + sourceFile!!.readText())
        }
        else{
            Log.i("logs","cart file ${sourceFile!!.absoluteFile} doesn't exist")
        }
    }

    fun getNumItems() : Int{
        var sum : Int = 0
        for(lot in lots.items){
            sum += lot.qtt
        }
        return sum
    }

    fun howManyOf(id: Int) : Int{
        for(lot in lots.items){
            if(lot.food_id == id)
                return lot.qtt
        }
        return 0
    }
}