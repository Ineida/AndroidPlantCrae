package com.projectPlant.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectPlant.R
import com.projectPlant.model.Plant

class PlantAdapter : RecyclerView.Adapter<PlantAdapter.MyViewHolder>() {
    var seeClick: ((Plant) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btEnable: Button = itemView.findViewById(R.id.bt_enable_item)
        val btColor: Button = itemView.findViewById(R.id.bt_color__item)
        val plantName: TextView = itemView.findViewById(R.id.tv_name_item)
        val plantIcon: ImageView = itemView.findViewById(R.id.tv_id_item)
    }

    var data = listOf<Plant>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = data[position]

        holder.plantName.text = item.plantByIdplant.name
        ///val colors   = R.array.colors
        var color: Int
        var decalageH: Double =
            item.plantByIdplant.humidityMax.toDouble() + item.plantByIdplant.humidityMax.toDouble()

        var parfait = item.realHumidity > decalageH / 2
        var good =
            item.realHumidity > decalageH / 10 && item.realTemperature < 29 || item.realHumidity > decalageH / 5

        if (parfait) {
            color = R.drawable.green
        } else if (good) {
            color = R.drawable.orange
        } else {
            color = R.drawable.red
        }

        holder.btColor.setBackgroundColor(color)

        // click sur enable/disable
        holder.btEnable.setOnClickListener {
            seeClick?.invoke(item)

        }
    }

}