package com.example.inventix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.data.BadgeType
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.theme.AccentYellow
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.AmberLowStockText
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.ButtonTextAmber
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.GoldTintBg
import com.example.inventix.ui.theme.GreenInStockBg
import com.example.inventix.ui.theme.GreenInStockText
import com.example.inventix.ui.theme.InactiveBg
import com.example.inventix.ui.theme.InactiveGray
import com.example.inventix.ui.theme.InTransitBlueBg
import com.example.inventix.ui.theme.InTransitBlueText
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.PendingText
import com.example.inventix.ui.theme.RedBadgeBg
import com.example.inventix.ui.theme.RedBadgeText
import com.example.inventix.ui.theme.TopBarTitleBrown
import com.example.inventix.ui.theme.TopBarYellow

@Composable
fun InventixTopBar(
    title: String,
    showBack: Boolean,
    onLeadingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp)
            .background(TopBarYellow)
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp)
        ) {
            IconButton(
                onClick = onLeadingClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = if (showBack) Icons.AutoMirrored.Outlined.ArrowBack else Icons.Outlined.Menu,
                    contentDescription = null,
                    tint = TopBarTitleBrown
                )
            }
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TopBarTitleBrown,
                fontFamily = Inter,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

data class BottomTab(
    val label: String,
    val icon: ImageVector,
    val targetRoute: String,
    val activeRoutes: Set<String>
)

@Composable
fun InventixBottomBar(
    role: UserRole,
    currentRoute: String?,
    onSelect: (String) -> Unit
) {
    val customerTabs = listOf(
        BottomTab("Stocks", Icons.Outlined.Inventory2, "products", setOf("products")),
        BottomTab("Reports", Icons.Outlined.BarChart, "reports", setOf("reports")),
        BottomTab("Delivery", Icons.Outlined.LocalShipping, "delivery", setOf("delivery")),
        BottomTab("Suppliers", Icons.Outlined.Group, "suppliers", setOf("suppliers"))
    )
    val supplierTabs = listOf(
        BottomTab("Stocks", Icons.Outlined.Inventory2, "products", setOf("products")),
        BottomTab("Orders", Icons.AutoMirrored.Outlined.ReceiptLong, "orders", setOf("orders"))
    )
    val tabs = if (role == UserRole.CUSTOMER) customerTabs else supplierTabs

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(CreamSurface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderBeige)
        )
        Row(modifier = Modifier.fillMaxWidth().height(64.dp)) {
            tabs.forEach { tab ->
                val active = currentRoute != null && currentRoute in tab.activeRoutes
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(tab.targetRoute) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (active) AccentYellow else Color.Transparent)
                            .padding(horizontal = 16.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (active) TopBarTitleBrown else MutedText,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (active) TopBarTitleBrown else MutedText,
                        maxLines = 1,
                        fontFamily = Inter
                    )
                }
            }
        }
    }
}

@Composable
fun InventixSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showFilterIcon: Boolean = false,
    modifier: Modifier = Modifier
) {
    val pill = RoundedCornerShape(20.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(pill)
            .background(Color.White)
            .border(1.dp, BorderBeige, pill)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(text = placeholder, fontSize = 13.sp, color = MutedText, fontFamily = Inter)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = DarkValue,
                    fontFamily = Inter
                ),
                cursorBrush = SolidColor(MaroonPrimary),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (showFilterIcon) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Filter",
                tint = MutedText,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

fun badgeLabel(type: BadgeType): String = when (type) {
    BadgeType.IN_STOCK -> "In stock"
    BadgeType.LOW_STOCK -> "Low stock"
    BadgeType.OUT_OF_STOCK -> "Out of stock"
    BadgeType.ACTIVE -> "Active"
    BadgeType.INACTIVE -> "Inactive"
    BadgeType.IN_TRANSIT -> "In Transit"
    BadgeType.PENDING -> "Pending"
    BadgeType.DELIVERED -> "Delivered"
}

fun badgeColors(type: BadgeType): Pair<Color, Color> = when (type) {
    BadgeType.IN_STOCK -> GreenInStockBg to GreenInStockText
    BadgeType.LOW_STOCK -> GoldTintBg to AmberLowStockText
    BadgeType.OUT_OF_STOCK -> RedBadgeBg to RedBadgeText
    BadgeType.ACTIVE -> GreenInStockBg to ActiveGreen
    BadgeType.INACTIVE -> InactiveBg to InactiveGray
    BadgeType.IN_TRANSIT -> InTransitBlueBg to InTransitBlueText
    BadgeType.PENDING -> GoldTintBg to PendingText
    BadgeType.DELIVERED -> GreenInStockBg to GreenInStockText
}

@Composable
fun StatusBadge(type: BadgeType, modifier: Modifier = Modifier) {
    val colors = badgeColors(type)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colors.first)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = badgeLabel(type),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colors.second,
            fontFamily = Inter
        )
    }
}

@Composable
fun ProductCard(
    name: String,
    category: String,
    price: String,
    stock: Int,
    status: BadgeType,
    onClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkValue,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = category, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
            }
            Spacer(modifier = Modifier.width(8.dp))
            StatusBadge(type = status)
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = BorderBeige, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = price,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkValue,
                modifier = Modifier.weight(1f),
                fontFamily = Inter
            )
            Text(
                text = "Stock: $stock",
                fontSize = 13.sp,
                color = MutedText,
                fontFamily = Inter
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(AccentYellow)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = ButtonTextAmber,
            fontFamily = Inter
        )
    }
}

@Composable
fun PillInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    val pill = RoundedCornerShape(20.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(pill)
            .background(Color.White)
            .border(1.dp, BorderBeige, pill)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(text = label, fontSize = 13.sp, color = MutedText, fontFamily = Inter)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    color = DarkValue,
                    fontFamily = Inter
                ),
                visualTransformation = if (isPassword && !visible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                cursorBrush = SolidColor(MaroonPrimary),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (isPassword) {
            IconButton(onClick = { visible = !visible }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = null,
                    tint = MutedText,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
