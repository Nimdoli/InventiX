package com.example.inventix.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.R
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.GradientBottomYellow
import com.example.inventix.ui.theme.GradientTopWhite
import com.example.inventix.ui.theme.IconBoxCream
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.LabelText
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText

@Composable
fun ChooseRoleScreen(onRolePicked: (UserRole) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GradientTopWhite, GradientBottomYellow))
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_inventix),
                contentDescription = "InventiX logo",
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome to InventiX",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaroonPrimary,
                textAlign = TextAlign.Center,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Please choose how you want to continue",
                fontSize = 14.sp,
                color = LabelText,
                textAlign = TextAlign.Center,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(24.dp))
            RoleCard(
                icon = Icons.Outlined.Person,
                title = "Customer",
                subtitle = "Access your orders and tracking",
                onClick = { onRolePicked(UserRole.CUSTOMER) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoleCard(
                icon = Icons.Outlined.Business,
                title = "Supplier",
                subtitle = "Manage shipments and inventory",
                onClick = { onRolePicked(UserRole.SUPPLIER) }
            )
        }
    }
}

@Composable
private fun RoleCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(IconBoxCream),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaroonPrimary,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaroonPrimary,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaroonPrimary
        )
    }
}
