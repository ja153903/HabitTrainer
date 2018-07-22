package com.example.jaime.habittrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.jaime.habittrainer.db.HabitDbTable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // doesn't need to be initialized at this point
    // but will be initialized before use
    // private lateinit var textViewDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        textViewDescription = findViewById(R.id.tv_description)
//        textViewDescription.text = "A glass of rum and coke will do you good"
        // Kotlin allows you to do this and declutters all the boilerplate
//        tvDescription.text = "A refreshing glass of rum and coke will do you good"
//        ivIcon.setImageResource(R.drawable.water)
//        tvTitle.text = "Drink Water"

        // Adapter -> defines data for recyclerview
        // RecyclerView -> implements 3 methods
        rv.setHasFixedSize(true)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = HabitsAdapter(HabitDbTable(this).readAllHabits())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_habit) {
            switch(CreateHabitActivity::class.java)
        }
        return true
    }

    private fun switch(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }
}
