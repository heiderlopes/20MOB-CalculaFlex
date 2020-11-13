package br.com.calculaflex.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.calculaflex.R
import br.com.calculaflex.domain.entity.DashboardItem
import com.squareup.picasso.Picasso

class HomeAdapter(
    private var menuItems: List<DashboardItem>,
    private var clickListener: (DashboardItem) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dash_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position], clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DashboardItem, clickListener: (DashboardItem) -> Unit) {
            val label = itemView.findViewById<TextView>(R.id.textView8)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView4)

            label.text = item.label

            Picasso.get()
                .load(item.image)
                .into(imageView)

            itemView.setOnClickListener { clickListener(item) }
        }
    }
}