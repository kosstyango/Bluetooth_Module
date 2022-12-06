package com.example.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import com.example.bluetooth.databinding.FragmentListBinding

class DeviceListFragment : Fragment() {
    private var bAdapter: BluetoothAdapter? = null
    private lateinit var binding: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        blueToothState()
    }

    private fun initBtAdapter(){
    val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    bAdapter = bManager.adapter
    }

    private fun blueToothState(){
        if (bAdapter?.isEnabled == true) {changeButtonColor(binding.imageBTH, ContextCompat.getColor(activity as Context, R.color.DarkGreen))}
        }
}