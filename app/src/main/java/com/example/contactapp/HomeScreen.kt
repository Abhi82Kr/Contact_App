
package com.example.contactapp

import ContactViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.contactapp.Data.Contact

@Composable
fun HomeScreen(navController: NavController, contactViewModel: ContactViewModel) {

    // Observe contactsList LiveData
    val contactList by contactViewModel.contactsList.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addscreen") },
                contentColor = Color.White,
                containerColor = Color.DarkGray,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Create")
            }
        },
        topBar = { TopAppBarView() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (contactList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(contactList) { contact ->
                        ContactItem(contact = contact, viewModel = viewModel(),navController)
                    }
                }
            } else {
                Text(
                    text = "No contacts available",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView() {
    TopAppBar(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        title = {
            Text(
                text = "All Contacts",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily.SansSerif
            )
        },
    )
}

@Composable
fun ContactItem(contact: Contact,viewModel: ContactViewModel,navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Column for contact details
            Column(
                modifier = Modifier.padding(10.dp) // Take available space
            ) {
                Text(
                    text = "Name: ${contact.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Phone: ${contact.phone}",
                    fontSize = 16.sp
                )
                Text(
                    text = "Email: ${contact.email}",
                    fontSize = 16.sp
                )
            }
            // Spacer for spacing between details and icons
            Spacer(modifier = Modifier.width(20.dp))

            // Column for icons
            Column(
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { viewModel.deleteContact(contact.contactId) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(onClick = {navController.navigate("editscreen/${contact.contactId}") }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }

    }

}
