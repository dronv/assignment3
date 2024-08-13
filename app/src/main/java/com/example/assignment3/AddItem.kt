package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment

class AddItemFragment : Fragment() {

    private lateinit var itemName: EditText
    private lateinit var spQuantity: Spinner
    private lateinit var btnAdd: Button

    companion object {
        private const val CHANNEL_ID = "ITEM_NOTIFICATION_CHANNEL"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemName = view.findViewById(R.id.item_name)
        spQuantity = view.findViewById(R.id.sp_quantity)
        btnAdd = view.findViewById(R.id.btn_add)

        val options = arrayOf("1", "2", "3", "4", "5")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spQuantity.adapter = adapter

        spQuantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        btnAdd.setOnClickListener {
            val item = itemName.text.toString()
            val quantity = spQuantity.selectedItem.toString()

            val bundle = Bundle().apply {
                putString("ITEM_NAME", item)
                putString("QUANTITY", quantity)
            }
            val itemsListFragment = ItemsListFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, itemsListFragment)
                .addToBackStack(null)
                .commit()

            showNotification(item, quantity)
        }
    }

    private fun showNotification(item: String, quantity: String) {
        val notificationManager = NotificationManagerCompat.from(requireContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Item Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for item notifications"
            }
            val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Item Added")
            .setContentText("Item: $item, Quantity: $quantity")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
