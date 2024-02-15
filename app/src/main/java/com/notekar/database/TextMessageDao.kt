package com.notekar.database

import androidx.room.*
import com.notekar.utils.Constants


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020. All rights reserved.
 */
@Dao
interface TextMessageDao {
    //Get list of all notes from db
    @Query("SELECT * FROM " + Constants.tableName)
    fun getAll(): List<TextMessage>

    @Query("SELECT * FROM "+Constants.tableName + " ORDER BY " + Constants.date +  " DESC ")
    fun getAllOrderBy(): List<TextMessage>

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

    @Query("UPDATE " + Constants.tableName + " SET " + Constants.topic_title + " = :title," + Constants.topic_body + " = :body," + Constants.date + " = :newDate," + Constants.time + " = :newTime WHERE " + Constants.date + " =:previousDate AND " + Constants.time + "=:previousTime")
    fun updateSingleData(
        title: String,
        body: String?,
        previousDate: String?,
        previousTime: String?,
        newDate: String?,
        newTime: String?
    )
}