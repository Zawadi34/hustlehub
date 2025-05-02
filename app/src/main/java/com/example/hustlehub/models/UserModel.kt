package com.example.hustlehub.models

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

fun UserModel(
    firstname: String,
    lastname: String,
    email: String,
    password: String,
    navController: NavHostController,
    context: Context,
    userId: String
) {
    val auth = FirebaseAuth.getInstance()

    if (email.isNotBlank() && password.isNotBlank()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Save additional user info to Firestore if needed
                    Toast.makeText(context, "Registered successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") // or use ROUTE_LOGIN
                } else {
                    Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    } else {
        Toast.makeText(context, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
    }
}


