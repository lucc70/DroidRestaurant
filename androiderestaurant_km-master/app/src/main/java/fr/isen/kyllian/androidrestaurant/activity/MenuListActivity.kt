package fr.isen.kyllian.androidrestaurant.activity

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import fr.isen.kyllian.androidrestaurant.adapters.CategoryListAdapter
import fr.isen.kyllian.androidrestaurant.databinding.ActivityMenuListBinding
import fr.isen.kyllian.androidrestaurant.service.foodService

private lateinit var binding: ActivityMenuListBinding

class MenuListActivity : AppCompatActivityWMenuBar() {
    var category :String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuListBinding.inflate(layoutInflater)
        this.category = intent.getStringExtra("category");
        binding.menuName.text = this.category
        binding.listCategory.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        var numcat = 0;
        when(category){
            "Entrées" -> numcat = 0
            "Plats" -> numcat = 1
            "Desserts" -> numcat = 2
        }

        if(foodService.food_data != null)
            binding.listCategory.adapter = CategoryListAdapter(foodService.food_data!!.data[numcat].items)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.listCategory.adapter?.notifyDataSetChanged()
    }
}