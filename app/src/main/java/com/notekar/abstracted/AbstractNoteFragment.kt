package com.notekar.abstracted

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.notekar.R
import com.notekar.R.color.app_base_color
import com.notekar.interfaces.IOnBackPressed
import com.notekar.utils.CustomLog
import com.notekar.utils.Utility
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
        val sharingIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setType("image/png")
            .setStream(imagePath)
            .intent
            .setData(imagePath)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (sharingIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(sharingIntent)
        }
    }

    override fun onBackPressed(): Boolean {
        return onBackPressedClicked()
    }
    open fun saveBitmap(bitmap: Bitmap): Uri {
        val imageName = Utility.getCurrentDate() + "_" + Utility.getCurrentTime()
        val storageDirectory = File(Utility.getExternalStoragePath(requireContext()))
        if (!storageDirectory.exists()){
            storageDirectory.mkdirs()
        }
        val imagePath =
            File(storageDirectory, "$imageName.png")
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("SharePictureFragment", e.localizedMessage, e)
        } catch (e: IOException) {
            Log.e("SharePictureFragment", e.localizedMessage, e)
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile( requireContext(),
                requireContext().packageName + ".provider",
                imagePath)

        } else{
            Uri.fromFile(File(imagePath.absolutePath + imagePath.name))
        }
    }


}