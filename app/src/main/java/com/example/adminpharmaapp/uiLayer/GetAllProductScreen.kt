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
import androidx.compose.material3.rememberTopAppBarState
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
import com.example.adminpharmaapp.navigation.UpdateProduct
import com.example.adminpharmaapp.uiLayer.UpdateScreen
import com.example.adminpharmaapp.viewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllProductScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel(),
) {
    val state = viewModel.getAllProductState.collectAsState()
    val showData = state.value.Data?.body() ?: emptyList()
    val context = LocalContext.current
    val updateState = viewModel.updateProductState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff4294FF)
                ),
                title = { Text(text = "All Product") },
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
                    items(showData) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(250.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Product Name: ${product.product_name}",
                                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Stock: ${product.stock}",
                                    modifier = Modifier.padding(start = 20.dp,top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Price: ${product.price}",
                                    modifier = Modifier.padding(start = 20.dp,top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Category: ${product.category}",
                                    modifier = Modifier.padding(start = 20.dp,top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Expire Date: ${product.expire_date}",
                                    modifier = Modifier.padding(start = 20.dp,top = 10.dp),
                                    fontSize = 20.sp
                                )
                                Log.d("Product", product.product_id)

                                Row (
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(start = 20.dp,top = 10.dp)
                                ){
                                    Button(onClick = {
                                        navController.navigate(UpdateProduct)

                                    }) {
                                        Text(text = "Update")
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
