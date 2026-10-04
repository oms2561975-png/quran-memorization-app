package com.quran.memorization.data

class QuranRepository {

    private val surahs = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", 7),
        Surah(2, "البقرة", "Al-Baqarah", 286),
        Surah(3, "آل عمران", "Aal-E-Imran", 200),
        Surah(4, "النساء", "An-Nisa", 176),
        Surah(5, "المائدة", "Al-Ma'idah", 120)
    )

    fun getSurahs(): List<Surah> {
        return surahs
    }

    fun getSurah(number: Int): Surah? {
        return surahs.firstOrNull { it.number == number }
    }

    fun getAyahs(surahNumber: Int): List<Ayah> {
        return emptyList()
    }

    fun search(query: String): List<Ayah> {
        return emptyList()
    }
}
