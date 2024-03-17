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
import com.example.foodapp.Model.MyOrder
import com.example.foodapp.R
import com.example.foodapp.UpdateOrderActivity
import com.google.firebase.database.FirebaseDatabase

class AdminOrderAdapter (var context: Context, var orderList:ArrayList<MyOrder>) : RecyclerView.Adapter<AdminOrderAdapter.AdminOrderHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.admin_order_layout,parent,false)
        return AdminOrderHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: AdminOrderHolder, position: Int) {
        val data = orderList[position]
        holder.count.text = data.ProductCount.toString()
        holder.state.text = data.ProductStatus
        holder.update.setOnClickListener {
            val intent = Intent(context,UpdateOrderActivity::class.java)
            intent.putExtra("FOOD_ID",data.ProductId)
            context.startActivity(intent)
        }
        holder.price.text = data.price
        holder.restroName.text = data.RestaurantName
        holder.nameFood.text = data.FoodName
        Glide.with(context).load(data.FoodImage).into(holder.imagefood)
    }





    inner class AdminOrderHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var imagefood: ImageView = itemView.findViewById(R.id.foodImage)
        var nameFood: TextView = itemView.findViewById(R.id.foodName)
        var restroName: TextView = itemView.findViewById(R.id.restroName)
        var price: TextView = itemView.findViewById(R.id.price)
        var count : TextView = itemView.findViewById(R.id.tvCounttt)
        var state : TextView = itemView.findViewById(R.id.tvStatus)
        var update : TextView = itemView.findViewById(R.id.tvUpdate)


    }

    private fun showLoginDialog(productId: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Cancel Order")
        builder.setMessage("You are not currently Sign in / or not sign Up please go to login page!!")
        builder.setPositiveButton("Confirm") { dialog, which ->
            // If the user confirms, add the item to the cart
            val reff = FirebaseDatabase.getInstance().reference.child("ProductCart").child(productId)
            reff.removeValue().addOnSuccessListener {
                Toast.makeText(context, "Order Canceled", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(context, "You did not cancel Order", Toast.LENGTH_SHORT).show()
                }

        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // If the user cancels, do nothing
            //dialog.dismiss()
        }
        builder.show()
    }

}