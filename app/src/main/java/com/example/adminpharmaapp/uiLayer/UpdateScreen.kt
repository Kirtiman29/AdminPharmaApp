package com.example.adminpharmaapp.uiLayer

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.adminpharmaapp.viewModel.AppViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun UpdateScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel(), ) {

    val context = LocalContext.current
    val state = viewModel.getAllProductState.collectAsState()
    val showData = state.value.Data?.body() ?: emptyList()



    var productName by mutableStateOf("")
    var stock by mutableStateOf("")
    var price by mutableStateOf("")
    var category by mutableStateOf("")
    var expireDate by mutableStateOf("")
    var productId by mutableStateOf("")
    val updateState = viewModel.updateProductState.collectAsState()
    for (data in showData) {
        productId = data.product_id
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(value = productName,
            onValueChange = {
                productName = it
            },
            placeholder = {
                Text(text = "Product Name  ")
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = stock,
            onValueChange = {
                stock = it
            },
            placeholder = {
                Text(text = "Enter the Stock ")
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = price,
            onValueChange = {
                price = it
            },
            placeholder = {
                Text(text = "Enter the Price  ")
            })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = category,
            onValueChange = {
                category = it
            },
            placeholder = {
                Text(text = "Enter Category  ")
            })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = expireDate,
            onValueChange = {
                expireDate = it
            },
            placeholder = {
                Text(text = "Enter Expire Date  ")
            })

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            when {
                updateState.value.Error != null -> {
                    Toast.makeText(context, "${updateState.value.Error}", Toast.LENGTH_SHORT).show()
                }

                updateState.value.Data != null-> {

                    viewModel.updateProduct(
                        product_name = productName,
                        stock = stock.toInt(),
                        price = price.toDouble(),
                        category = category,
                        expiry_date = expireDate,
                        product_id = productId
                    )
                    Toast.makeText(context, "${updateState.value.Data}", Toast.LENGTH_SHORT).show()

                }
            }



        }) {
            Text(text = "Update Product")
        }
    }

}