package com.notekar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.notekar.utils.Constants


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
@Entity(tableName = Constants.tableName)
data class TextMessage(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "u_id") var uid: Int = 0,
    @ColumnInfo(name = Constants.topic_title) val title: String?,
    @ColumnInfo(name = Constants.topic_body) val body: String?,
    @ColumnInfo(name = Constants.date) val date: String?,
    @ColumnInfo(name = Constants.time) val time: String?
)