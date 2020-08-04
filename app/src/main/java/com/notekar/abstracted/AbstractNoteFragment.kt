package com.notekar.abstracted

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.notekar.R
import com.notekar.R.color.app_base_color
import com.notekar.interfaces.IOnBackPressed
import kotlinx.android.synthetic.main.abstract_base_fragment.*


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abstract_base_fragment, container, false)
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
            R.id.action_share -> {
                shareViaText()
                true
            }
            R.id.action_image -> {
                true
            }
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

    override fun onBackPressed(): Boolean {
        return onBackPressedClicked()
    }
}