import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun UpdateScreen(
    productId: String,
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    //val state = viewModel.getAllProductState.collectAsState()
    //val showData = state.value.Data?.body() ?: emptyList()
    //val product = showData.firstOrNull() // Get the first product
    val updateState = viewModel.updateProductState.collectAsState()
    var updateData = updateState.value.Data?.body()
    val productState = viewModel.getSpecificProductState.collectAsState()
    val productData = productState.value.Data?.body()

    var productName by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var expireDate by remember { mutableStateOf("") }
    // val productId = product?.product_id ?: ""

    LaunchedEffect(productId) {
        viewModel.getSpecificProduct(productId)
    }
    LaunchedEffect(productData) {
        productData.let {
            productName = it?.product_name ?: ""
            stock = it?.stock.toString()
            price = it?.price.toString()
            category = it?.category ?: ""
            expireDate = it?.expire_date ?: ""
        }
    }

    when {
        updateState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        updateState.value.Error != null -> {
            Toast.makeText(context, "${updateState.value.Error}", Toast.LENGTH_SHORT).show()
        }

        updateState.value.Data != null -> {
            Toast.makeText(context, "${updateState.value.Data?.body()}", Toast.LENGTH_SHORT).show()
        }
    }

    when {
        productState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        productState.value.Error != null -> {
            Toast.makeText(context, "${productState.value.Error}", Toast.LENGTH_SHORT).show()
        }

        productState.value.Data != null -> {
            //Toast.makeText(context, "${productState.value.Data?.body()}", Toast.LENGTH_SHORT).show()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    placeholder = { Text("Product Name") })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    placeholder = { Text("Enter Stock") })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    placeholder = { Text("Enter Price") })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    placeholder = { Text("Enter Category") })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = expireDate,
                    onValueChange = { expireDate = it },
                    placeholder = { Text("Enter Expiry Date") })
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {

                    viewModel.updateProduct(
                        product_id = productId,
                        product_name = productName,
                        stock = stock.toInt(),
                        price = price.toDouble(),
                        category = category,
                        expiry_date = expireDate
                    )

                }) {
                    Text(text = "Update Product")
                }
            }
        }

    }


}
