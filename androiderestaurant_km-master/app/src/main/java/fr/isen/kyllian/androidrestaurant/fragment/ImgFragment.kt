package fr.isen.kyllian.androidrestaurant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.kyllian.androidrestaurant.databinding.FragmentImgBinding


private lateinit var binding: FragmentImgBinding
class ImgFragment(var url : String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentImgBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("URL")?.let {
            Picasso.get().load(it).into(binding.image)
        }
    }

    companion object{
        fun newInstance(picture : String) : ImgFragment {
            return ImgFragment("").apply { arguments= Bundle().apply { putString("URL",picture) } }
        }
    }

}