package com.example.td1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.example.td1.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import network.Plate
import network.FilePlate
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getSerializableExtra("list") as? Plate
        modifyQuantity(data)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title=data?.name
        supportActionBar?.setLogo(getDrawable(R.drawable.toolbar_shopping_cart))
        supportActionBar?.setDisplayUseLogoEnabled(true);

        binding.name.text=data?.name
        binding.description.text=data?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
        //if (data != null) {
        Picasso.get().load(data?.images?.get(0)).into(binding.imageDetails)
        //}



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
        binding.totalButton.setOnClickListener {
            if (index!=0) {
                createJsonData(data,index)
                val snack =
                    Snackbar.make(it, "Items ajoutés dans votre panier", Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
    }

    private fun createJsonData(data : Plate?, index : Int){


        var json=JSONObject()

        val dataFile = FilePlate(data?.name, ""+index*data?.prices?.get(0)?.price?.toInt() as Int, index)

        json.put("plate",addPlate(dataFile))

        saveJson(json.toString())

    }

    private fun saveJson(jsonString: String) {
        val output:Writer
        val file = createFile()
        output=BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()

    }

    private fun createFile(): File {
        val fileName = "Cart"

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        Log.d("directory", storageDir.toString())
        if (storageDir != null) {
            if(!storageDir.exists()){
                storageDir.mkdir()
            }
        }
        return File.createTempFile(fileName,".json",storageDir)
    }

    private fun addPlate(dataFile: FilePlate): JSONObject {
        return JSONObject()
            .put("name",dataFile.name)
            .put("price",dataFile.price)
            .put("quantity",dataFile.quantity)
    }
}