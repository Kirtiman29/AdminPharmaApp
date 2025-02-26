package com.example.adminpharmaapp.navigation

sealed class Screen(val route: String) {
    object updateProduct : Screen("updateProductScreen/{productId}")

    fun createRoute(productId: String) = "updateProductScreen/$productId"
}