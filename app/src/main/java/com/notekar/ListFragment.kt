package com.notekar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notekar.adapter.ListNoteAdapter
import com.notekar.database.AppDataBase
import com.notekar.database.TextMessage
import com.notekar.interfaces.IOnBackPressed
import com.notekar.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io

/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment(), ListNoteAdapter.onRowClick,IOnBackPressed {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mListNotes: RecyclerView
    private lateinit var adapter: ListNoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.first_fragment_label)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        //removing menu options from toolbar
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_share).isVisible = false
    }

    @SuppressLint("CheckResult")
    private fun fetchData() {
        Observable.fromCallable {
            AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao().getAllOrderBy()
        }.subscribeOn(
            io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it.isNotEmpty()) {
                adapter = ListNoteAdapter(it, this)
                mListNotes.adapter = adapter
            }
        }
    }

    private fun initUI(view: View) {
        mListNotes = view.findViewById(R.id.listNotes)
        linearLayoutManager = LinearLayoutManager(activity)
        mListNotes.layoutManager = linearLayoutManager
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        fetchData()

    }

    override fun onItemClicked(note: TextMessage) {
        val action = ListFragmentDirections.actionFirstFragmentToThirdFragment(note)
        findNavController().navigate(action)
    }

    override fun onClickDelete(note: TextMessage) {
        delete(note)
    }

    @SuppressLint("CheckResult")
    private fun delete(note: TextMessage) {
        Observable.fromCallable {
            AppDataBase.getAppDataBase(requireContext())!!.getTextMessageDao()
                .deleteById(note.date!!, note.time!!)
        }.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            fetchData()
        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}