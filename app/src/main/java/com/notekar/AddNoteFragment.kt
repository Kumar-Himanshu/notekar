package com.notekar

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.Menu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.notekar.abstracted.AbstractNoteFragment
import com.notekar.database.AppDataBase
import com.notekar.database.TextMessage
import com.notekar.utils.Utility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNoteFragment : AbstractNoteFragment() {
    override fun saveData() {
        if (TextUtils.isEmpty(binding.tvBody.text.toString())) {
            Toast.makeText(activity, "Please enter message", Toast.LENGTH_SHORT).show()
        } else {
            addData()
        }
    }

    override fun deleteData() {
        TODO("Not yet implemented")
    }

    override fun cancelData() {
        binding.tvTitle.setText("")
        binding.tvBody.setText("")
    }

    override fun getScreenTitle(): Int {
        return R.string.second_fragment_label
    }

    override fun onBackPressedClicked(): Boolean {
        return if (!TextUtils.isEmpty(binding.tvBody.text.toString())) {
            addData()
            true
        } else {
            false
        }
    }

    override fun sharePicture() {
        if (!TextUtils.isEmpty(binding.tvBody.text.toString())) {
            binding.screenLayout.isDrawingCacheEnabled = true
            val bitmap = binding.screenLayout.drawingCache
            shareAsImage(saveBitmap(bitmap))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = true
    }

    @SuppressLint("CheckResult")
    private fun addData() {
        Observable.fromCallable {
            val data = TextMessage(
                title = binding.tvTitle.text.toString(),
                body = binding.tvBody.text.toString(),
                date = Utility.getCurrentDate(),
                time = Utility.getCurrentTime()
            )
            AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().insertAll(data)
        }.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }


}