package com.example.foodapp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.LoginActivity
import com.example.foodapp.Model.Food
import com.example.foodapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView

class FoodAdapter (private val context: Context, private val mList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodAdapter.FoodHolder>() {
    var count = 1
    private var auth = FirebaseAuth.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return FoodHolder(view)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        // Bind data to views here
        val datt = mList[position]
        holder.nameFood.text = datt.FoodName
        holder.restroName.text = datt.RestaurantName
        holder.price.text = datt.price +"Tk"
        holder.tvcount.text = count.toString()
        holder.offers.text = datt.Offer + "% Off"
        if (datt.FoodImage.isNotEmpty()) {
            Glide.with(context)
                .load(datt.FoodImage)
                //
                .into(holder.imagefood)
        }
        holder.plus.setOnClickListener {
        if (count<=8){
            count++
            holder.tvcount.text = count.toString()
        }
            else{
            Toast.makeText(context, "Limit out", Toast.LENGTH_SHORT).show()
        }
        }
        holder.minus.setOnClickListener {
            if (count>=1){
                count--
                holder.tvcount.text = count.toString()
            }
            else{
                Toast.makeText(context, "select At last one item ", Toast.LENGTH_SHORT).show()
            }
        }
        holder.addCart.setOnClickListener {
            if (isUserAuthenticated()) {
                // If authenticated, show the dialog
                showConfirmationDialog(datt.FoodID,count,datt.Category,datt.FoodImage,datt.FoodName,datt.RestaurantName,datt.price)
            } else {
                // If not authenticated, simply add the item to the cart
                showLoginDialog()
            }
        }


    }
    private fun showConfirmationDialog(
        foodID: String,
        count: Int,
        category: String,
        foodImage: String,
        foodName: String,
        restaurantName: String,
        price: String
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to add this item to the cart?")
        builder.setPositiveButton("Confirm") { dialog, which ->
            // If the user confirms, add the item to the cart
            val auth = FirebaseAuth.getInstance()
            val ref = FirebaseDatabase.getInstance().reference.child("ProductCart")

            val productMap = HashMap<String,Any>()
            productMap["ProductId"] = foodID
            productMap["ProductCount"] = count.toString()
            productMap["ProductCategory"] = category
            productMap["ProductStatus"] = "Just Ordered"
            productMap["ProductBayer"] = auth.currentUser?.uid.toString()
            productMap["FoodImage"] = foodImage
            productMap["FoodName"] = foodName
            productMap["RestaurantName"] = restaurantName
            productMap["price"] = price


            ref.child(foodID).setValue(productMap).addOnSuccessListener {
                Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()
            }


        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // If the user cancels, do nothing
            //dialog.dismiss()
        }
        builder.show()
    }
    private fun showLoginDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Authentication")
        builder.setMessage("You are not currently Sign in / or not sign Up please go to login page!!")
        builder.setPositiveButton("Go to Sign In page") { dialog, which ->
            // If the user confirms, add the item to the cart
            context.startActivity(Intent(context,LoginActivity::class.java))

        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // If the user cancels, do nothing
            //dialog.dismiss()
        }
        builder.show()
    }




    override fun getItemCount(): Int {
        // Return the size of your dataset
        return mList.size
    }

    inner class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagefood: ImageView = itemView.findViewById(R.id.foodImage)
        var nameFood: TextView = itemView.findViewById(R.id.foodName)
        var restroName: TextView = itemView.findViewById(R.id.restroName)
        var price: TextView = itemView.findViewById(R.id.price)
        var offers: TextView = itemView.findViewById(R.id.tvoff)
        var addCart : CircleImageView = itemView.findViewById(R.id.addtocart)
        var plus : ImageView = itemView.findViewById(R.id.imPlus)
        var minus : ImageView = itemView.findViewById(R.id.imMinus)
        var tvcount : TextView = itemView.findViewById(R.id.tvcount)

    }

    private fun isUserAuthenticated(): Boolean {
        val auth = FirebaseAuth.getInstance()


        return auth.currentUser != null

    }

}