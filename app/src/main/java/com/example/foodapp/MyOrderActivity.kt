package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.Adapter.FoodAdapter
import com.example.foodapp.Model.Food
import com.example.foodapp.databinding.ActivityMyOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyOrderBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var path :String =""
    private lateinit var adapter: FoodAdapter
    private var mList: ArrayList<Food>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}