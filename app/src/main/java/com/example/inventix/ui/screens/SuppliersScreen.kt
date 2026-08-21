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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MailOutline
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
import com.example.inventix.ui.data.Suppliers
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.InactiveGray
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MutedText

@Composable
fun SuppliersScreen(onOpenMenu: () -> Unit) {
    var search by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Suppliers", showBack = false, onLeadingClick = onOpenMenu)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InventixSearchField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = "Search suppliers..."
                )
            }
            item {
                Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                    Text(
                        text = "Company",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MutedText,
                        modifier = Modifier.weight(1f),
                        fontFamily = Inter
                    )
                    Text(
                        text = "Product",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MutedText,
                        fontFamily = Inter
                    )
                }
            }
            val suppliers = Suppliers.filter {
                it.name.contains(search, ignoreCase = true)
            }
            items(suppliers) { supplier ->
                SupplierCard(supplier = supplier)
            }
        }
    }
}

@Composable
private fun SupplierCard(supplier: com.example.inventix.ui.data.Supplier) {
    val shape = RoundedCornerShape(10.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .clickable { }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(supplier.avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = supplier.initials,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Inter
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = supplier.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F110B),
                fontFamily = Inter
            )
            Text(text = supplier.address, fontSize = 11.sp, color = MutedText, fontFamily = Inter)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = null,
                    tint = MutedText,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = supplier.phone, fontSize = 11.sp, color = MutedText, fontFamily = Inter)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = null,
                    tint = MutedText,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = supplier.email, fontSize = 11.sp, color = MutedText, fontFamily = Inter)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (supplier.isActive) "Active" else "Inactive",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (supplier.isActive) ActiveGreen else InactiveGray,
            fontFamily = Inter
        )
    }
}
