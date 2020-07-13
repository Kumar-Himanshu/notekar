package com.notekar.database

import androidx.room.*
import com.notekar.utils.Constants


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
@Dao
interface TextMessageDao {
    //Get list of all notes from db
    @Query("SELECT * FROM " + Constants.tableName)
    fun getAll(): List<TextMessage>

    // add data into db
    @Insert
    fun insertAll(vararg users: TextMessage)

    // delete data into db
    @Delete
    fun deleteAll(user: TextMessage)

    // delete selected item from db
    @Query("DELETE FROM " + Constants.tableName + " WHERE " + Constants.date + " = :date AND " + Constants.time + " = :time")
    fun deleteById(date: String, time: String)

    //update db
    @Update
    fun update(user: TextMessage)
}