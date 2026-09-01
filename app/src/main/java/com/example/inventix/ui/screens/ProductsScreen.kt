package com.example.inventix.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.components.InventixSearchField
import com.example.inventix.ui.components.InventixTopBar
import com.example.inventix.ui.components.PillInputField
import com.example.inventix.ui.components.ProductCard
import com.example.inventix.ui.data.BadgeType
import com.example.inventix.ui.data.DonutSegment
import com.example.inventix.ui.data.Product
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.theme.AccentYellow
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.AddButtonBrown
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.DarkHeading
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.SupplierAddGold
import com.example.inventix.ui.theme.UnselectedTabBlack

@Composable
fun ProductsScreen(
    role: UserRole,
    products: List<Product>,
    productsLoading: Boolean,
    productsError: String?,
    stockOverviewSegments: List<DonutSegment>,
    stockOverviewTotal: Int,
    onRefresh: () -> Unit,
    onAddProduct: (name: String, category: String, price: Double, stock: Int) -> Unit,
    onOpenMenu: () -> Unit,
    onOpenPurchaseOrder: () -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Product", showBack = false, onLeadingClick = onOpenMenu)
        when {
            productsLoading -> LoadingState()
            productsError != null -> ErrorState(message = productsError, onRetry = onRefresh)
            products.isEmpty() -> EmptyProductsState(onAddProduct)
            role == UserRole.CUSTOMER -> CustomerProductsContent(products, stockOverviewSegments, stockOverviewTotal, onAddProduct)
            else -> SupplierProductsContent(products, onAddProduct, onOpenPurchaseOrder)
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaroonPrimary)
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, fontSize = 14.sp, color = MutedText, textAlign = TextAlign.Center, fontFamily = Inter)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tap to retry",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonPrimary,
            fontFamily = Inter,
            modifier = Modifier.clickable(onClick = onRetry)
        )
    }
}

@Composable
private fun EmptyProductsState(
    onSubmit: (name: String, category: String, price: Double, stock: Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(CreamSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Inventory2,
                contentDescription = null,
                tint = MutedText,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(AddButtonBrown)
                .clickable { showDialog = true }
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+ Add Products",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Inter
            )
        }
    }
    if (showDialog) {
        AddProductDialog(
            onDismiss = { showDialog = false },
            onSubmit = { name, category, price, stock ->
                showDialog = false
                onSubmit(name, category, price, stock)
            }
        )
    }
}

@Composable
private fun CustomerProductsContent(
    products: List<Product>,
    stockOverviewSegments: List<DonutSegment>,
    stockOverviewTotal: Int,
    onAddProduct: (name: String, category: String, price: Double, stock: Int) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var lowStockOnly by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                InventixSearchField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = "Search Product",
                    showFilterIcon = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(SupplierAddGold)
                        .clickable { showDialog = true }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+ Add Products",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = Inter
                    )
                }
            }
        }
        if (stockOverviewSegments.isNotEmpty()) {
            item { StockOverviewCard(stockOverviewSegments, stockOverviewTotal) }
        }
        item {
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        text = "In stock",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!lowStockOnly) ActiveGreen else UnselectedTabBlack,
                        fontFamily = Inter,
                        modifier = Modifier.clickable { lowStockOnly = false }
                    )
                    Text(
                        text = "Low stock",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (lowStockOnly) ActiveGreen else UnselectedTabBlack,
                        fontFamily = Inter,
                        modifier = Modifier.clickable { lowStockOnly = true }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BorderBeige)
                )
            }
        }
        val filtered = products
            .filter { if (lowStockOnly) it.status == BadgeType.LOW_STOCK else true }
            .filter { it.name.contains(search, ignoreCase = true) }
        items(filtered) { product ->
            ProductCard(
                name = product.name,
                category = product.category,
                price = product.price,
                stock = product.stock,
                status = product.status
            )
        }
    }
    if (showDialog) {
        AddProductDialog(
            onDismiss = { showDialog = false },
            onSubmit = { name, category, price, stock ->
                showDialog = false
                onAddProduct(name, category, price, stock)
            }
        )
    }
}

@Composable
private fun StockOverviewCard(segments: List<DonutSegment>, total: Int) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Stock Overview",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkHeading,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center) {
                DonutChart(segments = segments, total = total)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$total",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkValue,
                        fontFamily = Inter
                    )
                    Text(
                        text = "Total",
                        fontSize = 11.sp,
                        color = MutedText,
                        fontFamily = Inter
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                segments.forEach { segment ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(segment.color)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${segment.label} — ${segment.value} (${segment.percentText})",
                            fontSize = 12.sp,
                            color = DarkValue,
                            fontFamily = Inter
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DonutChart(segments: List<DonutSegment>, total: Int) {
    Canvas(modifier = Modifier.size(140.dp)) {
        val stroke = 26.dp.toPx()
        val diameter = size.minDimension - stroke
        val topLeft = Offset(
            (size.width - diameter) / 2f,
            (size.height - diameter) / 2f
        )
        val arcSize = Size(diameter, diameter)
        var startAngle = -90f
        segments.forEach { segment ->
            val sweep = segment.value.toFloat() / total.toFloat() * 360f
            drawArc(
                color = segment.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Butt)
            )
            startAngle += sweep
        }
    }
}

@Composable
private fun SupplierProductsContent(
    products: List<Product>,
    onAddProduct: (name: String, category: String, price: Double, stock: Int) -> Unit,
    onOpenPurchaseOrder: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                InventixSearchField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = "Search products...",
                    showFilterIcon = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(SupplierAddGold)
                        .clickable { showDialog = true }
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+ Add Products",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = Inter
                    )
                }
            }
        }
        item {
            Text(
                text = "Product List",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = UnselectedTabBlack,
                fontFamily = Inter
            )
        }
        val filtered = products.filter { it.name.contains(search, ignoreCase = true) }
        items(filtered) { product ->
            ProductCard(
                name = product.name,
                category = product.category,
                price = product.price,
                stock = product.stock,
                status = product.status,
                onClick = if (product.status == BadgeType.LOW_STOCK) {
                    { onOpenPurchaseOrder() }
                } else {
                    null
                }
            )
        }
    }
    if (showDialog) {
        AddProductDialog(
            onDismiss = { showDialog = false },
            onSubmit = { name, category, price, stock, status ->
                showDialog = false
                onAddProduct(name, category, price, stock)
            }
        )
    }
}

@Composable
private fun AddProductDialog(
    onDismiss: () -> Unit,
    onSubmit: (name: String, category: String, price: Double, stock: Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Product", fontFamily = Inter, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PillInputField(value = name, onValueChange = { name = it }, label = "Product name", leadingIcon = Icons.Outlined.Sell)
                PillInputField(value = category, onValueChange = { category = it }, label = "Category", leadingIcon = Icons.Outlined.Category)
                PillInputField(value = price, onValueChange = { price = it }, label = "Price (LKR)", leadingIcon = Icons.Outlined.Numbers)
                PillInputField(value = stock, onValueChange = { stock = it }, label = "Stock quantity", leadingIcon = Icons.Outlined.Inventory2)
                Text(
                    text = "Stock status (In Stock / Low Stock / Out of Stock) is set automatically based on quantity.",
                    fontSize = 12.sp,
                    color = MutedText,
                    fontFamily = Inter
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val priceValue = price.toDoubleOrNull() ?: 0.0
                val stockValue = stock.toIntOrNull() ?: 0
                onSubmit(name, category, priceValue, stockValue)
            }) {
                Text("Add", fontFamily = Inter, fontWeight = FontWeight.Bold, color = MaroonPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", fontFamily = Inter)
            }
        }
    )
}
