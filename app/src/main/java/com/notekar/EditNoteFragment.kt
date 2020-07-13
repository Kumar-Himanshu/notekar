package com.notekar

import android.os.AsyncTask
import android.text.TextUtils
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.notekar.abstracted.AbstractNoteFragment
import com.notekar.database.AppDataBase
import com.notekar.database.TextMessage
import com.notekar.utils.Utility
import kotlinx.android.synthetic.main.abstract_base_fragment.*
import kotlinx.coroutines.launch


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
class EditNoteFragment: AbstractNoteFragment() {
    override fun getSaveData() {
        if(TextUtils.isEmpty(tvBody.text.toString())){
            Toast.makeText(activity,"Please enter message", Toast.LENGTH_SHORT).show()
        }else{
            AsyncTask.execute {
                val data = TextMessage(
                    title = tvTitle.text.toString(),
                    body = tvBody.text.toString(),
                    date = Utility.getCurrentDate(),
                    time = Utility.getCurrentTime()
                )
                AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().update(data)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = true
        menu.findItem(R.id.action_cancel).isVisible = false
    }
}