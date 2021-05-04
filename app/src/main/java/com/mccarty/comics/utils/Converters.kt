package com.mccarty.comics.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mccarty.comics.models.*

class Converters {

    @TypeConverter
    fun fromThumbnailObject(thumbnail: Thumbnail): String = Gson().toJson(thumbnail)

    @TypeConverter
    fun toThumbnailObject(thumbnail: String): Thumbnail = Gson().fromJson(thumbnail, Thumbnail::class.java)

    @TypeConverter
    fun fromSeriesObject(series: Series): String = Gson().toJson(series)

    @TypeConverter
    fun toSeriesObject(series: String): Series = Gson().fromJson(series, Series::class.java)

    @TypeConverter
    fun fromStoriesObject(stories: Stories): String = Gson().toJson(stories)

    @TypeConverter
    fun toStoriesObject(stories: String): Stories = Gson().fromJson(stories, Stories::class.java)

    @TypeConverter
    fun fromComicsObject(comics: Comics): String = Gson().toJson(comics)

    @TypeConverter
    fun toComicsObject(comics: String): Comics = Gson().fromJson(comics, Comics::class.java)

    @TypeConverter
    fun fromEventsObject(events: Events): String = Gson().toJson(events)

    @TypeConverter
    fun toEventsObject(events: String): Events = Gson().fromJson(events, Events::class.java)

    @TypeConverter
    fun fromUrlsArrayList(urls: ArrayList<Url>): String = Gson().toJson(urls)

    @TypeConverter
    fun toUrlsArrayList(urls: String): ArrayList<Url> {
        val itemType = object : TypeToken<ArrayList<Url>>() {}.type
        return Gson().fromJson(urls, itemType)
    }
}