package com.example.bluetooth
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetooth.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

class DeviceListFragment : Fragment(), ItemAdapter.Listener {
    private lateinit var itemAdapter : ItemAdapter
    private var bAdapter: BluetoothAdapter? = null
    private lateinit var binding: FragmentListBinding
    private lateinit var btLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageBTH.setOnClickListener{ //Вешаем обработчик на кнопку "Bluetooth"
         btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))} //Запрашиваем разрешение у Системы на включение Bluetooth
        initRcViews()
        registerBtLauncher()
        initBtAdapter()
        blueToothState()
    }

    private fun initRcViews() = with (binding){
        rcView.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = ItemAdapter(this@DeviceListFragment) //указываем на ФРАГМЕНТ, а не на bindind (поэтому с @)
        rcView.adapter = itemAdapter
    }

    private fun getPairedDevices(){
        try {
            val list = ArrayList<ListItem>()
            val deviceList = bAdapter?.bondedDevices as Set<BluetoothDevice>
            deviceList.forEach(){
            list.add(ListItem(it.name, it.address, false))
                }
            binding.tvEmptyPired.visibility = if(list.isEmpty()) View.VISIBLE //Проверяем наполненность списка
                else View.GONE //Гасим надпись "Пусто", если список НЕ пустой
            itemAdapter.submitList(list) //Передаём список устройств в RecyclerView
            }catch (e: SecurityException){ //в случае ошибки...ничего не делаем
                }
        }

    private fun initBtAdapter(){
    val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    bAdapter = bManager.adapter
    }

    private fun blueToothState(){ //Проверка состояния Bluetooth
        if (bAdapter?.isEnabled == true) {
            changeButtonColor(binding.imageBTH, ContextCompat.getColor(activity as Context, R.color.DarkGreen))
            getPairedDevices()
        }else{
            changeButtonColor(binding.imageBTH, ContextCompat.getColor(activity as Context, R.color.Red))
        }
    }

    private fun registerBtLauncher(){ //обработка нажатия на кнопку "Bluetooth"
        btLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK){
                Snackbar.make(binding.root, "Bluetooth IS activated", Snackbar.LENGTH_LONG).show()
                changeButtonColor(binding.imageBTH, ContextCompat.getColor(activity as Context, R.color.DarkGreen))
                getPairedDevices()
            }else{
                changeButtonColor(binding.imageBTH, ContextCompat.getColor(activity as Context, R.color.Red))
                Snackbar.make(binding.root, "Bluetooth is NOT activated", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(device: ListItem) { //здесь будем сохранять выбранное устройство

    }
}