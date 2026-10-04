package com.quran.memorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuranApp()
        }
    }
}

@Composable
fun QuranApp() {

    var darkMode by remember { mutableStateOf(false) }

    val lightColors = lightColorScheme(
        primary = Color(0xFF287A5B),
        secondary = Color(0xFF4F8A6E),
        background = Color(0xFFF6F8F6),
        surface = Color.White
    )

    val darkColors = darkColorScheme(
        primary = Color(0xFF65C995),
        secondary = Color(0xFF8BC7A7),
        background = Color(0xFF101512),
        surface = Color(0xFF18201B)
    )

    MaterialTheme(
        colorScheme = if (darkMode) darkColors else lightColors
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            HomeScreen(
                darkMode = darkMode,
                onDarkModeChange = { darkMode = it }
            )
        }
    }
}

data class Feature(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun HomeScreen(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    var selectedTab by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    val features = listOf(
        Feature("الحفظ", Icons.Default.MenuBook),
        Feature("المراجعة", Icons.Default.Refresh),
        Feature("التكرار", Icons.Default.Replay),
        Feature("اختبرني", Icons.Default.Quiz),
        Feature("التسميع", Icons.Default.Mic),
        Feature("تقدمي", Icons.Default.ShowChart)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("الرئيسية") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.MenuBook, null) },
                    label = { Text("القرآن") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.BarChart, null) },
                    label = { Text("التقدم") }
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("حسابي") }
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "رفيق القرآن",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "تحفيظ • مراجعة • تسميع • متابعة",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = {
                        onDarkModeChange(!darkMode)
                    }
                ) {
                    Icon(
                        imageVector =
                            if (darkMode)
                                Icons.Default.LightMode
                            else
                                Icons.Default.DarkMode,
                        contentDescription = "الوضع الليلي"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "حفظ اليوم",
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "سورة الملك",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "الآيات 1 – 5",
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { 0.65f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.25f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "65% من خطة اليوم",
                        color = Color.White,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            message = "فتح جلسة الحفظ"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.PlayArrow, null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ابدأ جلسة الحفظ")
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "أدواتك اليومية",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(features) { feature ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp)
                            .clickable {
                                message = "تم اختيار: ${feature.title}"
                            },
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = feature.icon,
                                contentDescription = feature.title,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = feature.title,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            if (message.isNotEmpty()) {

                Snackbar(
                    modifier = Modifier.padding(bottom = 8.dp),
                    action = {
                        TextButton(
                            onClick = { message = "" }
                        ) {
                            Text("إغلاق")
                        }
                    }
                ) {
                    Text(message)
                }
            }
        }
    }
}
