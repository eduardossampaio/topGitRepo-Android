package com.esampaio.core.models
import java.util.Date

data class PullRequest(
    val name:String,
    val title:String,
    val authorName:String,
    val date:Date,
    val body:String
)