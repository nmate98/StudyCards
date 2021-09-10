package com.nmate.studycards.modellek

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tag")
data class Tag (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id : Long?,
    @ColumnInfo(name = "tag")var tag: String
)