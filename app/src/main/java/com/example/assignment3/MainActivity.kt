package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menuOptions: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(menuOptions)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, AddItemFragment())
                    addToBackStack(null)
                    commit()
                }
            }
            R.id.view_list -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, ItemsListFragment())
                    addToBackStack(null)
                    commit()
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
