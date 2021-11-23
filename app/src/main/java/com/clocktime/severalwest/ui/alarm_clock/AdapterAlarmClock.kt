package com.clocktime.severalwest.ui.alarm_clock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.clocktime.severalwest.R
import com.clocktime.severalwest.model.AlarmClock

class AdapterAlarmClock(
    private var conditionSwitch: (index: Int, condition: Boolean) -> Unit,
    private var delete: (index: Int) -> Unit
) :
    RecyclerView.Adapter<AdapterAlarmClock.MyViewHolder>() {

    private var arrayOfAlarmClock: MutableList<AlarmClock> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<AlarmClock>) {
        this.arrayOfAlarmClock = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view, parent, false
            )
        )
    }

    override fun getItemCount(): Int = arrayOfAlarmClock.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    inner class MyViewHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val time: TextView = itemView.findViewById(R.id.item_view_time_txt)
        private val comment: TextView = itemView.findViewById(R.id.item_view_comment_txt)
        private val delete: ImageView = itemView.findViewById(R.id.item_view_delete_img)
        private val switch: SwitchCompat = itemView.findViewById(R.id.item_view_condition_switch)

        @SuppressLint("NotifyDataSetChanged")
        fun bind() {
            time.text = time.context.getString(
                R.string.adapter_time,
                arrayOfAlarmClock[adapterPosition].hour,
                arrayOfAlarmClock[adapterPosition].minute
            )
            comment.text = arrayOfAlarmClock[adapterPosition].comment

            switch.setOnCheckedChangeListener { _, isChecked ->
                conditionSwitch.invoke(arrayOfAlarmClock[adapterPosition].id, isChecked)
            }

            if (!arrayOfAlarmClock[adapterPosition].condition) {
                switch.isChecked = false
            }

            delete.setOnClickListener {
                this@AdapterAlarmClock.delete.invoke(arrayOfAlarmClock[adapterPosition].id)
                arrayOfAlarmClock.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }
}