package com.notekar

import android.os.AsyncTask
import android.text.TextUtils
import androidx.fragment.app.Fragment
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
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNoteFragment : AbstractNoteFragment() {
    override fun getSaveData() {
        if(TextUtils.isEmpty(tvBody.text.toString())){
            Toast.makeText(activity,"Please enter message",Toast.LENGTH_SHORT).show()
        }else{
            AsyncTask.execute {
                val data = TextMessage(
                    title = tvTitle.text.toString(),
                    body = tvBody.text.toString(),
                    date = Utility.getCurrentDate(),
                    time = Utility.getCurrentTime()
                )
                AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().insertAll(data)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = true
    }
}