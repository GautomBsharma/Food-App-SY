package com.example.foodapp.Adapter

import android.app.AlertDialog
import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyOrderAdapter(var context: Context,var orderList:ArrayList<MyOrder>) :RecyclerView.Adapter<MyOrderAdapter.MyOrderHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false)
        return MyOrderHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyOrderHolder, position: Int) {
        val data = orderList[position]
        holder.count.text = data.ProductCount.toString()
        holder.state.text = data.ProductStatus
        holder.cancel.setOnClickListener {
            showLoginDialog(data.ProductId)
        }
        holder.price.text = data.price
        holder.restroName.text = data.RestaurantName
        holder.nameFood.text = data.FoodName
        Glide.with(context).load(data.FoodImage).into(holder.imagefood)
    }





    inner class MyOrderHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var imagefood: ImageView = itemView.findViewById(R.id.foodImage)
        var nameFood: TextView = itemView.findViewById(R.id.foodName)
        var restroName: TextView = itemView.findViewById(R.id.restroName)
        var price: TextView = itemView.findViewById(R.id.price)
        var count :TextView= itemView.findViewById(R.id.tvCounttt)
        var state :TextView= itemView.findViewById(R.id.tvState)
        var cancel :TextView= itemView.findViewById(R.id.tvCancel)


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