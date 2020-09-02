package com.notekar.abstracted

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.notekar.R
import com.notekar.R.color.app_base_color
import com.notekar.interfaces.IOnBackPressed
import kotlinx.android.synthetic.main.abstract_base_fragment.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
abstract class AbstractNoteFragment : Fragment(),IOnBackPressed {

    abstract fun saveData()
    abstract fun deleteData()
    abstract fun cancelData()
    abstract fun getScreenTitle(): Int
    abstract fun onBackPressedClicked(): Boolean
    abstract fun sharePicture()

    private lateinit var content: CoordinatorLayout
    private lateinit var baseView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abstract_base_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content = view.findViewById<CoordinatorLayout>(R.id.content)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().setTitle(getScreenTitle())
        requireActivity().titleColor = resources.getColor(app_base_color)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel -> {
                cancelData()
                true
            }
            R.id.action_delete -> {
                deleteData()
                true
            }
            R.id.action_save -> {
                saveData()
                true
            }
            R.id.action_text -> {
                shareViaText()
                true
            }
            R.id.action_image -> {
                sharePicture()
                true
            }
//            R.id.action_image -> {
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareViaText() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, tvBody.text.toString())
        sendIntent.type = "text/plain"
        Intent.createChooser(sendIntent, "Share via")
        startActivity(sendIntent)
    }
    fun shareAsImage(imagePath: Uri) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        sharingIntent.type = "image/*"
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    open fun takeScreenshot(): Bitmap? {
        baseView = content.rootView
        baseView.isDrawingCacheEnabled = true
        return baseView.drawingCache
    }
    override fun onBackPressed(): Boolean {
        return onBackPressedClicked()
    }
}