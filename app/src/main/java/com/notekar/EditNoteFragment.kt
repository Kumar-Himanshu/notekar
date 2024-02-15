package com.notekar

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.notekar.abstracted.AbstractNoteFragment
import com.notekar.database.AppDataBase
import com.notekar.database.TextMessage
import com.notekar.databinding.AbstractBaseFragmentBinding
import com.notekar.utils.Utility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 * A simple [Fragment] subclass as the third destination in the navigation.
 */
class EditNoteFragment : AbstractNoteFragment() {
    private lateinit var mCreatedDate: String
    private lateinit var mCreatedTime: String
    private val args: EditNoteFragmentArgs by navArgs()
    private var _binding: AbstractBaseFragmentBinding? = null

    override fun saveData() {
        if (TextUtils.isEmpty(binding.tvBody.text.toString())) {
            Toast.makeText(activity, "Please enter message", Toast.LENGTH_SHORT).show()
        } else {
            updateData()
        }
    }

    override fun deleteData() {
        delete()
    }

    override fun cancelData() {
        initUI(args.notes)
    }

    override fun getScreenTitle(): Int {
        return R.string.third_fragment_label
    }

    override fun onBackPressedClicked(): Boolean {
        return if (!TextUtils.isEmpty(binding.tvBody.text.toString())) {
            updateData()
            true
        }else{
            false
        }
    }

    override fun sharePicture() {
        binding.screenLayout.isDrawingCacheEnabled = true
        val bitmap = binding.screenLayout.drawingCache
        shareAsImage(saveBitmap(bitmap))
////        val action = EditNoteFragmentDirections.actionThirdFragmentToFourthFragment(tvBody.text.toString())
//        val action = EditNoteFragmentDirections.actionThirdFragmentToFourthFragment(args.notes)
//        findNavController().navigate(action)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete).isVisible = true
        menu.findItem(R.id.action_cancel).isVisible = true
    }

    @SuppressLint("CheckResult")
    private fun updateData() {
        Observable.fromCallable {
            AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().updateSingleData(binding.tvTitle.text.toString(),binding.tvBody.text.toString(),mCreatedDate,mCreatedTime,Utility.getCurrentDate(),Utility.getCurrentTime())
        }.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }
    @SuppressLint("CheckResult")
    private fun delete() {
        Observable.fromCallable {
            AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().deleteById(mCreatedDate,mCreatedTime)
        }.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(args.notes)
    }

    private fun initUI(note: TextMessage) {
        binding.tvTitle.setText(note.title)
        binding.tvBody.setText(note.body)
        mCreatedDate = note.date!!
        mCreatedTime = note.time!!
    }
}