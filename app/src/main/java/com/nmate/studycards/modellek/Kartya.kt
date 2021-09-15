package com.nmate.studycards.modellek

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nmate.studycards.Tipus

@Entity(tableName = "Kartya")
data class Kartya(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long? = 0L,
    @ColumnInfo(name = "kerdes")var kerdes: String? = "",
    @ColumnInfo(name = "tipus")var tipus: Tipus = Tipus.VALASZOLOS,
    @ColumnInfo(name = "valasz")var valasz: String? = "**vege****vege****vege**",
)
