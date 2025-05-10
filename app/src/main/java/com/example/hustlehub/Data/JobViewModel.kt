package com.example.hustlehub.data

import android.R.attr.description
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hustlehub.models.JobModel
import com.example.hustlehub.navigation.ROUTE_VIEW_JOB
import com.example.hustlehub.network.ImgurService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class JobViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("jobs")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uploadJobWithImage(
        uri: Uri,
        context: Context,
        jobname: String,
        location: String,
        salary: String,
        desc: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID 4a9cd0ac9fd5d4f"
                )

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""

                    val jobId = database.push().key ?: ""
                    val job = JobModel(
                        jobname, location, salary, desc, imageUrl, jobId,
                        jobId = TODO(),
                        jobRepository = TODO(),
                    )

                    database.child(jobId).setValue(job)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "job saved successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUTE_VIEW_JOB)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save job", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun viewJobs(
        job: MutableState<JobModel>,
        jobs: SnapshotStateList<JobModel>,
        context: Context
    ): Nothing? {
        val ref = FirebaseDatabase.getInstance().getReference("Jobs")

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobs.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(JobModel::class.java)
                    value?.let {
                        jobs.add(it)
                    }
                }
                if (jobs.isNotEmpty()) {
                    job.value = jobs.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch jobs: ${error.message}", Toast.LENGTH_SHORT).show()

            }
        })

        val jobs = null
        return jobs
    }
    fun updateJob(context: Context,navController: NavController,
                      jobname: String,location: String,
                      salary: String,desc: String,jobId: String){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Jobs/$jobId")
        val updatedClient = JobModel(
            jobname, location,
            salary, desc, jobId = jobId, description = description.toString(),
            imageUrl = TODO(),
            jobRepository = TODO()
        )

        databaseReference.setValue(updatedClient)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                    Toast.makeText(context,"Job Updated Successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_JOB)
                }else{

                    Toast.makeText(context,"Job update failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun deleteJob(context: Context,jobId: String,
                      navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Job")
            .setMessage("Are you sure you want to delete this job?")
            .setPositiveButton("Yes"){ _, _ ->
                val jobId = ""
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Jobs/$jobId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(context,"Job deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Job not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}
//
//fun applyToJob(jobId: String, userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
//    val db = Firebase.getInstance()
//    val application = hashMapOf(
//        "jobId" to jobId,
//        "userId" to userId,
//    )
//
//    db.collection("job_applications")
//        .add(application)
//        .addOnSuccessListener { onSuccess() }
//        .addOnFailureListener { onFailure(it) }
//}
//
//private fun Unit.collection(string: String) {}
//
//private fun Firebase.getInstance() {
//    TODO("Not yet implemented")
//}
