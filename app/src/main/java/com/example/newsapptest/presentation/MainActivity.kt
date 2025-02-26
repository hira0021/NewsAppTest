package com.example.newsapptest.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.newsapptest.R
import com.example.newsapptest.databinding.ActivityMainBinding
import com.example.newsapptest.presentation.home.HomeFragment
import com.example.newsapptest.presentation.recent.RecentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeTag = "HOME_FRAGMENT"
    private val recentTag = "RECENT_FRAGMENT"

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.main_color_dark, theme)

        val homeFragment = supportFragmentManager.findFragmentByTag(homeTag) ?: HomeFragment()
        val recentFragment = supportFragmentManager.findFragmentByTag(recentTag) ?: RecentFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment, homeTag)
                .add(R.id.fragment_container, recentFragment, recentTag)
                .hide(recentFragment)
                .commit()
            activeFragment = homeFragment
        } else {
            activeFragment = supportFragmentManager.fragments.find { !it.isHidden }
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    switchFragment(homeFragment)
                    true
                }

                R.id.action_recent -> {
                    switchFragment(recentFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun switchFragment(targetFragment: Fragment) {
        if (activeFragment == targetFragment) return

        supportFragmentManager.beginTransaction()
            .hide(activeFragment!!)
            .show(targetFragment)
            .commit()

        activeFragment = targetFragment
    }
}
