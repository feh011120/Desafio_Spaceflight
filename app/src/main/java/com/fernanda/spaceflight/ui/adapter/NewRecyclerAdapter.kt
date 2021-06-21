package com.fernanda.spaceflight.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernanda.spaceflight.R
import com.fernanda.spaceflight.network.response.NewResponse
import kotlin.properties.Delegates

class NewRecyclerAdapter(private val getNews: (NewResponse) -> Unit) :
        RecyclerView.Adapter<NewRecyclerAdapter.ViewHolder>(), Filterable {

    var items: List<NewResponse> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var newList: List<NewResponse> by Delegates.observable(emptyList()) { _, old, new ->
        if (old != new) notifyDataSetChanged()
    }

    var filterList = ArrayList<NewResponse>()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(newResponse: NewResponse, getNews: (NewResponse) -> Unit) {
            val name = itemView.findViewById<TextView>(R.id.textSite)
            val subTitle = itemView.findViewById<TextView>(R.id.textTitle)
            val infoSpace = itemView.findViewById<TextView>(R.id.infoSpace)
            val image = itemView.findViewById<ImageView>(R.id.imageView2)
            val view = itemView.findViewById<View>(R.id.viewMargi)
            val click = itemView.findViewById<ConstraintLayout>(R.id.itemClick)
            val colors = arrayOf(
                    Color.parseColor("#FF0000"),
                    Color.parseColor("#0080ff"),
                    Color.parseColor("#0000FF"),
                    Color.parseColor("#ff00ff"),
                    Color.parseColor("#8000ff"),
                    Color.parseColor("#00a651"),
                    Color.parseColor("#004e00"),
                    Color.parseColor("#FF00FF"),
                    Color.parseColor("#3c3c3c"),
                    Color.parseColor("#5e0c0f"),
                    Color.parseColor("#d84960"),
                    Color.parseColor("#5cb404"),
                    Color.parseColor("#ff7f00")
            )
            val randomColor = colors.random()


            view.setBackgroundColor(randomColor)

            name.setTextColor(randomColor)
            name.text = newResponse.newsSite

            infoSpace.text = newResponse.summary
            subTitle.text = newResponse.title
            Glide.with(image.context).load(newResponse.imageUrl).into(image)
            click.setOnClickListener {
                getNews(newResponse)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_space, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newResponse = items[position]
        holder.bind(newResponse, getNews)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charResults = constraint.toString()
                filterList = if (charResults.isEmpty()) {
                    newList as ArrayList<NewResponse>
                } else {
                    val resultList = ArrayList<NewResponse>()
                    for (row in newList) {
                        if (row.title?.toLowerCase()?.contains(constraint.toString().toLowerCase())!!)
                            resultList.add(row)
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as List<NewResponse>
                notifyDataSetChanged()
            }
        }
    }
}