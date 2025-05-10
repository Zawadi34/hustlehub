package com.example.hustlehub.ui.theme.screens.application

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hustlehub.Data.ApplicationViewModel
import com.example.hustlehub.navigation.ROUTE_APPLICATION


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(navController: NavController,
    jobId: String,
    onBack: () -> Unit,
    viewModel: ApplicationViewModel
) {
    // Fetch job details when composable enters composition
    LaunchedEffect(jobId) {
        viewModel.loadviewjob(jobId)
    }

    val jobState by viewModel.jobDetails.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val submissionResult by viewModel.submissionResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Apply for Job") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        ROUTE_APPLICATION
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                jobState.isLoading -> {
                    CircularProgressIndicator()
                }
                jobState.error != null -> {
                    Text(text = "Error loading job: ${jobState.error}")
                }
                else -> {
                    Text(text = jobState.job?: "Untitled", style = MaterialTheme.typography.headlineSmall)
                    Text(text = jobState.job ?: "Description unavailable", style = MaterialTheme.typography.bodyMedium)

                    var coverLetter by remember { mutableStateOf(TextFieldValue()) }
                    Text(text = "Cover Letter", style = MaterialTheme.typography.labelMedium)
                    BasicTextField(
                        value = coverLetter,
                        onValueChange = { coverLetter = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
//                            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                            .padding(8.dp)
                    )

                    Button(
                        onClick = { viewModel.submitApplication(jobId, coverLetter.text) },
                        enabled = !isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(text = "Submit Application")
                        }
                    }

                    submissionResult?.let { result ->
                        Text(
                            text = if (result.success) "Application submitted!" else "Failed: ${result.errorMessage}",
                            color = if (result.success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

private fun ApplicationViewModel.loadviewjob(string: String) {}
