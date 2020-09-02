package com.notekar

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.content.FileProvider
import androidx.navigation.fragment.navArgs
import com.notekar.abstracted.AbstractNoteFragment
import com.notekar.database.TextMessage
import com.notekar.utils.Constants
import com.notekar.utils.Utility
import kotlinx.android.synthetic.main.abstract_base_fragment.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 02-09-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
class SharedPictureFragment : AbstractNoteFragment() {
    private val args: SharedPictureFragmentArgs by navArgs()
    override fun saveData() {
    }

    override fun deleteData() {
    }

    override fun cancelData() {
    }

    override fun getScreenTitle(): Int {
        return R.string.action_share
    }

    override fun onBackPressedClicked(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(args.data)
    }


    override fun sharePicture() {
        screenLayout.isDrawingCacheEnabled = true
        val bitmap = screenLayout.drawingCache
        shareAsImage(saveBitmap(bitmap))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        //removing menu options from toolbar
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_text).isVisible = false
    }


    private fun initUI(note: TextMessage) {
        tvTitle.setText(note.title)
        tvBody.setText(note.body)
    }

    private fun saveBitmap(bitmap: Bitmap): Uri {
        val imagePath =
            File(Constants.EXTERNAL_STORAGE_PATH + "/Notekar_" + Utility.getCurrentDate() + "_" + Utility.getCurrentTime() + ".png")
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
        return FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            imagePath
        )
    }

}