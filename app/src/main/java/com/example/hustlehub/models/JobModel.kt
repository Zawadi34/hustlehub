package com.example.hustlehub.models

import androidx.navigation.NavHostController
import com.example.hustlehub.data.JobViewModel

data class JobModel(
    var id: String = "",
    var jobname: String = "",
    var location: String = "",
    var salary: String = "",
    var description: String = "",
    var imageUrl: String = "",
    val jobId: String,
    var jobRepository: JobViewModel
)

data class JobItem(
    var jobname: String,
    var location: String = "",
    val jobRepository1: JobViewModel,
    var salary: String = "",
    var desc: String = "",
    var imageUrl: String = "",
    var jobId: String = "",
    var updateJob: String = "",
    val description: String,
    val navController: NavHostController,
    val jobRepository: JobViewModel,
    val job: String=""
)

data class Application(
    val id: String = "",
    val jobId: String = "",
    val userId: String = "",
    val coverLetter: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
