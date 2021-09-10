package com.nmate.studycards.adatbazis

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag
import com.nmate.studycards.modellek.TagKartyaKapcsolo

@Dao
interface Dao {
    @Query("Select * from kartya")
    fun getKartyak(): List<Kartya>
    
    @Query("Select * from Kartya where id = :id")
    fun getKartyaFromId(id : Long) : Kartya

    @Query("Select k.id, k.kerdes, k.tipus, k.valasz  from Kartya k inner join tagkartyakapcsolo tkk on k.id = tkk.KartyaID where tkk.TagID in (:tagID)")
    fun getKartyaFromTag(tagID: List<Long>) : List<Kartya>

    @Insert
    fun insertKartya(kartya: Kartya) : Long

    @Update
    fun updateKartya(kartya: Kartya)

    @Delete
    fun deleteKartya(kartya: Kartya)

    @Query("Select * from Tag")
    fun getTagek(): List<Tag>

    @Query("Select * from Tag where id = :id")
    fun getTagFromId(id : Long) : Tag

    @Insert
    fun insertTag(tag: Tag): Long

    @Update
    fun updateTag(tag: Tag)

    @Delete
    fun deleteTag(tag: Tag)

    @Insert
    fun insertTagOfCard(kartyaKapcsolo: TagKartyaKapcsolo)

    @Query("Select * from tagkartyakapcsolo")
    fun getTagKartyaKapcsolo() : List<TagKartyaKapcsolo>
}