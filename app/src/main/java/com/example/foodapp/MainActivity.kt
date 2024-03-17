package com.example.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = ArrayList<SlideModel>()

        val defaultImage = SlideModel(R.drawable.pizza, ScaleTypes.FIT)
        imageList.add(defaultImage)
        imageList.add(SlideModel(R.drawable.barger, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.beriany, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.kfc, ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.goLog.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.cardFast.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","Fast_Food")
            startActivity(intent)
        }
        binding.cardabiriani.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","Beriany")
            startActivity(intent)
        }
        binding.cardsnack.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","snack")
            startActivity(intent)
        }
        binding.carddrink.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","drink")
            startActivity(intent)
        }
        binding.carddesert.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","desert")
            startActivity(intent)
        }
        binding.cardcake.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.putExtra("FOOD_NAME","cake")
            startActivity(intent)
        }
        binding.myOrder.setOnClickListener {
            startActivity(Intent(this,MyOrderActivity::class.java))
        }
        binding.AdminButton.setOnClickListener {
            startActivity(Intent(this,AdminActivity::class.java))
        }

    }
}