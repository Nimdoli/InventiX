package com.example.inventix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
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
import com.example.inventix.ui.theme.DangerRed
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.EmailBlue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.LogoutBg
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText

@Composable
fun MenuScreen(
    onBack: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        InventixTopBar(title = "Menu", showBack = true, onLeadingClick = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            ProfileCard()
            Spacer(modifier = Modifier.height(16.dp))
            MenuItemsCard(onOpenProfile = onOpenProfile)
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(LogoutBg)
                    .clickable(onClick = onLogout),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Log out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DangerRed,
                    fontFamily = Inter
                )
            }
        }
    }
}

@Composable
private fun ProfileCard() {
    val shape = RoundedCornerShape(10.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CreamSurface)
            .border(1.dp, BorderBeige, shape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaroonPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "WK",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Inter
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Wasantha Kade",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                fontFamily = Inter
            )
            Text(text = "Mahinda(Admin)", fontSize = 12.sp, color = MutedText, fontFamily = Inter)
            Text(text = "mahindarajapaksha@inventix.lk", fontSize = 12.sp, color = EmailBlue, fontFamily = Inter)
        }
    }
}

@Composable
private fun MenuItemsCard(onOpenProfile: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        MenuRow(icon = Icons.Outlined.Person, label = "My Profile", onClick = onOpenProfile)
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        MenuRow(icon = Icons.Outlined.Lock, label = "Change Password")
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        MenuRow(icon = Icons.Outlined.NotificationsNone, label = "Notifications")
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        MenuRow(icon = Icons.Outlined.Language, label = "Language", trailing = "English")
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        MenuRow(icon = Icons.Outlined.HelpOutline, label = "Help & Support")
    }
}

@Composable
private fun MenuRow(
    icon: ImageVector,
    label: String,
    trailing: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaroonPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = DarkValue,
            modifier = Modifier.weight(1f),
            fontFamily = Inter
        )
        trailing?.let {
            Text(text = it, fontSize = 13.sp, color = MutedText, fontFamily = Inter)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(18.dp)
        )
    }
}
