package com.quran.memorization.data

import android.content.Context
import org.json.JSONObject

class QuranRepository(private val context: Context) {

    private var surahs: List<Surah> = emptyList()
    private var ayahs: List<Ayah> = emptyList()

    init {
        loadQuran()
    }

    private fun loadQuran() {
        try {
            val json = context.assets
                .open("quran.json")
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(json)
            val surahsArray = root.getJSONArray("surahs")

            val surahList = mutableListOf<Surah>()
            val ayahList = mutableListOf<Ayah>()

            for (i in 0 until surahsArray.length()) {
                val surahObject = surahsArray.getJSONObject(i)

                val surahNumber = surahObject.getInt("number")
                val surahName = surahObject.getString("name")
                val englishName =
                    if (surahObject.has("englishName"))
                        surahObject.getString("englishName")
                    else
                        ""

                val versesArray = surahObject.getJSONArray("ayahs")

                surahList.add(
                    Surah(
                        number = surahNumber,
                        name = surahName,
                        englishName = englishName,
                        versesCount = versesArray.length()
                    )
                )

                for (j in 0 until versesArray.length()) {
                    val ayahObject = versesArray.getJSONObject(j)

                    ayahList.add(
                        Ayah(
                            id = ayahObject.optInt(
                                "id",
                                ayahList.size + 1
                            ),
                            surahNumber = surahNumber,
                            ayahNumber = ayahObject.getInt("number"),
                            text = ayahObject.getString("text"),
                            page = ayahObject.optInt("page", 0),
                            juz = ayahObject.optInt("juz", 0)
                        )
                    )
                }
            }

            surahs = surahList
            ayahs = ayahList

        } catch (e: Exception) {
            surahs = emptyList()
            ayahs = emptyList()
        }
    }

    fun getSurahs(): List<Surah> {
        return surahs
    }

    fun getSurah(number: Int): Surah? {
        return surahs.firstOrNull {
            it.number == number
        }
    }

    fun getAyahs(surahNumber: Int): List<Ayah> {
        return ayahs.filter {
            it.surahNumber == surahNumber
        }
    }

    fun getAyah(
        surahNumber: Int,
        ayahNumber: Int
    ): Ayah? {
        return ayahs.firstOrNull {
            it.surahNumber == surahNumber &&
            it.ayahNumber == ayahNumber
        }
    }

    fun search(query: String): List<Ayah> {
        if (query.isBlank()) return emptyList()

        val normalizedQuery = query.trim()

        return ayahs.filter {
            it.text.contains(
                normalizedQuery,
                ignoreCase = false
            )
        }
    }

    fun getTotalSurahs(): Int {
        return surahs.size
    }

    fun getTotalAyahs(): Int {
        return ayahs.size
    }
}
