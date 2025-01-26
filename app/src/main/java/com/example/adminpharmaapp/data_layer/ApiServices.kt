package com.example.adminpharmaapp.data_layer

import com.example.adminpharmaapp.data_layer.response.AddProductResponse
import com.example.adminpharmaapp.data_layer.response.DeleteSpecificUserResponce
import com.example.adminpharmaapp.data_layer.response.GetProductResponse
import com.example.adminpharmaapp.data_layer.response.OrderDetailResponce
import com.example.adminpharmaapp.data_layer.response.UpdateOrderResponse
import com.example.adminpharmaapp.data_layer.response.UpdateProductResponse
import com.example.adminpharmaapp.data_layer.response.UpdateUserResponse
import com.example.adminpharmaapp.data_layer.response.getAllUserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @GET("getAllUsers")
    suspend fun getAllUser(

    ): Response<getAllUserResponse>

    @FormUrlEncoded
    @PATCH("update_userDetail")
    suspend fun updateAllUserDetail(
        @Field("user_id") user_id: String,
        @Field("isAprooved") isAprooved: Int,
    ): Response<UpdateUserResponse>

    @FormUrlEncoded
    @PATCH("updateUserOrderTable")
    suspend fun UpdateOrder(
        @Field("order_id") order_id: String,
        @Field("isApproved") isApproved: Int,

    ): Response<UpdateOrderResponse>

    @FormUrlEncoded
    @PATCH("update_adminProduct")
    suspend fun updateAdminProduct(
        @Field("product_id") product_id: String,
        @Field("product_name") product_name: String,
        @Field("stock") stock: Int,
        @Field("price") price: Double,
        @Field("category") category: String,
        @Field("expiry_date") expiry_date: String,
    ): Response<UpdateProductResponse>


    @FormUrlEncoded
    @POST("add_product")
    suspend fun addProduct(
        @Field("product_name") product_name: String,
        @Field("stock") stock: Int,
        @Field("price") price: Int,
        @Field("category") category: String,
        //@Field("expiry_date") expiry_date:
    ): Response<AddProductResponse>


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteSpecificUser", hasBody = true)
    suspend fun delete_SpecificUser(
        @Field("user_id") user_id: String,
    ): Response<DeleteSpecificUserResponce>


    @GET("get_all_product")
    suspend fun getAllProduct(): Response<GetProductResponse>

    @GET("get_order_detail")
    suspend fun getOrderDetail(): Response<OrderDetailResponce>

}