package com.notekar.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 13-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */
object Utility {
    fun getCurrentDate():String{
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }

    fun getCurrentTime():String{
        return SimpleDateFormat("hh-mm-ss", Locale.getDefault()).format(Date())
    }

}