package com.example.adminpharmaapp.data_layer.response

data class GetAllSellsHistoryResponseItem (
    var product_id: String,
    var quantity: Int,
    var remaining_stock: Int,
    var price: Float,
    var total_amount: Int,
    var user_id: String,
    var product_name: String,
    var user_name: String,
    var date_of_sale: String,
    val id: Int,
    var sell_id: String


)