package com.example.adminpharmaapp.uiLayer


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavHostController
import com.example.adminpharmaapp.navigation.Home
import com.example.adminpharmaapp.viewModel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {

    val state = viewModel.addProductState.collectAsState()
    val context = LocalContext.current


    var productName by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }

    when {
        state.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.value.Error}")
            }
        }

        state.value.Data != null -> {
            Toast.makeText(context, "${state.value.Data?.body()?.product_id}", Toast.LENGTH_SHORT)
                .show()

            LaunchedEffect(Unit) {
                productName = ""
                stock = ""
                price = ""
                category = ""
                //expiryDate = ""
            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff4294FF)

                ),

                title = {
                    Text(text = "Add Product")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(25.dp)
                            .clickable {
                                navController.navigate(Home)
                            }
                    )
                }
            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
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
            Button(onClick = {
                viewModel.addProduct(
                    product_name = productName,
                    stock = stock.toInt(),
                    price = price.toInt(),
                    category = category
                )


            }) {
                Text(text = "Add Product")
            }
        }
    }

}