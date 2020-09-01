package com.notekar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.notekar.utils.Utility
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 04-08-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
class SplashActivity: AppCompatActivity() {
    private val SPLASHTIMEOUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        versionTv.text = String.format(getString(R.string.version),Utility.getAppVersion(this@SplashActivity))
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASHTIMEOUT)
    }
}