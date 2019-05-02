package com.example.beertracker

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        decrementButton.setOnClickListener { view ->
            val originalValue = totalBeersLeft.text.toString().toInt()
            val newValue = originalValue - 1
            totalBeersLeft.text = newValue.toString()
            Snackbar.make(view, "Hope you enjoyed it! Beer count changed from $originalValue to $newValue",
                Snackbar.LENGTH_LONG).show()
        }

        incrementButton.setOnClickListener { view ->
            val originalValue = totalBeersLeft.text.toString().toInt()
            val newValue = originalValue + 1
            totalBeersLeft.text = newValue.toString()
            Snackbar.make(view, "You deserve a beer! Beer count changed from $originalValue to $newValue",
                Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
