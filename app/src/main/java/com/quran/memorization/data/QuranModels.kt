package com.quran.memorization.data

data class Surah(
    val number: Int,
    val name: String,
    val englishName: String = "",
    val versesCount: Int = 0
)

data class Ayah(
    val id: Int,
    val surahNumber: Int,
    val ayahNumber: Int,
    val text: String,
    val page: Int = 0,
    val juz: Int = 0
)

data class Bookmark(
    val id: Long = 0,
    val surahNumber: Int,
    val ayahNumber: Int,
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class ReadingPosition(
    val surahNumber: Int,
    val ayahNumber: Int,
    val updatedAt: Long = System.currentTimeMillis()
)
