package com.mccarty.comics.api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicCharacterService {

    @GET("/v1/public/characters")
    suspend fun searchAllCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int): JsonObject
}