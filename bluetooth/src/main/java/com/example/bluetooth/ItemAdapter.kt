package com.example.bluetooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.databinding.ListItemBinding

class ItemAdapter(private val listener: Listener) : ListAdapter<ListItem, ItemAdapter.MyHolder>(Comparator()) {
    private var oldCheckBox: CheckBox? = null

    class MyHolder(view : View, private val adapter: ItemAdapter,
                   private val listener: Listener) :RecyclerView.ViewHolder(view) {
        private val b = ListItemBinding.bind(view)
        private var device: ListItem? = null
        init {
            b.checkBox.setOnClickListener{ //Слушаем нажатие на CheckBox
                device?.let { it1 -> listener.onClick(it1) }
                adapter.selectCheckBox(it as CheckBox)
            }
            itemView.setOnClickListener{ //Слушаем нажатие на строчку с устройством (необязательно)
                device?.let { it1 -> listener.onClick(it1) }//это тот же обработчик нажатия на строчку
                adapter.selectCheckBox(b.checkBox)//это тот же обработчик нажатия на строчку
            }
        }
        fun bind(item: ListItem) = with(b) {
            device = item
            name.text = item.name
            mac.text = item.mac
            if(item.isChecked) adapter.selectCheckBox(checkBox)
        }
    }
        class Comparator : DiffUtil.ItemCallback<ListItem>(){
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
    return MyHolder(view, this, listener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun selectCheckBox(checkBox: CheckBox){ //обработчик нажатия кнопок выбора устройств из списка
        oldCheckBox?.isChecked = false //убираем галочку со "старой" кнопки
        oldCheckBox = checkBox //обновляем "старую" кнопку
        oldCheckBox?.isChecked = true//ставим галочку новой "старой" кнопке
    }

    interface Listener{
        fun onClick(device: ListItem)
    }
}