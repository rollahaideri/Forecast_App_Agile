package com.example.forecastappagile


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: ArrayList<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }



    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view, mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        println(ItemsViewModel )

        // sets the image to the imageview from our itemHolder class
        holder.cityName.text = ItemsViewModel.cityName

        // sets the text to the textview from our itemHolder class
        holder.temp.text = ItemsViewModel.temp

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to text
     class ViewHolder(ItemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val temp: TextView = itemView.findViewById(R.id.temp)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }


    }
}
