package com.example.hustlehub.ui.theme.screens.jobdetails

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.hustlehub.R
import com.example.hustlehub.data.JobViewModel
import com.example.hustlehub.navigation.ROUTE_UPDATE_JOB


@Composable
fun JobScreen(navController: NavController){
    val context = LocalContext.current
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    {uri: Uri? -> uri?.let{imageUri.value=it} }
    var jobname by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var jobsalary by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val JobViewModel: JobViewModel = viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card (shape = CircleShape,
            modifier = Modifier.padding(10.dp).size(200.dp)){
            AsyncImage(model = imageUri.value ?:R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clickable { launcher.launch("image/*") })
        }
        Text(text = "Attach job image")

        OutlinedTextField(value = jobname,
            onValueChange = {newJobname ->jobname=newJobname},
            label = { Text(text =  "Jobname") },
            placeholder = { Text(text = "Please enter job name") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = location,
            onValueChange = {newLocation ->location=newLocation},
            label = { Text(text = "Location") },
            placeholder = { Text(text = "Please enter your location") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = jobsalary,
            onValueChange = {newJobsalary ->jobsalary=newJobsalary},
            label = { Text(text = "Salary") },
            placeholder = { Text(text = "Please enter the salary") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = desc,
            onValueChange ={newDesc->desc=newDesc},
            label = { Text(text = "Brief description") },
            placeholder = { Text(text = "Please enter job description") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            singleLine = false)
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = { ROUTE_UPDATE_JOB }) { Text(text = "Delete") }
            Button(onClick = {ROUTE_UPDATE_JOB
                imageUri.value?.let {
                    JobViewModel.uploadJobWithImage(it, context, jobname, location, jobsalary,desc,navController)
                } ?: Toast.makeText(context, "Please pick an image", Toast.LENGTH_SHORT).show()
            }) { Text(text = "SAVE") }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobScreenPreview(){
    JobScreen(rememberNavController())
}