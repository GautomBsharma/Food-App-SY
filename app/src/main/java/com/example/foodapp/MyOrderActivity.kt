package com.example.foodapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.Adapter.MyOrderAdapter
import com.example.foodapp.Model.MyOrder
import com.example.foodapp.databinding.ActivityMyOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyOrderBinding
    private lateinit var adapter: MyOrderAdapter
    private lateinit var orderList:ArrayList<MyOrder>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        orderList = ArrayList()
        adapter = MyOrderAdapter(this,orderList)
        binding.orderRecyler.layoutManager = LinearLayoutManager(this)
        binding.orderRecyler.adapter = adapter
        getdata()
    }

    private fun getdata() {
        val uid = auth.currentUser?.uid.toString()
        val ref = FirebaseDatabase.getInstance().reference.child("ProductCart")
        ref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    orderList.clear()
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(MyOrder::class.java)
                        if (data != null) {
                            if (uid == data.ProductBayer) {
                                orderList.add(data)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }
}