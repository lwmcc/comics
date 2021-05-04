package com.mccarty.comics.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mccarty.comics.models.Result

@Dao
interface CharacterDao {
    @Query("SELECT * FROM result ORDER BY name ASC")
    fun getAllCharacters(): Array<Result>

    @Query("SELECT * FROM result ORDER BY name DESC")
    fun getAllCharactersDesc(): Array<Result>

    @Query("SELECT * FROM result WHERE NAME")
    fun findSearchedCharacter(): Array<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: Array<Result>)
}