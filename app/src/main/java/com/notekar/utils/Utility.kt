package com.notekar.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
object Utility {
    fun getCurrentDate():String{
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }

    fun getCurrentTime():String{
        return SimpleDateFormat("hh-mm-ss", Locale.getDefault()).format(Date())
    }

    fun getExternalStoragePath(context:Context): String{
        return Constants.EXTERNAL_STORAGE_PATH + context.packageName + "/.docs"
    }
    /**
     * To fetch App version
     */
    fun getAppVersion(context: Context): String? {
        var version: String? = null
        try {
            version =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            CustomLog.e("Exception", "app version exception", e)
        }
        return version
    }

    /**
     * Use this method to save file in device storage using Scoped storage architecture and return uri of that file
     */
    fun addBitmapToAlbum(bitmap: Bitmap, displayName: String, mimeType: String, compressFormat: Bitmap.CompressFormat, context: Context,contentResolver: ContentResolver): Uri {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        } else {
            values.put(MediaStore.MediaColumns.DATA, "${getExternalStoragePath(context)}/$displayName")
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            val outputStream = contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(compressFormat, 100, outputStream)
                outputStream.close()
            }
        }
        return uri!!
    }

}