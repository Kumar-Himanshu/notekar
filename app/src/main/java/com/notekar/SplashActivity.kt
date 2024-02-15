package com.notekar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.notekar.databinding.ActivitySplashBinding
import com.notekar.utils.CustomLog
import com.notekar.utils.Utility


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 04-08-2020.
 * Copyright (c) 2020. All rights reserved.
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASHTIMEOUT: Long = 3000 // 1 sec
    private val PERMISSIONS_WRITE_EXTERNAL_STORAGE = 101
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.versionTv.text =
            String.format(getString(R.string.version), Utility.getAppVersion(this@SplashActivity))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(!Environment.isExternalStorageManager()) {
                val uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri))
            }else{
                openActivity()
            }
        }else{
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_WRITE_EXTERNAL_STORAGE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val builder = AlertDialog.Builder(this@SplashActivity)
                    builder.setTitle("").setMessage(getString(R.string.permission_storage_msg)).setCancelable(false)
                    builder.setPositiveButton(getString(R.string.permission_btn)
                    ) { dialog, which ->
                        dialog!!.dismiss()
                        val i = Intent()
                        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        i.addCategory(Intent.CATEGORY_DEFAULT)
                        i.data = Uri.parse("package:" + this@SplashActivity.packageName)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        startActivity(i)
                    }
                    builder.show()
                    CustomLog.i("Notekar splash", "Permission has been denied by user")
                } else {
                    openActivity()
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            this@SplashActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this@SplashActivity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSIONS_WRITE_EXTERNAL_STORAGE
            )
        } else {
            openActivity()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this@SplashActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@SplashActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this@SplashActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_WRITE_EXTERNAL_STORAGE
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            openActivity()
        }
    }

    private fun openActivity(){
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASHTIMEOUT)

    }
}