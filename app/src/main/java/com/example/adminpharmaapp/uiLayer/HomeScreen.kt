package com.example.adminpharmaapp.uiLayer

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.adminpharmaapp.navigation.AddProduct
import com.example.adminpharmaapp.viewModel.AppViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {
    val state = viewModel.getAllUserState.collectAsState()
    val userData = state.value.data?.body() ?: emptyList()
    val updateState = viewModel.updateUserState.collectAsState()
    val context = LocalContext.current

    val specificDeleteState = viewModel.deleteSpecificUserState.collectAsState()


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )

    when{
        specificDeleteState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

    }
        specificDeleteState.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Toast.makeText(context, "${specificDeleteState.value.Error}"
                    , Toast.LENGTH_SHORT).show()

                Log.d("Delete" , "${specificDeleteState.value.Error}")
            }
        }
        specificDeleteState.value.Data != null -> {
            Toast.makeText(context, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            Log.d("message","${specificDeleteState.value.Data}")

        }
    }


    when {
        updateState.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        updateState.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${updateState.value.error}")
            }
        }

        updateState.value.Data != null -> {
            Toast.makeText(context, "User status updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff4294FF)
                ),
                title = {
                    Text(text = "Admin Panel")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(AddProduct)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "Add",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )

        }
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(20.dp))


        // Handle User List State
        when {
            state.value.loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.value.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.value.error}")
                }

            }

            state.value.data != null -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(userData) { userData ->
                        EachCard(
                            name = userData.name,
                            email = userData.email,
                            phone = userData.phone_number,
                            userId = userData.user_id,
                            isApproved = userData.isAprooved,
                            address = userData.address,
                            pincode = userData.pincode,
                            block = userData.block.toString(),
                            level = userData.level.toString(),
                            date = userData.date_of_account_creation,
                            navHostController = navController,
                            onClickApproved = {
                                viewModel.updateUser(
                                    user_id = userData.user_id,
                                    isAprooved = 1
                                )
                            },
                            onClickDelete = {
                                viewModel.deleteSpecificUser(
                                    user_id = userData.user_id
                                )

                            }


                        )


                    }
                }
            }
        }
    }
}

@Composable
fun EachCard(
    name: String,
    email: String,
    phone: String,
    userId: String,
    isApproved: Int,
    address: String,
    pincode: String,
    block: String,
    level: String,
    date: String,
    navHostController: NavHostController,
    onClickApproved: () -> Unit,
    onClickDelete: () -> Unit,// Pass callback function correctly
) {
    var approved by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = approved) {
        if (approved) {
            // Perform additional side effects here
            Log.d("EachCard", "User $name approved")
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = "Name: $name",
                modifier = Modifier.padding(start = 10.dp),

                )
            Text(
                text = "Email: $email",
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "Phone: $phone",
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "User ID: $userId",
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                if (isApproved == 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onClickApproved()

                            }

                        ) {
                            Text(text = "Approve")
                        }
                    }
                } else {
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Block")
                    }
                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(onClick = {
                    onClickDelete()
                }) {
                    Text(text = "Delete")
                }


            }
        }
    }
}
