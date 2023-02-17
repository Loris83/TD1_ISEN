package com.example.td1

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.td1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle","MenuActivity onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonListener()

        }
    private fun buttonListener(){
        binding.starterButton.setOnClickListener{
            Log.d("button", "click sur bouton entrée")
            showCategory(Category.STARTER)
        }

        binding.plateButton.setOnClickListener{
            Log.d("button", "click sur bouton plat")
            showCategory(Category.PLATE)
        }

        binding.dessertButton.setOnClickListener{
            Log.d("button", "click sur bouton dessert")
            showCategory(Category.DESSERT)
        }
    }

    private fun showCategory(category: Category){
        val intent= Intent(this,MenuActivity::class.java)
        intent.putExtra(MenuActivity.extraKey, category)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle","MenuActivity onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle","MenuActivity onStart")
    }
}