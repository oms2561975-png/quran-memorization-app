package com.quran.memorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuranApp()
        }
    }
}

enum class AppScreen {
    HOME,
    MEMORIZATION,
    REVISION,
    REPETITION,
    TEST,
    RECITATION,
    PROGRESS,
    ACCOUNT
}

data class Feature(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val screen: AppScreen
)

@Composable
fun QuranApp() {

    var darkMode by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

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

            AppNavigation(
                currentScreen = currentScreen,
                onScreenChange = { currentScreen = it },
                darkMode = darkMode,
                onDarkModeChange = { darkMode = it }
            )
        }
    }
}

@Composable
fun AppNavigation(
    currentScreen: AppScreen,
    onScreenChange: (AppScreen) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    Scaffold(
        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = currentScreen == AppScreen.HOME,
                    onClick = { onScreenChange(AppScreen.HOME) },
                    icon = {
                        Icon(Icons.Default.Home, null)
                    },
                    label = {
                        Text("الرئيسية")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == AppScreen.MEMORIZATION,
                    onClick = { onScreenChange(AppScreen.MEMORIZATION) },
                    icon = {
                        Icon(Icons.Default.MenuBook, null)
                    },
                    label = {
                        Text("القرآن")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == AppScreen.PROGRESS,
                    onClick = { onScreenChange(AppScreen.PROGRESS) },
                    icon = {
                        Icon(Icons.Default.BarChart, null)
                    },
                    label = {
                        Text("التقدم")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == AppScreen.ACCOUNT,
                    onClick = { onScreenChange(AppScreen.ACCOUNT) },
                    icon = {
                        Icon(Icons.Default.Person, null)
                    },
                    label = {
                        Text("حسابي")
                    }
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (currentScreen) {

                AppScreen.HOME -> {
                    HomeScreen(
                        onScreenChange = onScreenChange,
                        darkMode = darkMode,
                        onDarkModeChange = onDarkModeChange
                    )
                }

                AppScreen.MEMORIZATION -> {
                    MemorizationScreen(
                        onBack = {
                            onScreenChange(AppScreen.HOME)
                        }
                    )
                }

                AppScreen.REVISION -> {
                    RevisionScreen(
                        onBack = {
                            onScreenChange(AppScreen.HOME)
                        }
                    )
                }

                AppScreen.REPETITION -> {
                    RepetitionScreen(
                        onBack = {
                            onScreenChange(AppScreen.HOME)
                        }
                    )
                }

                AppScreen.TEST -> {
                    TestScreen(
                        onBack = {
                            onScreenChange(AppScreen.HOME)
                        }
                    )
                }

                AppScreen.RECITATION -> {
                    RecitationScreen(
                        onBack = {
                            onScreenChange(AppScreen.HOME)
                        }
                    )
                }

                AppScreen.PROGRESS -> {
                    ProgressScreen()
                }

                AppScreen.ACCOUNT -> {
                    AccountScreen(
                        darkMode = darkMode,
                        onDarkModeChange = onDarkModeChange
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onScreenChange: (AppScreen) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    val features = listOf(

        Feature(
            "الحفظ",
            "حفظ جديد ومتابعة الآيات",
            Icons.Default.MenuBook,
            AppScreen.MEMORIZATION
        ),

        Feature(
            "المراجعة",
            "راجع محفوظك بذكاء",
            Icons.Default.Refresh,
            AppScreen.REVISION
        ),

        Feature(
            "التكرار",
            "كرر الآيات حتى الإتقان",
            Icons.Default.Replay,
            AppScreen.REPETITION
        ),

        Feature(
            "اختبرني",
            "اختبر مستوى حفظك",
            Icons.Default.Quiz,
            AppScreen.TEST
        ),

        Feature(
            "التسميع",
            "سجل تسميعك وتابعه",
            Icons.Default.Mic,
            AppScreen.RECITATION
        ),

        Feature(
            "تقدمي",
            "تابع إنجازك وإحصاءاتك",
            Icons.Default.ShowChart,
            AppScreen.PROGRESS
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "رفيق القرآن",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "تحفيظ • مراجعة • تكرار • تسميع",
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

            Spacer(modifier = Modifier.height(18.dp))
        }

        item {

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
                        text = "خطة اليوم",
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

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
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            onScreenChange(AppScreen.MEMORIZATION)
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

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Text(
                text = "أدواتك اليومية",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        item {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(features) { feature ->

                    FeatureCard(
                        feature = feature,
                        onClick = {
                            onScreenChange(feature.screen)
                        }
                    )
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "هدفك اليوم",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "استمر بخطوة صغيرة كل يوم، فالثبات هو طريق الإتقان.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FeatureCard(
    feature: Feature,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = feature.icon,
                contentDescription = feature.title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = feature.title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = feature.subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String? = null,
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBack
        ) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "رجوع"
            )
        }

        Column {

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            if (subtitle != null) {

                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun MemorizationScreen(
    onBack: () -> Unit
) {

    var selectedSurah by remember {
        mutableStateOf("سورة الملك")
    }

    var repeatCount by remember {
        mutableStateOf(3)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        ScreenHeader(
            title = "الحفظ",
            subtitle = "جلسة حفظ جديدة",
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            item {

                SectionCard(
                    title = "السورة",
                    text = selectedSurah
                ) {

                    selectedSurah =
                        if (selectedSurah == "سورة الملك")
                            "سورة البقرة"
                        else
                            "سورة الملك"
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "الآيات",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "﴿ تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ ﴾",
                            fontSize = 21.sp,
                            lineHeight = 36.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "الآية 1",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "عدد التكرارات",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                onClick = {
                                    if (repeatCount > 1) repeatCount--
                                }
                            ) {
                                Icon(Icons.Default.Remove, null)
                            }

                            Text(
                                text = "$repeatCount مرات",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = {
                                    if (repeatCount < 20) repeatCount++
                                }
                            ) {
                                Icon(Icons.Default.Add, null)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            item {

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.PlayArrow, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("بدء جلسة الحفظ")
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.VolumeUp, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("استماع للتلاوة")
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    text: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}

@Composable
fun RevisionScreen(
    onBack: () -> Unit
) {

    SimpleFeatureScreen(
        title = "المراجعة",
        subtitle = "راجع محفوظك وحافظ على ثباته",
        icon = Icons.Default.Refresh,
        description = "ستتمكن هنا من تنظيم المراجعة اليومية حسب السورة ومستوى الإتقان.",
        buttonText = "ابدأ المراجعة",
        onBack = onBack
    )
}

@Composable
fun RepetitionScreen(
    onBack: () -> Unit
) {

    SimpleFeatureScreen(
        title = "التكرار",
        subtitle = "كرر الآيات حتى الإتقان",
        icon = Icons.Default.Replay,
        description = "حدد عدد مرات تكرار الآية أو مجموعة الآيات ثم ابدأ جلسة التكرار.",
        buttonText = "ابدأ التكرار",
        onBack = onBack
    )
}

@Composable
fun TestScreen(
    onBack: () -> Unit
) {

    SimpleFeatureScreen(
        title = "اختبرني",
        subtitle = "اختبر مستوى حفظك",
        icon = Icons.Default.Quiz,
        description = "اختبارات الحفظ ستساعدك على اكتشاف الآيات التي تحتاج إلى مراجعة.",
        buttonText = "ابدأ الاختبار",
        onBack = onBack
    )
}

@Composable
fun RecitationScreen(
    onBack: () -> Unit
) {

    SimpleFeatureScreen(
        title = "التسميع",
        subtitle = "سجل تسميعك وتابع مستواك",
        icon = Icons.Default.Mic,
        description = "ستكون هذه الشاشة مخصصة لتسجيل التسميع ومراجعة التسجيلات وتقييم الأداء.",
        buttonText = "ابدأ التسميع",
        onBack = onBack
    )
}

@Composable
fun SimpleFeatureScreen(
    title: String,
    subtitle: String,
    icon: ImageVector,
    description: String,
    buttonText: String,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        ScreenHeader(
            title = title,
            subtitle = subtitle,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = description,
                        fontSize = 15.sp,
                        lineHeight = 25.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(icon, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(buttonText)
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "تقدمي",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "تابع رحلتك مع القرآن",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            ProgressCard(
                title = "الحفظ",
                value = "65%",
                progress = 0.65f
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProgressCard(
                title = "المراجعة",
                value = "48%",
                progress = 0.48f
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProgressCard(
                title = "الإتقان",
                value = "72%",
                progress = 0.72f
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "إنجازاتك",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("📖 جلسات الحفظ: 12")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🔄 جلسات المراجعة: 8")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🎙️ جلسات التسميع: 6")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🔥 أيام متتالية: 7")
                }
            }
        }
    }
}

@Composable
fun ProgressCard(
    title: String,
    value: String,
    progress: Float
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Composable
fun AccountScreen(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "حسابي",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "إعدادات رفيق القرآن",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "المظهر",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text("الوضع الليلي")

                        Switch(
                            checked = darkMode,
                            onCheckedChange = onDarkModeChange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SettingsItem(
                icon = Icons.Default.Backup,
                title = "النسخ الاحتياطي",
                subtitle = "حفظ بياناتك واستعادتها"
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItem(
                icon = Icons.Default.Description,
                title = "التقارير",
                subtitle = "تقارير الحفظ والتقدم"
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingsItem(
                icon = Icons.Default.Info,
                title = "عن التطبيق",
                subtitle = "رفيق القرآن — الإصدار 1.0"
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
