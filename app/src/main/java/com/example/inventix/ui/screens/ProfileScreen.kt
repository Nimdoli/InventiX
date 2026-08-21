package com.example.inventix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.components.InventixTopBar
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        InventixTopBar(title = "My Profile", showBack = true, onLeadingClick = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(MaroonPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WK",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = Inter
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Wasantha Kade",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Mahinda(Admin)",
                fontSize = 13.sp,
                color = MutedText,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(24.dp))

            val shape = RoundedCornerShape(10.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(Color.White)
                    .border(1.dp, BorderBeige, shape)
            ) {
                ProfileField(
                    icon = Icons.Outlined.Person,
                    label = "Full Name",
                    value = "Wasantha Kade"
                )
                ProfileField(
                    icon = Icons.Outlined.Email,
                    label = "Email",
                    value = "mahindarajapaksha@inventix.lk"
                )
                ProfileField(
                    icon = Icons.Outlined.Phone,
                    label = "Contact Number",
                    value = "077 123 4567"
                )
                ProfileField(
                    icon = Icons.Outlined.Business,
                    label = "Store Name",
                    value = "Tech Solutions Ltd.",
                    showDivider = false
                )
            }
        }
    }
}

@Composable
private fun ProfileField(
    icon: ImageVector,
    label: String,
    value: String,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaroonPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkValue,
                    fontFamily = Inter
                )
            }
        }
        if (showDivider) {
            androidx.compose.material3.HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        }
    }
}
