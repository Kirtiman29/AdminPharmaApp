package com.example.adminpharmaapp.uiLayer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var refreshTrigger by remember { mutableStateOf(false) }
    val state = viewModel.getOrderDetailState.collectAsState()
    val orderDetail = state.value.Data?.body() ?: emptyList()
    val context = LocalContext.current
    val updateOrderState = viewModel.updateOrderState.collectAsState()

    //val getProductState = viewModel.getSpecificProductState.collectAsState()
    val getProductState = viewModel.getSpecificProductState.collectAsState()
    var stock by remember { mutableStateOf(0) }
    var value1 = getProductState.value.Data?.body()
    Log.d("om", "Om:  $value1")




    LaunchedEffect(refreshTrigger) {
        viewModel.getOrderDetail()
    }

    LaunchedEffect(updateOrderState.value) {
        updateOrderState.value.Data?.let {
            Toast.makeText(context, "Order status updated successfully!", Toast.LENGTH_SHORT).show()
            Log.d("message", "$it")
        }
        updateOrderState.value.Error?.let {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff4294FF)),
                title = { Text(text = "Order Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.value.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                    items(orderDetail) { order ->
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
                                Text(text = "User Name: ${order.user_name}", fontSize = 20.sp)
                                Text(text = "Product Name: ${order.product_name}", fontSize = 20.sp)
                                Text(text = "Category: ${order.category}", fontSize = 20.sp)
                                Text(text = "Price: ${order.price}", fontSize = 20.sp)
                                Text(text = "Quantity: ${order.quantity}", fontSize = 20.sp)
                                Text(text = "Total Amount: ${order.total_amount}", fontSize = 20.sp)



                                Row(modifier = Modifier.fillMaxWidth()) {
                                    if (order.isApproved == 0) {
                                        Button(onClick = {
                                            viewModel.updateOrder(order.order_id, isApproved = 1)
                                           viewModel.getSpecificProduct(product_id = order.product_id)
//                                            val stock = getProductState.value.Data?.body()?.stock
//                                            viewModel.updateAdminProductStock(product_id = order.product_id,
//                                                stock = stock!! - order.quantity)
                                            refreshTrigger = !refreshTrigger
                                        },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Red,
                                                contentColor = Color.Black
                                            )) {
                                            Text(text = "Confirm")
                                        }
                                    } else {
                                        Button(onClick = {},
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Green,
                                                contentColor = Color.Black
                                            )) {
                                            Text(text = "Approved")
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(100.dp))
                                    Button(onClick = {},
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Black,
                                            contentColor = Color.White
                                        )) {
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
