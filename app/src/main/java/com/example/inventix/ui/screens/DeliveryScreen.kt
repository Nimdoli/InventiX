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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
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
import com.example.inventix.ui.data.Deliveries
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.UnselectedTabBlack

@Composable
fun DeliveryScreen(hasDeliveries: Boolean, onOpenMenu: () -> Unit) {
    var search by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(BadgeType.IN_TRANSIT) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Delivery", showBack = false, onLeadingClick = onOpenMenu)
        if (!hasDeliveries) {
            NoDeliveriesYetState()
            return@Column
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp),
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
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        StatusTab(
                            label = "Pending",
                            type = BadgeType.PENDING,
                            selected = selectedTab,
                            onSelect = { selectedTab = it }
                        )
                        StatusTab(
                            label = "In Transit",
                            type = BadgeType.IN_TRANSIT,
                            selected = selectedTab,
                            onSelect = { selectedTab = it }
                        )
                        StatusTab(
                            label = "Delivered",
                            type = BadgeType.DELIVERED,
                            selected = selectedTab,
                            onSelect = { selectedTab = it }
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
            val deliveries = Deliveries.filter {
                it.status == selectedTab && it.company.contains(search, ignoreCase = true)
            }
            items(deliveries) { delivery ->
                DeliveryCard(delivery = delivery)
            }
        }
    }
}

@Composable
private fun StatusTab(
    label: String,
    type: BadgeType,
    selected: BadgeType,
    onSelect: (BadgeType) -> Unit
) {
    Text(
        text = label,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = if (selected == type) ActiveGreen else UnselectedTabBlack,
        fontFamily = Inter,
        modifier = Modifier.clickable { onSelect(type) }
    )
}

@Composable
private fun DeliveryCard(delivery: com.example.inventix.ui.data.Delivery) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = delivery.id,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                modifier = Modifier.weight(1f),
                fontFamily = Inter
            )
            StatusBadge(type = delivery.status)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = delivery.company,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkValue,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Shipped: ${delivery.shippedDate}",
                fontSize = 12.sp,
                color = MutedText,
                modifier = Modifier.weight(1f),
                fontFamily = Inter
            )
            Text(
                text = "Est. Arrival ${delivery.etaDate}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MutedText,
                fontFamily = Inter
            )
        }
    }
}

@Composable
private fun NoDeliveriesYetState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.LocalShipping,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No items yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkValue,
            fontFamily = Inter
        )
    }
}
