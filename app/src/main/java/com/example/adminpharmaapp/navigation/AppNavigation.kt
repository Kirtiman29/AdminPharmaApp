package com.example.adminpharmaapp.navigation

import GetAllProductScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminpharmaapp.uiLayer.AddProductScreen

import com.example.adminpharmaapp.uiLayer.HomeScreen
import com.example.adminpharmaapp.uiLayer.OrderDetailScreen
import com.example.adminpharmaapp.uiLayer.UpdateScreen

import kotlinx.coroutines.launch

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()




    Box {
        val items = listOf(
            BottomNavItem(
                title = "Products",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavItem(
                title = "Add",
                selectedIcon = Icons.Filled.Add,
                unselectedIcon = Icons.Outlined.Add
            ),
            BottomNavItem(
                title = "Orders",
                selectedIcon = Icons.Filled.ShoppingCart,
                unselectedIcon = Icons.Outlined.ShoppingCart
            ),
            BottomNavItem(
                title = "Users",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                coroutineScope.launch {
                                    when (index) {
                                        0 -> navController.navigate(GetAllProduct)
                                        1 -> navController.navigate(AddProduct)
                                        2 -> navController.navigate(OrderDetail)
                                        3 -> navController.navigate(Home)

                                    }
                                }


                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                if (selectedItemIndex == index) {
                                    Icon(
                                        imageVector = item.selectedIcon,
                                        contentDescription = item.title
                                    )
                                } else {
                                    Icon(
                                        imageVector = item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }

                }

            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = GetAllProduct) {


                    composable<Home> {
                        HomeScreen(navController)
                    }
                    composable<AddProduct> {
                        AddProductScreen(navController)
                    }
                    composable<GetAllProduct> {
                        GetAllProductScreen(navController)
                    }
                    composable<OrderDetail> {
                        OrderDetailScreen(navController)
                    }

                    composable<UpdateProduct> {
                        UpdateScreen(navController)

                    }


                }
            }
        }


    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)