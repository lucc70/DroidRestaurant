package fr.isen.kyllian.androidrestaurant.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.kyllian.androidrestaurant.fragment.ImgFragment

class DetailViewAdapter (activity : AppCompatActivity,val itemlist : List<String>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImgFragment.newInstance(itemlist[position]) as Fragment
    }
}