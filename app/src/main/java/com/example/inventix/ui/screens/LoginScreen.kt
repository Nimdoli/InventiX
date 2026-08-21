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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventix.R
import com.example.inventix.ui.components.PillInputField
import com.example.inventix.ui.components.PrimaryButton
import com.example.inventix.ui.theme.ActiveGreen
import com.example.inventix.ui.theme.BorderBeige
import com.example.inventix.ui.theme.CreamSurface
import com.example.inventix.ui.theme.GradientBottomYellow
import com.example.inventix.ui.theme.GradientTopWhite
import com.example.inventix.ui.theme.GoogleBorder
import com.example.inventix.ui.theme.Inter
import com.example.inventix.ui.theme.MaroonPrimary
import com.example.inventix.ui.theme.MutedText

@Composable
fun LoginScreen(onLogin: () -> Unit, onSignUp: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GradientTopWhite, GradientBottomYellow))
            )
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(CreamSurface)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.logo_inventix),
                    contentDescription = "InventiX logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "InventiX",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaroonPrimary,
                    textAlign = TextAlign.Center,
                    fontFamily = Inter
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Secure Access for Inventory Management",
                    fontSize = 14.sp,
                    color = MutedText,
                    textAlign = TextAlign.Center,
                    fontFamily = Inter
                )
            }
            PillInputField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                leadingIcon = Icons.Outlined.Person
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PillInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true
                )
                Text(
                    text = "Forgot password?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    textAlign = TextAlign.Right,
                    fontFamily = Inter
                )
            }
            PrimaryButton(text = "Login", onClick = onLogin, modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(BorderBeige))
                Text(
                    text = "OR CONTINUE WITH",
                    fontSize = 12.sp,
                    color = MutedText,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontFamily = Inter
                )
                Box(modifier = Modifier.weight(1f).height(1.dp).background(BorderBeige))
            }
            GoogleButton()
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 14.sp,
                    color = MutedText,
                    fontFamily = Inter
                )
                Text(
                    text = "Signup",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ActiveGreen,
                    fontFamily = Inter,
                    modifier = Modifier.clickable(onClick = onSignUp)
                )
            }
        }
    }
}

@Composable
private fun GoogleButton() {
    val shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, GoogleBorder, shape)
            .clickable { },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = "Google",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Sign in with Google",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = MaroonPrimary,
            fontFamily = Inter
        )
    }
}
