package com.example.td1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.td1.databinding.ActivityMenuBinding
import com.google.gson.GsonBuilder
import network.MenuResult
import network.NetworkConstants
import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList

enum class Category {STARTER, PLATE, DESSERT}

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var currentCategory: Category

    companion object{
        val extraKey = "extraKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category=intent.getSerializableExtra(extraKey) as? Category
        currentCategory = category ?: Category.STARTER

        //binding.menuTitle.text=categoryName(category ?: Category.STARTER)
        supportActionBar?.title=categoryName()//(category ?: Category.STARTER)
        //if category is null then it takes the value of Category.STARTER
        //showDatas()
        //buttonListener()
        makeRequest()
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstants.idShopKey, 1)
        val request = JsonObjectRequest(
            com.android.volley.Request.Method.POST,
            NetworkConstants.url,
            params,
            {result ->
                //SUCCESS OF REQUEST
                Log.d("requests", result.toString(2))
                parseData(result.toString())
            },
            {error ->
                //ERROR WHEN REQUEST
                Log.e("requests",error.toString())
            }
        )
        queue.add(request)
        //showDatas()
    }

    private fun parseData(data: String){
        val result = GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first{it.name == categoryFilterKey()}
        showDatas(category)
    }

    private fun showDatas(category: network.Category) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(category.items) {
            val intent = Intent(this, DetailsActivity::class.java)
            /*intent.putExtra("item_name",it.name)
            intent.putExtra("item_ingredients",it.ingredients[0].name)*/
            intent.putExtra("item_picture",it.images[0])
            intent.putExtra("list",it)

            startActivity(intent)
        }
    }

    private fun categoryName(): String{
        return when(currentCategory){
            Category.STARTER -> getString(R.string.starterButton)
            Category.PLATE -> getString(R.string.plateButton)
            Category.DESSERT -> getString(R.string.dessertButton)
        }
    }

    private fun categoryFilterKey(): String{
        return when(currentCategory){
            Category.STARTER -> "Entrées"
            Category.PLATE -> "Plats"
            Category.DESSERT -> "Desserts"
        }
    }

    /*private fun showDetails(){
        val intent= Intent(this,DetailsActivity::class.java)
        startActivity(intent)
    }*/
}