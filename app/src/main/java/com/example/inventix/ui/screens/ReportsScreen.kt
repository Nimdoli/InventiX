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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.inventix.ui.theme.AccentYellow
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.DarkHeading
import com.example.inventix.ui.theme.DarkValue
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.LineChartStroke
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText
import com.example.inventix.ui.theme.PageTitleOlive

@Composable
fun ReportsScreen(onOpenMenu: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        InventixTopBar(title = "Reports", showBack = false, onLeadingClick = onOpenMenu)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    KpiCard(
                        title = "SALES REPORT",
                        subtitle = "Report Generated",
                        count = "24",
                        modifier = Modifier.weight(1f)
                    )
                    KpiCard(
                        title = "INVENTORY REPORT",
                        subtitle = "Scheduled",
                        count = "3",
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
private fun KpiCard(title: String, subtitle: String, count: String, modifier: Modifier = Modifier) {
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
            text = subtitle,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = DarkValue,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = count,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PageTitleOlive,
            fontFamily = Inter
        )
    }
}

@Composable
private fun RevenueTrendsCard() {
    val shape = RoundedCornerShape(10.dp)
    val points = listOf(14f, 30f, 22f, 40f, 34f, 52f, 46f, 62f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Revenue Trends",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkHeading,
            fontFamily = Inter
        )
        Spacer(modifier = Modifier.height(12.dp))
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val gridLines = 4
            val gridColor = BorderBeige
            repeat(gridLines + 1) { i ->
                val y = size.height * i / gridLines
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }
            val max = points.max()
            val stepX = size.width / (points.size - 1)
            val coords = points.mapIndexed { index, value ->
                Offset(index * stepX, size.height - (value / max) * size.height * 0.85f - size.height * 0.05f)
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
                    colors = listOf(AccentYellow.copy(alpha = 0.55f), Color.Transparent)
                )
            )
            drawPath(
                path = linePath,
                color = LineChartStroke,
                style = Stroke(width = 2.5.dp.toPx())
            )
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
        ReportRow(name = "Sales Report May", date = "Generated May 31, 2024")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderBeige)
        )
        ReportRow(name = "Q1 Inventory Audit", date = "Generated Apr 02, 2024")
    }
}

@Composable
private fun ReportRow(name: String, date: String) {
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
            Text(text = date, fontSize = 12.sp, color = MutedText, fontFamily = Inter)
        }
        Icon(
            imageVector = Icons.Outlined.Download,
            contentDescription = "Download",
            tint = MaroonPrimary
        )
    }
}
