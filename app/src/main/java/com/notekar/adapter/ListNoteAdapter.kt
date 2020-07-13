package com.notekar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.notekar.R
import com.notekar.database.TextMessage


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 14-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
class ListNoteAdapter(private var listNote : List<TextMessage>, private var navController: NavController): RecyclerView.Adapter<ListNoteAdapter.NoteViewHolder>() {
//    var navController1 = this.navController
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val movie: TextMessage = listNote[position]
        holder.bind(movie)
    }

    class NoteViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.adapter_recycler_view, parent, false)) {
        private var mTitleView: TextView? = null
        private var mDateView: TextView? = null
        private var mTimeView: TextView? = null
        private var mllNotes:ConstraintLayout? = null


        init {
            mTitleView = itemView.findViewById(R.id.tvTitle)
            mDateView = itemView.findViewById(R.id.tvDate)
            mTimeView = itemView.findViewById(R.id.tvTime)
            mllNotes = itemView.findViewById(R.id.llNotes)
//            mllNotes!!.setOnClickListener {
//                navController1.navigate(R.id.action_FirstFragment_to_SecondFragment)
//            }
        }

        fun bind(notes: TextMessage) {
            mTitleView?.text = notes.title
            mDateView?.text = notes.date
            mTimeView?.text = notes.time
        }
    }
}