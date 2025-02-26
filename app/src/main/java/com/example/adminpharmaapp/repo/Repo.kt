package com.example.adminpharmaapp.repo

import com.example.adminpharmaapp.State
import com.example.adminpharmaapp.data_layer.ApiProvider
import com.example.adminpharmaapp.data_layer.response.DeleteSpecificUserResponce
import com.example.adminpharmaapp.data_layer.response.GetProductResponse
import com.example.adminpharmaapp.data_layer.response.OrderDetailResponce
import com.example.adminpharmaapp.data_layer.response.UpdateApminProductStockResponce
import com.example.adminpharmaapp.data_layer.response.UpdateProductResponse
import com.example.adminpharmaapp.data_layer.response.getAllUserResponse
import com.example.adminpharmaapp.data_layer.response.getAllUserResponseItem
import com.example.adminpharmaapp.data_layer.response.getSpecificProductResponce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class Repo {


    suspend fun updateAdminProductStockRepo(
        product_id: String,
        stock: Int,
    ): Flow<State<Response<UpdateApminProductStockResponce>>> = flow {
        emit(State.Loading)
        try {
            val updateAdminStockResponce = ApiProvider.provideApi()
                .updateAdminProductStock(
                    product_id = product_id,
                    stock = stock
                )
            emit(State.Success(updateAdminStockResponce))
        } catch (e: Exception){
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getSpecificProductRepo(
        product_id: String,
    ): Flow<State<Response<getSpecificProductResponce>>> = flow {
        emit(State.Loading)
        try{
            val getSpecificProductRes = ApiProvider.provideApi()
                .getSpecificProduct(product_id = product_id)
            emit(State.Success(getSpecificProductRes))

        } catch (e: Exception){
            emit(State.Error(e.message.toString()))
        }
    }


    suspend fun updateOrderRepo(
        order_id: String,
        isApproved: Int,
    ): Flow<State<Response<getAllUserResponse>>> = flow {
        emit(State.Loading)
        try {
            val updateOrderResponse = ApiProvider.provideApi()
                .UpdateOrder(order_id = order_id, isApproved = isApproved)
            emit(State.Success(updateOrderResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }


    suspend fun getOrderDetailRepo(): Flow<State<Response<OrderDetailResponce>>> = flow {
        emit(State.Loading)
        try {
            val getOrderDetailResponse = ApiProvider.provideApi().getOrderDetail()
            emit(State.Success(getOrderDetailResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getAllProductRepo(): Flow<State<Response<GetProductResponse>>> = flow {
        emit(State.Loading)

        try {
            val getAllProductResponse = ApiProvider.provideApi().getAllProduct()
            emit(State.Success(getAllProductResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun deleteSpecificUserRepo(
        user_id: String,
    ): Flow<State<Response<DeleteSpecificUserResponce>>> = flow {
        emit(State.Loading)

        try {
            val specificDeleteResponse = ApiProvider.provideApi()
                .delete_SpecificUser(user_id = user_id)
            emit(State.Success(specificDeleteResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getAllUserRepo(): Flow<State<Response<getAllUserResponse>>> = flow {
        emit(State.Loading)

        try {
            val response = ApiProvider.provideApi().getAllUser()
            emit(State.Success(response))

        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }

    suspend fun approvedUserRepo(
        user_id: String,
        isAprooved: Int,

        ): Flow<State<Response<getAllUserResponse>>> = flow {
        emit(State.Loading)
        try {
            val approvedResponse = ApiProvider.provideApi()
                .updateAllUserDetail(user_id = user_id, isAprooved = isAprooved)
            emit(State.Success(approvedResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))

        }
    }

    suspend fun updateProductRepo(
        product_id: String,
        product_name: String,
        stock: Int,
        price: Double,
        category: String,
        expiry_date: String,

    ): Flow<State<Response<UpdateProductResponse>>> = flow {
        emit(State.Loading)

        try {
            val updateProductResponse = ApiProvider.provideApi().updateAdminProduct(
                product_id = product_id,
                product_name = product_name,
                stock = stock,
                price = price,
                category = category,
                expiry_date = expiry_date
            )
            emit(State.Success(updateProductResponse))


        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }


    suspend fun AddProductRepo(
        product_name: String,
        stock: Int,
        price: Int,
        category: String,
        // expiry_date: String
    ): Flow<State<Response<getAllUserResponse>>> = flow {
        emit(State.Loading)
        try {
            val addProductResponse = ApiProvider.provideApi().addProduct(
                product_name = product_name,
                stock = stock,
                price = price,
                category = category
            )
            emit(State.Success(addProductResponse))

        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }
}
