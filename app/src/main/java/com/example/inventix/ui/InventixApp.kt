package com.example.inventix.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inventix.ui.components.InventixBottomBar
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.screens.ChooseRoleScreen
import com.example.inventix.ui.screens.DeliveryScreen
import com.example.inventix.ui.screens.LoginScreen
import com.example.inventix.ui.screens.MenuScreen
import com.example.inventix.ui.screens.OrdersScreen
import com.example.inventix.ui.screens.ProductsScreen
import com.example.inventix.ui.screens.PurchaseOrderScreen
import com.example.inventix.ui.screens.ReportsScreen
import com.example.inventix.ui.screens.SuppliersScreen

object Routes {
    const val CHOOSE_ROLE = "choose_role"
    const val LOGIN = "login"
    const val PRODUCTS = "products"
    const val ORDERS = "orders"
    const val REPORTS = "reports"
    const val DELIVERY = "delivery"
    const val SUPPLIERS = "suppliers"
    const val MENU = "menu"
    const val PURCHASE_ORDER = "purchase_order"
}

@Composable
fun InventixApp(appViewModel: AppViewModel) {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomBarRoutes = setOf(
        Routes.PRODUCTS,
        Routes.ORDERS,
        Routes.REPORTS,
        Routes.DELIVERY,
        Routes.SUPPLIERS
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (currentRoute in bottomBarRoutes && appViewModel.role != null) {
                InventixBottomBar(
                    role = appViewModel.role ?: UserRole.CUSTOMER,
                    currentRoute = currentRoute,
                    onSelect = { route ->
                        navController.navigate(route) { launchSingleTop = true }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.CHOOSE_ROLE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.CHOOSE_ROLE) {
                ChooseRoleScreen(
                    onRolePicked = { role ->
                        appViewModel.chooseRole(role)
                        navController.navigate(Routes.LOGIN) { launchSingleTop = true }
                    }
                )
            }
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLogin = {
                        navController.navigate(Routes.PRODUCTS) {
                            popUpTo(Routes.CHOOSE_ROLE) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.PRODUCTS) {
                ProductsScreen(
                    role = appViewModel.role ?: UserRole.CUSTOMER,
                    hasProducts = appViewModel.hasProducts,
                    onAddProducts = { appViewModel.addFirstProduct() },
                    onOpenMenu = { navController.navigate(Routes.MENU) },
                    onOpenPurchaseOrder = { navController.navigate(Routes.PURCHASE_ORDER) }
                )
            }
            composable(Routes.ORDERS) {
                OrdersScreen(
                    onOpenMenu = { navController.navigate(Routes.MENU) },
                    onOpenOrder = { navController.navigate(Routes.PURCHASE_ORDER) }
                )
            }
            composable(Routes.REPORTS) {
                ReportsScreen(onOpenMenu = { navController.navigate(Routes.MENU) })
            }
            composable(Routes.DELIVERY) {
                DeliveryScreen(onOpenMenu = { navController.navigate(Routes.MENU) })
            }
            composable(Routes.SUPPLIERS) {
                SuppliersScreen(onOpenMenu = { navController.navigate(Routes.MENU) })
            }
            composable(Routes.MENU) {
                MenuScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        appViewModel.logout()
                        navController.navigate(Routes.CHOOSE_ROLE) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.PURCHASE_ORDER) {
                PurchaseOrderScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
