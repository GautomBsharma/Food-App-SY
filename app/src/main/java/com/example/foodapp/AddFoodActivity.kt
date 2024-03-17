package com.example.foodapp

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodapp.databinding.ActivityAddFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var path :String =""
    private var imageUri : Uri?=null
    private var launchGelaryActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data!!.data
            binding.postImage.setImageURI(imageUri)
            binding.postImage.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        path = intent.getStringExtra("FOOD_NAME").toString()

        auth = Firebase.auth
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(true)
        binding.pickBtn.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGelaryActivity.launch(intent)

        }
        binding.postBtn.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        if (binding.tvdisName.text.toString().isEmpty()){
            binding.tvdisName.error = "Enter Food Name"

        }
        else if (binding.restrodentName.text.toString().isEmpty()){
            binding.restrodentName.error = "Enter Restaurant Name"

        }
        else if (binding.edPrice.text.toString().isEmpty()){
            binding.edPrice.error = "Enter Price"

        }
        else if (binding.edOffers.text.toString().isEmpty()){
            binding.edOffers.error = "Enter Offer"

        }
        else if (imageUri.toString() == ""){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show()

        }
        else {
            uploadImages(imageUri!!)
            dialog.show()
        }


    }


    private fun uploadImages(uri: Uri) {

        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("FoodImage").child(fileName)
        storageRef.putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {image->
                storeData(image.toString())
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Storage Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }
    private fun storeData(imageUrl: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Food").child(path)
        val postId= dbRef.push().key
        val updateMap = HashMap<String,Any>()
        updateMap["FoodImage"] = imageUrl
        updateMap["FoodID"] = postId.toString()
        updateMap["FoodName"] = binding.tvdisName.text.toString()
        updateMap["RestaurantName"] = binding.restrodentName.text.toString()
        updateMap["price"] = binding.edPrice.text.toString()
        updateMap["Offer"] = binding.edOffers.text.toString()
        updateMap["Category"] = path


        if (postId != null) {
            dbRef.child(postId).setValue(updateMap).addOnSuccessListener {
                Toast.makeText(this, "add successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }
        }
    }
}