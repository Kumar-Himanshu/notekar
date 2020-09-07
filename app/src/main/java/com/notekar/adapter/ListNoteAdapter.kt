package com.notekar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.notekar.R
import com.notekar.database.TextMessage


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 14-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
class ListNoteAdapter(
    private var listNote: List<TextMessage>,
    private var clickListener: onRowClick
) : RecyclerView.Adapter<ListNoteAdapter.NoteViewHolder>() {
    var singleClickListener = this.clickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: TextMessage = listNote[position]
        holder.bind(note)
        holder.clickItem(singleClickListener, note)
    }

    class NoteViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.adapter_recycler_view, parent, false)) {
        private var mTitleView: TextView? = null
        private var mDateView: TextView? = null
        private var mTimeView: TextView? = null
        private var mllNotes: CardView? = null
        private var mImgEdit: ImageView? = null
        private var mImgDelete: ImageView? = null


        init {
            mTitleView = itemView.findViewById(R.id.tvTitle)
            mDateView = itemView.findViewById(R.id.tvDate)
            mTimeView = itemView.findViewById(R.id.tvTime)
            mllNotes = itemView.findViewById(R.id.llNotes)
            mImgEdit = itemView.findViewById(R.id.imgEdit)
            mImgDelete = itemView.findViewById(R.id.imgDelete)
        }

        fun bind(notes: TextMessage) {
            if (notes.title!!.isNotEmpty()) {
                mTitleView?.text = notes.title
            } else {
                if (notes.body!!.isNotEmpty()) {
                    mTitleView?.text = notes.body
                } else {
                    mTitleView?.text = ""
                }
            }
            mDateView?.text = notes.date
            mTimeView?.text = notes.time
        }

        fun clickItem(singleClick: onRowClick, note: TextMessage) {
            mllNotes!!.setOnClickListener {
                singleClick.onItemClicked(note)
            }
            mllNotes!!.setOnLongClickListener {
                mImgEdit!!.visibility = View.VISIBLE
                mImgDelete!!.visibility = View.VISIBLE
                true
            }
            mImgEdit!!.setOnClickListener{singleClick.onItemClicked(note)}
            mImgDelete!!.setOnClickListener {
                singleClick.onClickDelete(note)
            }
        }

    }

    interface onRowClick {
        fun onItemClicked(note: TextMessage)
        fun onClickDelete(note: TextMessage)
    }
}