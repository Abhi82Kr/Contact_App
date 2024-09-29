package com.example.contactapp

import ContactViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun  AddScreen(navController: NavController,viewModel: ContactViewModel) {

    var name= remember { mutableStateOf("") }
    var phone= remember { mutableStateOf("") }
    var email= remember { mutableStateOf("") }

    val context= LocalContext.current

    Column(modifier = Modifier.fillMaxSize()
        .wrapContentSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally


    )

    {
        Image(painter = painterResource(R.drawable.signup),
            contentDescription = "image",
            modifier = Modifier.width(100.dp).padding(10.dp)

        )
        Text(text = "Add Contact", fontSize = 35.sp, fontFamily = FontFamily.SansSerif)

        OutlinedTextField(
            modifier = Modifier.padding(top=10.dp),
            value = name.value,
            onValueChange = {name.value=it},
            label = { Text("Enter Name") }
        )
        OutlinedTextField(
            modifier = Modifier.padding(top=10.dp),
            value = phone.value,
            onValueChange = {phone.value=it},
            label = { Text("Enter Phone no.") }
        )
        OutlinedTextField(
            modifier = Modifier.padding(top=10.dp),
            value = email.value,
            onValueChange = {email.value=it},
            label = { Text("Enter Email") }
        )

        Button(
            onClick = {
                viewModel.addContact(context,name.value,phone.value,email.value)
                viewModel.getContacts()
                navController.navigate("homescreen")
            },
            modifier = Modifier.padding(10.dp)

        ) {
            Text("Save")
        }







    }

}
