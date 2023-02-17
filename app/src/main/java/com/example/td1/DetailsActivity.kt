package com.example.td1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.td1.databinding.ActivityDetailsBinding
import com.example.td1.databinding.ActivityMenuBinding
import com.squareup.picasso.Picasso
import network.Plate

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getSerializableExtra("list") as? Plate
        modifyQuantity(data)

        binding.name.text=data?.name
        binding.description.text=data?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
        if (data != null) {
            Picasso.get().load(data.images[0]).into(binding.imageDetails)
        }



    }
    fun modifyQuantity(data : Plate?){
        var index=0
        binding.quantity.text=""+index
        binding.buttonAdd.setOnClickListener {
            index++
            binding.quantity.text=""+index
            if (data != null) {
                binding.totalButton.text="TOTAL :"+index*(data.prices[0].price.toInt())+"€"
            }

        }
        binding.buttonMinus.setOnClickListener {
            if(index==0){
                binding.quantity.text=""+index
                binding.totalButton.text="TOTAL : 0€"
            }
            else{
                index--
                binding.quantity.text=""+index
                if (data != null) {
                    binding.totalButton.text="TOTAL : "+index*(data.prices[0].price.toInt())+"€"
                }
            }
        }
    }
}