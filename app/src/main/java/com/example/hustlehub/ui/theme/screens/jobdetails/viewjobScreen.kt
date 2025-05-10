package com.example.hustlehub.ui.theme.screens.jobdetails

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.hustlehub.data.JobViewModel

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.hustlehub.models.JobItem
import com.example.hustlehub.models.JobModel
import com.example.hustlehub.navigation.ROUTE_UPDATE_JOB

@Composable
fun ViewJobs(navController: NavHostController) {
    val context = LocalContext.current
    val jobRepository = JobViewModel()
    val emptyUploadState = remember {
        mutableStateOf(
            JobModel(
                "", "", "", "", "", "",
                jobId = TODO(),
                jobRepository = TODO(),
            )
        )
    }
    val emptyUploadListState = remember {
        mutableStateListOf<JobModel>()
    }
//    val jobs = jobRepository.viewJob(emptyUploadState,context)
//    Column (modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally){
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "All Products",
//                fontSize = 30.sp,
//                fontFamily = FontFamily.SansSerif,
//                color= Color.Black)
//            Spacer(modifier = Modifier.height(20.dp))
//
//            LazyColumn(){
//                items(jobs){
//                    JobItem(
//                        jobname = it.jobname,
//                        location = it.location,
//                        salary = it.salary,
//                        desc = it.description,
//                        jobId = it.jobId,
//                        imageUrl = it.imageUrl,
//                        navController = navController,
//                        jobRepository = jobRepository
//
//                    )
//                }
//
//            }
//        }
//    }
//}


    @Composable
    fun JobItem(
        jobname: String, location: String, salary: String,
        desc: String, jobId: String, imageUrl: String, navController: NavHostController,
        jobRepository: JobViewModel
    ) {
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .height(210.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors
                    (containerColor = Color.Gray)
            )
            {
                Row {
                    Column {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(200.dp)
                                .height(150.dp)
                                .padding(10.dp)
                        )

                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                onClick = {
                                    jobRepository.deleteJob(context, jobId, navController)

                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color.Red)
                            ) {
                                Text(
                                    text = "REMOVE",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Button(
                                onClick = {
                                    navController.navigate("$ROUTE_UPDATE_JOB/$jobId")
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color.Green)
                            ) {
                                Text(
                                    text = "UPDATE",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                    }
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "JOB NAME",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = jobname,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "LOCATION",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = location,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "SALARY",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = salary,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "DESC",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = desc,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }


    }
}
//    @Preview
//    @Composable
//    fun ViewJobsPreview() {
//        ViewJobs(rememberNavController())
//    }
//}