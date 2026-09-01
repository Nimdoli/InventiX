package com.example.inventix.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.KeyboardArrowDown
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.ui.components.InventixTopBar
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.DarkHeading
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.PendingText
import com.example.inventix.ui.theme.UnselectedTabBlack

@Composable
fun ReportsScreen(hasReports: Boolean, onOpenMenu: () -> Unit) {
    var salesTabSelected by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        InventixTopBar(title = "Reports", showBack = false, onLeadingClick = onOpenMenu)
        if (!hasReports) {
            NoReportsYetState()
            return@Column
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { DateRangePill() }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                    Text(
                        text = "Sales Reports",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (salesTabSelected) ActiveGreen else UnselectedTabBlack,
                        fontFamily = Inter,
                        modifier = Modifier.clickable { salesTabSelected = true }
                    )
                    Text(
                        text = "Inventory Reports",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!salesTabSelected) ActiveGreen else UnselectedTabBlack,
                        fontFamily = Inter,
                        modifier = Modifier.clickable { salesTabSelected = false }
                    )
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    KpiCard(
                        title = "REPORTS GENERATED",
                        count = "24",
                        trendText = "↑ 6 this month",
                        trendColor = ActiveGreen,
                        modifier = Modifier.weight(1f)
                    )
                    KpiCard(
                        title = "SCHEDULED",
                        count = "3",
                        trendText = "Next: Monday 10AM",
                        trendColor = PendingText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item { RevenueTrendsCard() }
            item { RecentReportsCard() }
        }
    }
}

@Composable
private fun NoReportsYetState() {
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
                imageVector = Icons.Outlined.Description,
                contentDescription = null,
                tint = MutedText,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No reports yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkHeading,
            fontFamily = Inter
        )
    }
}

@Composable
private fun DateRangePill() {
    val shape = RoundedCornerShape(50)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = null,
            tint = MaroonPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "01 May 2026 - 01 June 2026",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = DarkValue,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = null,
            tint = MaroonPrimary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun KpiCard(
    title: String,
    count: String,
    trendText: String,
    trendColor: Color,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = modifier
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderBeige, shape)
            .padding(14.dp)
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MutedText,
            letterSpacing = 0.8.sp,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = count,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkValue,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = trendText,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = trendColor,
            fontFamily = Inter
        )
    }
}

@Composable
private fun RevenueTrendsCard() {
    val shape = RoundedCornerShape(10.dp)
    val points = listOf(35f, 60f, 47f, 45f, 35f, 48f, 78f, 60f, 68f, 87f, 80f, 90f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sales Overview",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkHeading,
                fontFamily = Inter
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .border(1.dp, BorderBeige, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "This month", fontSize = 12.sp, color = DarkValue, fontFamily = Inter)
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MutedText,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.height(160.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "100K", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
                Text(text = "50K", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
                Text(text = "0", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                val gridLines = 2
                repeat(gridLines + 1) { i ->
                    val y = size.height * i / gridLines
                    drawLine(
                        color = BorderBeige,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                val max = points.max()
                val stepX = size.width / (points.size - 1)
                val coords = points.mapIndexed { index, value ->
                    Offset(index * stepX, size.height - (value / max) * size.height * 0.9f)
                }
                val linePath = Path().apply {
                    moveTo(coords.first().x, coords.first().y)
                    coords.drop(1).forEach { lineTo(it.x, it.y) }
                }
                val fillPath = Path().apply {
                    addPath(linePath)
                    lineTo(coords.last().x, size.height)
                    lineTo(coords.first().x, size.height)
                    close()
                }
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(ActiveGreen.copy(alpha = 0.25f), Color.Transparent)
                    )
                )
                drawPath(
                    path = linePath,
                    color = ActiveGreen,
                    style = Stroke(width = 2.5.dp.toPx())
                )
                coords.forEach { point ->
                    drawCircle(color = ActiveGreen, radius = 3.dp.toPx(), center = point)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 34.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "1 May", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
            Text(text = "10 May", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
            Text(text = "20 May", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
            Text(text = "31 May", fontSize = 10.sp, color = MutedText, fontFamily = Inter)
        }
    }
}

@Composable
private fun RecentReportsCard() {
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
        Text(
            text = "Recent Reports",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkHeading,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(12.dp))
        ReportRow(name = "Sales_reports_May2026.pdf", date = "Generated 28 May", size = "1.2 MB")
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderBeige))
        ReportRow(name = "Stock_valuation_Q2.xlsx", date = "Generated 25 May", size = "680 KB")
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderBeige))
        ReportRow(name = "Purchase_summary_Apr.pdf", date = "Generated 02 May", size = "980 KB")
    }
}

@Composable
private fun ReportRow(name: String, date: String, size: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkValue,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "$date  •  $size", fontSize = 12.sp, color = MutedText, fontFamily = Inter)
        }
        Icon(
            imageVector = Icons.Outlined.Download,
            contentDescription = "Download",
            tint = MaroonPrimary
        )
    }
}
