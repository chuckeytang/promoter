package com.example.promotor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.promotor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_ai -> replaceFragment(AITaskFragment())
                R.id.nav_tasks -> replaceFragment(TasksFragment())
                R.id.nav_functions -> replaceFragment(FunctionsFragment())
                R.id.nav_effects -> replaceFragment(EffectsFragment())
                R.id.nav_me -> replaceFragment(MeFragment())
            }
            true
        }

        // Set default fragment when activity starts
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_tasks // Set "任务" as default
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}