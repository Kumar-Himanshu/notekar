package com.notekar.utils

import android.os.Environment


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 */
object Constants {
    val EXTERNAL_STORAGE_PATH =
        Environment.getExternalStorageDirectory().toString() + "/Android/data/"
    const val tableName = "note_data"
    const val topic_title = "title"
    const val topic_body = "message"
    const val date = "date"
    const val time = "time"
    const val arg_note = "notes"
}