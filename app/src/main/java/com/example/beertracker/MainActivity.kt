package com.example.beertracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.R.attr.y
import android.R.attr.x
import android.R.raw
import android.media.MediaPlayer
import android.opengl.Matrix


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        homeTitle.text = resources.getString(R.string.how_much)

        val dbHandler = DBHandler(this, null, null, 1)

        var initialVal = dbHandler.getBeer()
        totalBeersLeft.text = initialVal.toString()

        var openBottleSound = MediaPlayer.create(this, R.raw.beer_bottle_open)
        var openCanSound = MediaPlayer.create(this, R.raw.beer_can_open)
        var openFridgeSound = MediaPlayer.create(this, R.raw.beer_pour)

        val removeBeerSoundList = listOf(openBottleSound, openCanSound)
        val addBeerSoundList = listOf(openFridgeSound)

        decrementButton.setOnClickListener { view ->
            initialVal = dbHandler.getBeer()

            // initial value is 0, can't have anymore beer
            if (initialVal == 0) {
                Snackbar.make(view, "You are all out of beer!!!",
                    Snackbar.LENGTH_LONG).show()
            } else {
                // this will randomly choose between all opening beer sounds
                removeBeerSoundList.shuffled().take(1)[0].start()
                dbHandler.removeBeer(1)
                val newVal = dbHandler.getBeer()
                totalBeersLeft.text = newVal.toString()
                Snackbar.make(view, "Hope you enjoyed it! Beer count changed from $initialVal to $newVal",
                    Snackbar.LENGTH_LONG).show()
            }
        }

        incrementButton.setOnClickListener { view ->
            initialVal = dbHandler.getBeer()
            // this will randomly choose between all adding beer sounds
            addBeerSoundList.shuffled().take(1)[0].start()
            dbHandler.addBeer(1)
            val newVal = dbHandler.getBeer()
            totalBeersLeft.text = newVal.toString()
            Snackbar.make(view, "You deserve a beer! Beer count changed from $initialVal to $newVal",
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
            R.id.action_settings -> {
//                setContentView(R.layout.settings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
