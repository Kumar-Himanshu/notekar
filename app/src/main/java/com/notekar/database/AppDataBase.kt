package com.notekar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
@Database(entities = [TextMessage::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun getTextMessageDao() : TextMessageDao
    companion object {
        var INSTANCE: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "noteDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}