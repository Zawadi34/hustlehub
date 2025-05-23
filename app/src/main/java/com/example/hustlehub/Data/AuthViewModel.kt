package com.example.hustlehub.Data

import com.example.hustlehub.navigation.ROUTE_HOME
import com.example.hustlehub.navigation.ROUTE_LOGIN
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.hustlehub.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel : ViewModel(){
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun signup(firstname:String,lastname:String, email: String, password: String,
               navController: NavController,
               context: Context
    ){
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank()){

            Toast.makeText(context,"Please fill all the fields", Toast.LENGTH_LONG).show()

            return
        }

        _isLoading.value = true

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful){
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userData = UserModel(
                        firstname = firstname, lastname = lastname,
                        email = email, password = password, userId = userId,
                        navController = TODO(ROUTE_HOME),
                        context = TODO()
                    )
                    saveUserToDatabase(userId,userData,navController,context)


                } else{
                    _errorMessage.value = task.exception?.message

                    Toast.makeText(context,"Registration failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun saveUserToDatabase(
        userId: String, userData: Unit,
        navController: NavController, context: Context
    ){
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if (regRef.isSuccessful){

                Toast.makeText(context,"User Successfully Registered", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
            } else{
                _errorMessage.value = regRef.exception?.message

                Toast.makeText(context,"Database error", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun login(
        email: String,
        password: String,
        navController: NavHostController,
        context: Context
    ) {
        val auth = FirebaseAuth.getInstance()

        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_HOME) // change this to your actual home route
                    } else {
                        Toast.makeText(
                            context,
                            "Login failed: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("LOGIN", "Login error: ", task.exception)
                    }
                }
        } else {
            Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
        }
    }


}

private fun Unit.addOnSuccessListener(function: () -> Unit) {}
