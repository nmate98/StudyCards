package com.nmate.studycards.modellek

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "TagKartyaKapcsolo",
    foreignKeys = [ForeignKey(entity = Tag::class,
        parentColumns = ["id"],
        childColumns = ["TagID"],
        onDelete = CASCADE),
        ForeignKey(entity = Kartya::class,
            parentColumns = ["id"],
            childColumns = ["KartyaID"],
            onDelete = CASCADE)],
    primaryKeys = ["TagID", "KartyaID"])
data class TagKartyaKapcsolo(
    @ColumnInfo(index = true, name = "TagID") var TagID: Long,
    @ColumnInfo(index = true, name = "KartyaID") var KartyaID: Long,
)