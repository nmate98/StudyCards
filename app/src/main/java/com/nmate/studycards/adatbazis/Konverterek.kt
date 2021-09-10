package com.nmate.studycards.adatbazis

import androidx.room.TypeConverter
import com.nmate.studycards.Tipus

class Konverterek {

        @TypeConverter
        fun toHealth(value: String) = enumValueOf<Tipus>(value)

        @TypeConverter
        fun fromHealth(value: Tipus) = value.name
}