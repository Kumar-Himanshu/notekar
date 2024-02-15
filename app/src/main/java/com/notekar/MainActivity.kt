package com.notekar

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import com.notekar.databinding.ActivityMainBinding
import com.notekar.interfaces.IOnBackPressed

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.overflowIcon!!.setColorFilter(
            resources.getColor(R.color.black),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment =
            fragment?.childFragmentManager?.fragments?.get(0) as? IOnBackPressed
        currentFragment?.onBackPressed()?.takeIf { !it }
            ?.let {
                if (currentFragment is ListFragment) {
                    finish()
                } else {
                    super.onBackPressed()
                }
            }
    }
}