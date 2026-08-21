package com.example.inventix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.components.InventixSearchField
import com.example.inventix.ui.components.InventixTopBar
import com.example.inventix.ui.components.StatusBadge
import com.example.inventix.ui.data.BadgeType
import com.example.inventix.ui.data.Orders
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.DarkHeading
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.UnselectedTabBlack

@Composable
fun OrdersScreen(
    hasOrders: Boolean,
    onOpenMenu: () -> Unit,
    onOpenOrder: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var showDelivered by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Orders", showBack = false, onLeadingClick = onOpenMenu)
        if (!hasOrders) {
            NoOrdersYetState()
            return@Column
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InventixSearchField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = "Search products..."
                )
            }
            item {
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                        Text(
                            text = "Pending",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!showDelivered) ActiveGreen else UnselectedTabBlack,
                            fontFamily = Inter,
                            modifier = Modifier.clickable { showDelivered = false }
                        )
                        Text(
                            text = "Delivered",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (showDelivered) ActiveGreen else UnselectedTabBlack,
                            fontFamily = Inter,
                            modifier = Modifier.clickable { showDelivered = true }
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
            val orders = Orders.filter {
                it.status == (if (showDelivered) BadgeType.DELIVERED else BadgeType.PENDING) &&
                    it.store.contains(search, ignoreCase = true)
            }
            items(orders) { order ->
                OrderCard(order = order, onClick = onOpenOrder)
            }
        }
    }
}

@Composable
private fun OrderCard(order: com.example.inventix.ui.data.Order, onClick: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = order.id,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                modifier = Modifier.weight(1f),
                fontFamily = Inter
            )
            StatusBadge(type = order.status)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = order.store,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkHeading,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = order.location, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = order.amount,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                modifier = Modifier.weight(1f),
                fontFamily = Inter
            )
            Text(text = order.date, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${order.itemCount} items",
                fontSize = 12.sp,
                color = MutedText,
                fontFamily = Inter
            )
        }
    }
}

@Composable
private fun NoOrdersYetState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ReceiptLong,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No orders yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkValue,
            fontFamily = Inter
        )
    }
}
