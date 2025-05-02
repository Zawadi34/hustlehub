package com.example.hustlehub.ui.theme.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.hustlehub.Data.AuthViewModel
import com.example.hustlehub.R
import com.example.hustlehub.navigation.ROUTE_LOGIN



@Composable
fun RegisterScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register Here!!",
            fontSize = 40.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
            label = { Text("Enter First Name") },
            placeholder = { Text("Please enter firstname") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
            label = { Text("Enter Last Name") },
            placeholder = { Text("Please enter last name") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter Email") },
            placeholder = { Text("Please enter your email") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter Password") },
            placeholder = { Text("Please enter password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                authViewModel.signup(
                    firstname,
                    lastname,
                    email,
                    password,
                    navController,
                    context
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString { append("If already registered, Login here ") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(ROUTE_LOGIN)
                }
        )
    }
}
