package com.example.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.Adapter.FoodAdapter
import com.example.foodapp.Model.Admin
import com.example.foodapp.Model.Food
import com.example.foodapp.databinding.ActivityFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private lateinit var db:FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var path :String =""
    private lateinit var adapter: FoodAdapter
    private var mList: ArrayList<Food>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)

        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        path = intent.getStringExtra("FOOD_NAME").toString()
        binding.addthing.setOnClickListener {
            val intent = Intent(this,AddFoodActivity::class.java)
            intent.putExtra("FOOD_NAME",path)
            startActivity(intent)
        }
        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addthing.visibility = View.VISIBLE
                            }

                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.productRecyler.layoutManager= LinearLayoutManager(this)
        mList = ArrayList()
        adapter = FoodAdapter(this, mList!!)
        binding.productRecyler.adapter = adapter
        getData()
        setContentView(binding.root)

    }

    private fun getData() {
        val ref = FirebaseDatabase.getInstance().reference.child("Food").child(path)

        ref.addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Food::class.java)
                        if (data != null) {
                            mList?.add(data)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}