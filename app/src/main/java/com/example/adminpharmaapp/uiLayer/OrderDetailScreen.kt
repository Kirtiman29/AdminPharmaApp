package com.example.adminpharmaapp.uiLayer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.adminpharmaapp.viewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {

    val state = viewModel.getOrderDetailState.collectAsState()
    val orderDetail = state.value.Data?.body() ?: emptyList()
    val context = LocalContext.current
    val updateOrderState = viewModel.updateOrderState.collectAsState()

    when {
        updateOrderState.value.Error != null -> {
            Toast.makeText(context, "${updateOrderState.value.Error}", Toast.LENGTH_SHORT).show()
        }

        updateOrderState.value.Data != null -> {
            Toast.makeText(context, "Order status updated successfully!", Toast.LENGTH_SHORT).show()
            Log.d("message", "${updateOrderState.value.Data}")
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff4294FF)
                ),
                title = { Text(text = "Order Details") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
            )
        }
    ) { paddingValues ->
        when {
            state.value.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            state.value.Error != null -> {
                Toast.makeText(context, "${state.value.Error}", Toast.LENGTH_SHORT).show()

            }

            state.value.Data != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(orderDetail) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(300.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "User Name: ${it.user_name}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Product Name: ${it.product_name}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Category: ${it.category}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Price: ${it.price.toString()}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Category: ${it.quantity.toString()}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Total Amount: ${it.total_amount}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, top = 10.dp)
                                ) {
                                    if (it.isApproved == 0){
                                        Button(onClick = {
                                            viewModel.updateOrder(
                                                order_id = it.order_id,
                                                isApproved = 1
                                            )
                                            Log.d("TAG", "OrderDetailScreen: ${it.order_id}")
                                        }) {
                                            Text(text = "Confirm")
                                        }
                                    }else{
                                        Button(onClick = {}) {
                                            Text(text = "Approve")
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(100.dp))
                                    Button(onClick = {}) {
                                        Text(text = "Delete")
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }


}