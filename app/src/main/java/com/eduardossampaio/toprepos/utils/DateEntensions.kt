package com.eduardossampaio.toprepos.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Date.toString(format:String): String{
    return SimpleDateFormat(format, Locale.getDefault()).format(this);
}