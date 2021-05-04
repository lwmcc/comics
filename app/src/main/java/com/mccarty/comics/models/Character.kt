package com.mccarty.comics.models

import androidx.room.*

@Entity(tableName = "result")
data class Result(
    @ColumnInfo(name = "comics") val comics: Comics,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "events") val events: Events,
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "modified") val modified: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "resourceURI") val resourceURI: String,
    @ColumnInfo(name = "series") val series: Series?,
    @ColumnInfo(name = "stories") val stories: Stories?,
    @ColumnInfo(name = "thumbnail") val thumbnail: Thumbnail,
    @ColumnInfo(name = "urls") val urls: ArrayList<Url>
)

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)

data class Thumbnail(
     val extension: String,
     val path: String
)

data class Url(
    val type: String,
    val url: String
)

data class Item(
    val name: String,
    val resourceURI: String
)

data class ItemX(
    val name: String,
    val resourceURI: String
)

data class ItemXX(
    val name: String,
    val resourceURI: String
)

data class ItemXXX(
    val name: String,
    val resourceURI: String,
    val type: String
)