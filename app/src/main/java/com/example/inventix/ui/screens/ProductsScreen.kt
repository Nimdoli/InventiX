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
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.inventix.ui.components.ProductCard
import com.example.inventix.ui.data.BadgeType
import com.example.inventix.ui.data.CustomerProducts
import com.example.inventix.ui.data.DonutSegment
import com.example.inventix.ui.data.StockOverviewSegments
import com.example.inventix.ui.data.SupplierProducts
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.theme.AccentYellow
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.AddButtonBrown
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.DarkHeading
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.PageTitleOlive
import com.example.inventix.ui.theme.SupplierAddGold
import com.example.inventix.ui.theme.UnselectedTabBlack

@Composable
fun ProductsScreen(
    role: UserRole,
    hasProducts: Boolean,
    onAddProducts: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenPurchaseOrder: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Products", showBack = false, onLeadingClick = onOpenMenu)
        when {
            !hasProducts -> EmptyProductsState(onAddProducts)
            role == UserRole.CUSTOMER -> CustomerProductsContent()
            else -> SupplierProductsContent(onOpenPurchaseOrder)
        }
    }
}

@Composable
private fun EmptyProductsState(onAddProducts: () -> Unit) {
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
                .clickable(onClick = onAddProducts)
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
}

@Composable
private fun CustomerProductsContent() {
    var search by remember { mutableStateOf("") }
    var lowStockOnly by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Products",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = PageTitleOlive,
                fontFamily = Inter
            )
        }
        item {
            InventixSearchField(
                value = search,
                onValueChange = { search = it },
                placeholder = "Search Products",
                showFilterIcon = true
            )
        }
        item { StockOverviewCard() }
        item {
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
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
        val products = CustomerProducts
            .filter { if (lowStockOnly) it.status == BadgeType.LOW_STOCK else true }
            .filter { it.name.contains(search, ignoreCase = true) }
        items(products) { product ->
            ProductCard(
                name = product.name,
                category = product.category,
                price = product.price,
                stock = product.stock,
                status = product.status
            )
        }
    }
}

@Composable
private fun StockOverviewCard() {
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
                DonutChart(segments = StockOverviewSegments, total = 550)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "550",
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
                StockOverviewSegments.forEach { segment ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(segment.color)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${segment.label} \u2014 ${segment.value} (${segment.percentText})",
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
private fun SupplierProductsContent(onOpenPurchaseOrder: () -> Unit) {
    var search by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp
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
                        .clickable(onClick = onOpenPurchaseOrder)
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
        val products = SupplierProducts.filter {
            it.name.contains(search, ignoreCase = true)
        }
        items(products) { product ->
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
}
