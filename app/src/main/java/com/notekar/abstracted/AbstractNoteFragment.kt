package com.notekar.abstracted

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.notekar.R
import com.notekar.R.color.app_base_color
import com.notekar.interfaces.IOnBackPressed
import com.notekar.utils.CustomLog
import com.notekar.utils.Utility
import kotlinx.android.synthetic.main.abstract_base_fragment.*


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 */
abstract class AbstractNoteFragment : Fragment(),IOnBackPressed {

    abstract fun saveData()
    abstract fun deleteData()
    abstract fun cancelData()
    abstract fun getScreenTitle(): Int
    abstract fun onBackPressedClicked(): Boolean
    abstract fun sharePicture()

    private lateinit var content: CoordinatorLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abstract_base_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content = view.findViewById(R.id.content)
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
        CustomLog.i("uri",imagePath.toString())
        val shareIntent =  Intent(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, imagePath)

        try {
            startActivity(shareIntent)
        }
        catch (exception : android.content.ActivityNotFoundException) {
            CustomLog.i("error","app not installed")
        }
    }

    override fun onBackPressed(): Boolean {
        return onBackPressedClicked()
    }
    open fun saveBitmap(bitmap: Bitmap): Uri {
        val imageName = "${Utility.getCurrentDate() + "_" + Utility.getCurrentTime()}.jpg"
        val mimeType = "image/jpeg"
        val compressFormat = Bitmap.CompressFormat.JPEG
        return Utility.addBitmapToAlbum(bitmap, imageName, mimeType, compressFormat,
            requireContext(),
            requireActivity().contentResolver)
    }


}