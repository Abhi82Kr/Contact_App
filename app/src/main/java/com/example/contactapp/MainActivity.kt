package com.example.contactapp

import ContactViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.ui.theme.ContactAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}


@Composable
fun Navigation(){
    val navController= rememberNavController()
    var contactViewModel:ContactViewModel= viewModel()

    NavHost(navController=navController, startDestination = "homescreen") {
        composable("homescreen"){
            HomeScreen(navController,contactViewModel)
        }
        composable("addscreen"){
            AddScreen(navController,contactViewModel)
        }
        composable("editscreen/{contactId}") { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId")
            if (contactId != null) {
                EditScreen(contactId,navController,contactViewModel)
            }
        }
    }
}
