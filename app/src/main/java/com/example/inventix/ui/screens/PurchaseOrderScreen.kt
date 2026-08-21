package com.example.inventix.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.components.InventixTopBar
import com.example.inventix.ui.theme.AddButtonBrown
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.PoCardGray
import com.example.inventix.ui.theme.SendGreen
import com.example.inventix.ui.theme.SuggestedNavy

@Composable
fun PurchaseOrderScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        InventixTopBar(title = "Purchase Order", showBack = true, onLeadingClick = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            val shape = RoundedCornerShape(12.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(PoCardGray)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Auto generated purchase order",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = PO_BODY,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 22.sp,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionButton(
                        text = "Edit",
                        bgColor = AddButtonBrown,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        text = "Send",
                        bgColor = SendGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SuggestedNavy)
                        .clickable { },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Suggested best supplier",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontFamily = Inter
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontFamily = Inter
        )
    }
}

private const val PO_BODY =
    "To: Apex Distributors\n" +
        "From: InventiX (Pvt) Ltd\n" +
        "\n" +
        "Date: 16 May 2026\n" +
        "\n" +
        "Item: Full Cream Milk Powder 400g\n" +
        "Quantity: 50 units\n" +
        "Unit Price: LKR 1,180.00\n" +
        "Total: LKR 59,000.00\n" +
        "\n" +
        "Delivery Address:\n" +
        "No. 24, Galle Road, Colombo 03, Sri Lanka\n" +
        "\n" +
        "Notes: Auto-generated reorder triggered by low stock alert (Stock: 12)."
