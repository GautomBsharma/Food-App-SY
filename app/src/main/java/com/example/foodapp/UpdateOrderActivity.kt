package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodapp.databinding.ActivityUpdateOrderBinding
import com.google.firebase.database.FirebaseDatabase

class UpdateOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pid = intent.getStringExtra("FOOD_ID")
        val ref = pid?.let { FirebaseDatabase.getInstance().reference.child("ProductCart").child(it) }
        binding.button.setOnClickListener {
            val updateMap = HashMap<String, Any>()
            updateMap["ProductStatus"] = binding.updatetext.text.toString()
            ref?.updateChildren(updateMap)?.addOnSuccessListener {
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
                finish()

            }?.addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(this, "Update Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}