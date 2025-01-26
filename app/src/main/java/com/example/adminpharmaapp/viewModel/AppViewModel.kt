package com.example.adminpharmaapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminpharmaapp.State
import com.example.adminpharmaapp.data_layer.response.AddProductResponse
import com.example.adminpharmaapp.data_layer.response.DeleteSpecificUserResponce
import com.example.adminpharmaapp.data_layer.response.GetProductResponse
import com.example.adminpharmaapp.data_layer.response.OrderDetailResponce
import com.example.adminpharmaapp.data_layer.response.UpdateOrderResponse
import com.example.adminpharmaapp.data_layer.response.UpdateProductResponse
import com.example.adminpharmaapp.data_layer.response.UpdateUserResponse
import com.example.adminpharmaapp.data_layer.response.getAllUserResponse
import com.example.adminpharmaapp.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo) : ViewModel() {
    private val _getAllUserState = MutableStateFlow(GetAllUserState())
    val getAllUserState = _getAllUserState.asStateFlow()

    private val _updateUserState = MutableStateFlow(UpdateUserState())
    val updateUserState = _updateUserState.asStateFlow()


    private val _addProductState = MutableStateFlow(AddProductState())
    val addProductState = _addProductState.asStateFlow()

    private val _deleteSpecificUserState =
        MutableStateFlow(DeleteSpecificUserState())
    val deleteSpecificUserState = _deleteSpecificUserState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getOrderDetailState = MutableStateFlow(GetOrderDetailState())
    val getOrderDetailState = _getOrderDetailState.asStateFlow()



    private val _updateProductState = MutableStateFlow(UpdateProductState())
    val updateProductState = _updateProductState.asStateFlow()

    private val _updateOrderState = MutableStateFlow(UpdateOrderState())
    val updateOrderState = _updateOrderState.asStateFlow()

    fun updateOrder(
        order_id: String,
        isApproved: Int,

    ){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateOrderRepo(
                order_id = order_id,
                isApproved = isApproved
            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateOrderState.value = UpdateOrderState(Loading = true)
                    }
                    is State.Success -> {
                        _updateOrderState.value = UpdateOrderState(Data = it.data as Response<UpdateOrderResponse> , Loading = false)
                    }
                    is State.Error -> {
                        _updateOrderState.value = UpdateOrderState(Error = it.message, Loading = false)
                    }
                }
            }
        }
    }

    fun updateProduct(
        product_id: String,
        product_name: String,
        stock: Int,
        price: Double,
        category: String,
        expiry_date: String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateProductRepo(
                product_id = product_id,
                product_name = product_name,
                stock = stock,
                price = price,
                category = category,
                expiry_date = expiry_date


            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateProductState.value = UpdateProductState(Loading = true)
                    }
                    is State.Success -> {
                        _updateProductState.value = UpdateProductState(Data = it.data as Response<UpdateProductResponse> , Loading = false)
                    }
                    is State.Error -> {
                        _updateProductState.value = UpdateProductState(Error = it.message, Loading = false)
                    }
                }
            }
        }

    }


    init {
        getAllProduct()
    }
    fun getAllProduct(){

        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProductRepo().collect{
                when(it){
                    is State.Loading -> {
                        _getAllProductState.value = GetAllProductState(Loading = true)
                    }
                    is State.Success -> {
                        _getAllProductState.value = GetAllProductState(Data = it.data as Response<GetProductResponse> , Loading = false)
                        Log.d("TAG", "getAllProduct: ${it.data}")
                    }
                    is State.Error -> {
                        _getAllProductState.value = GetAllProductState(Error = it.message, Loading = false)
                    }

                }
            }
        }
    }


    fun deleteSpecificUser(
        user_id: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSpecificUserRepo(
                user_id = user_id
            ).collect {
                when (it) {
                    is State.Loading -> {
                        _deleteSpecificUserState.value =
                            DeleteSpecificUserState(Loading = true)
                    }
                    is State.Success -> {
                        _deleteSpecificUserState.value =
                            DeleteSpecificUserState(
                            Data = it.data as Response<DeleteSpecificUserResponce>,
                            Loading = false
                        )

                    }
                    is State.Error -> {
                        _deleteSpecificUserState.value =
                            DeleteSpecificUserState(
                            Error = it.message,
                            Loading = false
                        )

                    }
                }
            }
        }
    }


    fun addProduct(
        product_name: String,
        stock: Int,
        price: Int,
        category: String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.AddProductRepo(
                product_name = product_name,
                stock = stock,
                price = price,
                category = category
                ).collect{
                    when(it){
                        is State.Loading -> {
                            _addProductState.value = AddProductState(Loading = true)
                        }
                        is State.Success -> {
                            _addProductState.value = AddProductState(Data = it.data as Response<AddProductResponse>, Loading = false)
                        }
                        is State.Error -> {
                            _addProductState.value = AddProductState(Error = it.message, Loading = false)

                        }

                    }
            }
        }
    }


    fun updateUser(
        user_id: String,
        isAprooved: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.approvedUserRepo(
                user_id = user_id,
                isAprooved = isAprooved
            ).collect {
                when (it) {
                    is State.Loading -> {
                        _updateUserState.value = UpdateUserState(loading = true)
                    }

                    is State.Success -> {
                        _updateUserState.value =
                            UpdateUserState(Data = it.data as Response<UpdateUserResponse>, loading = false)
                    }

                    is State.Error -> {
                        _updateUserState.value = UpdateUserState(error = it.message, loading = false)
                    }
                }
            }

        }
    }



    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllUserRepo().collect {
                when (it) {
                    is State.Loading -> {
                        _getAllUserState.value = GetAllUserState(loading = true)
                    }

                    is State.Success -> {
                        _getAllUserState.value =
                            GetAllUserState(data = it.data as Response<getAllUserResponse>)

                    }

                    is State.Error -> {
                        _getAllUserState.value = GetAllUserState(error = it.message)
                    }
                }
            }
        }

    }


    init {
        getOrderDetail()
    }
    fun getOrderDetail(){
        viewModelScope.launch (Dispatchers.IO){
            repo.getOrderDetailRepo()
                .collect{
                    when(it){
                        is State.Loading -> {
                            _getOrderDetailState.value = GetOrderDetailState(Loading = true)
                        }
                        is State.Success -> {
                            _getOrderDetailState.value = GetOrderDetailState(Data = it.data as Response<OrderDetailResponce>, Loading = false)
                        }
                        is State.Error -> {
                            _getOrderDetailState.value = GetOrderDetailState(Error = it.message, Loading = false)
                        }
                    }
                }
        }

    }


}

data class GetAllUserState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: Response<getAllUserResponse>? = null,
)

data class UpdateUserState(
    val loading: Boolean = false,
    val error: String? = null,
    val Data: Response<UpdateUserResponse>? = null,
)

data class AddProductState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<AddProductResponse>? = null,
)

data class DeleteSpecificUserState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<DeleteSpecificUserResponce>? = null,
)


data class GetAllProductState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetProductResponse>? = null,
)

data class GetOrderDetailState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<OrderDetailResponce>? = null,
)

data class UpdateProductState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateProductResponse>? = null,
)

data class UpdateOrderState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateOrderResponse>? = null,
)

