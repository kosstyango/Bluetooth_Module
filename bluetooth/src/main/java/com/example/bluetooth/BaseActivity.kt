package com.example.bluetooth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list)
        initRcView()
    }
    private fun initRcView(){
        val rcView = findViewById<RecyclerView>(R.id.rcView)
        rcView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter()
        rcView.adapter = adapter
        adapter.submitList(createDeviceList())
    }
    private fun createDeviceList() : List<ListItem>{
        val list = ArrayList<ListItem>()
        for (i in 1 until 10){
            list.add(ListItem("Device $i", "23:34:45:56"))
        }
        return list
    }
}