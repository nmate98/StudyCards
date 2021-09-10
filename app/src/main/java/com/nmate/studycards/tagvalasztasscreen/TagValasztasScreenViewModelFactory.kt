package com.nmate.studycards.tagvalasztasscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModel

class TagValasztasScreenViewModelFactory (private val db : Dao): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TagValasztasScreenViewModel(db) as T
        }

}