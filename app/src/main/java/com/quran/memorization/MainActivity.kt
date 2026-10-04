package com.quran.memorization

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quran.memorization.data.Ayah
import com.quran.memorization.data.QuranRepository
import com.quran.memorization.data.Surah

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuranApp(this)
        }
    }
}

private enum class Screen {
    HOME,
    SURAHS,
    AYAHS,
    SEARCH,
    BOOKMARKS
}

@Composable
fun QuranApp(context: Context) {

    var screen by remember {
        mutableStateOf(Screen.HOME)
    }

    var selectedSurah by remember {
        mutableIntStateOf(1)
    }

    val repository = remember {
        QuranRepository(context)
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            androidx.compose.runtime.CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {

                when (screen) {

                    Screen.HOME -> HomeScreen(
                        repository = repository,
                        onQuranClick = {
                            screen = Screen.SURAHS
                        },
                        onSearchClick = {
                            screen = Screen.SEARCH
                        },
                        onBookmarksClick = {
                            screen = Screen.BOOKMARKS
                        }
                    )

                    Screen.SURAHS -> SurahsScreen(
                        repository = repository,
                        onBack = {
                            screen = Screen.HOME
                        },
                        onSurahClick = { number ->
                            selectedSurah = number
                            screen = Screen.AYAHS
                        }
                    )

                    Screen.AYAHS -> AyahsScreen(
                        repository = repository,
                        surahNumber = selectedSurah,
                        context = context,
                        onBack = {
                            screen = Screen.SURAHS
                        }
                    )

                    Screen.SEARCH -> SearchScreen(
                        repository = repository,
                        onBack = {
                            screen = Screen.HOME
                        },
                        onAyahClick = { number ->
                            selectedSurah = number
                            screen = Screen.AYAHS
                        }
                    )

                    Screen.BOOKMARKS -> BookmarksScreen(
                        repository = repository,
                        context = context,
                        onBack = {
                            screen = Screen.HOME
                        },
                        onAyahClick = { number ->
                            selectedSurah = number
                            screen = Screen.AYAHS
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    repository: QuranRepository,
    onQuranClick: () -> Unit,
    onSearchClick: () -> Unit,
    onBookmarksClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {

                        Text(
                            text = "رفيق القرآن",
                            fontSize = 22.sp
                        )

                        Text(
                            text = "تحفيظ • مراجعة • تسميع",
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {

                Text(
                    text = "مرحبًا بك",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {

                Text(
                    text = "ابدأ رحلتك مع القرآن الكريم",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {

                FeatureCard(
                    title = "القرآن الكريم",
                    description = "114 سورة • جميع الآيات",
                    icon = Icons.Default.MenuBook,
                    onClick = onQuranClick
                )
            }

            item {

                FeatureCard(
                    title = "البحث في القرآن",
                    description = "ابحث عن آية أو كلمة",
                    icon = Icons.Default.Search,
                    onClick = onSearchClick
                )
            }

            item {

                FeatureCard(
                    title = "العلامات المحفوظة",
                    description = "الوصول السريع إلى الآيات المحفوظة",
                    icon = Icons.Default.Bookmark,
                    onClick = onBookmarksClick
                )
            }

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "بيانات القرآن",
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "السور: ${repository.getTotalSurahs()}",
                            fontSize = 15.sp
                        )

                        Text(
                            text =
                                "الآيات: ${repository.getTotalAyahs()}",
                            fontSize = 15.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "المصدر: Tanzil Project",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 19.sp
                )

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurahsScreen(
    repository: QuranRepository,
    onBack: () -> Unit,
    onSurahClick: (Int) -> Unit
) {

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("سور القرآن الكريم")
                },
                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "رجوع"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            items(
                repository.getSurahs()
            ) { surah ->

                SurahRow(
                    surah = surah,
                    onClick = {
                        onSurahClick(surah.number)
                    }
                )
            }
        }
    }
}

@Composable
private fun SurahRow(
    surah: Surah,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = surah.number.toString(),
            fontSize = 15.sp,
            modifier = Modifier.width(35.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = surah.name,
                fontSize = 20.sp
            )

            Text(
                text = "${surah.versesCount} آية",
                fontSize = 12.sp,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AyahsScreen(
    repository: QuranRepository,
    surahNumber: Int,
    context: Context,
    onBack: () -> Unit
) {

    val surah = repository.getSurah(surahNumber)

    val ayahs = repository
        .getAyahs(surahNumber)
        .filter {
            it.ayahNumber > 0
        }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        surah?.name ?: "السورة"
                    )
                },
                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "رجوع"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            item {

                Text(
                    text = surah?.name ?: "",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            items(
                items = ayahs,
                key = {
                    "${surahNumber}:${it.ayahNumber}"
                }
            ) { ayah ->

                AyahCard(
                    ayah = ayah,
                    context = context
                )
            }
        }
    }
}

@Composable
private fun AyahCard(
    ayah: Ayah,
    context: Context
) {

    val preferences = remember {

        context.getSharedPreferences(
            "quran_preferences",
            Context.MODE_PRIVATE
        )
    }

    val key =
        "${ayah.surahNumber}:${ayah.ayahNumber}"

    var bookmarked by remember {

        mutableStateOf(
            preferences.getBoolean(
                "bookmark_$key",
                false
            )
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = ayah.ayahNumber.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {

                        bookmarked = !bookmarked

                        preferences.edit()
                            .putBoolean(
                                "bookmark_$key",
                                bookmarked
                            )
                            .apply()
                    }
                ) {

                    Icon(
                        imageVector =
                            if (bookmarked)
                                Icons.Default.Bookmark
                            else
                                Icons.Default.BookmarkBorder,
                        contentDescription = "علامة"
                    )
                }
            }

            Text(
                text = ayah.text,
                fontSize = 25.sp,
                lineHeight = 46.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    repository: QuranRepository,
    onBack: () -> Unit,
    onAyahClick: (Int) -> Unit
) {

    var query by remember {
        mutableStateOf("")
    }

    val results = remember(query) {

        if (query.length >= 2) {

            repository
                .search(query)
                .filter {
                    it.ayahNumber > 0
                }
                .take(100)

        } else {

            emptyList()
        }
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("البحث في القرآن")
                },
                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "رجوع"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("اكتب كلمة أو جزءًا من آية")
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(results) { ayah ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAyahClick(
                                    ayah.surahNumber
                                )
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text =
                                    "سورة ${
                                        repository
                                            .getSurah(
                                                ayah.surahNumber
                                            )
                                            ?.name
                                            ?: ""
                                    } — آية ${ayah.ayahNumber}",
                                fontSize = 14.sp
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = ayah.text,
                                fontSize = 19.sp,
                                lineHeight = 32.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookmarksScreen(
    repository: QuranRepository,
    context: Context,
    onBack: () -> Unit,
    onAyahClick: (Int) -> Unit
) {

    val preferences = remember {

        context.getSharedPreferences(
            "quran_preferences",
            Context.MODE_PRIVATE
        )
    }

    val allAyahs = repository
        .getAyahsForAllSurahs()
        .filter { ayah ->

            ayah.ayahNumber > 0 &&
                preferences.getBoolean(
                    "bookmark_${ayah.surahNumber}:${ayah.ayahNumber}",
                    false
                )
        }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("العلامات المحفوظة")
                },
                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "رجوع"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            items(allAyahs) { ayah ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAyahClick(
                                ayah.surahNumber
                            )
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Text(
                            text =
                                "سورة ${
                                    repository
                                        .getSurah(
                                            ayah.surahNumber
                                        )
                                        ?.name
                                        ?: ""
                                } — آية ${ayah.ayahNumber}"
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = ayah.text,
                            fontSize = 19.sp,
                            lineHeight = 32.sp
                        )
                    }
                }
            }
        }
    }
}
