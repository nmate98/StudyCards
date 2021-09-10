package com.nmate.studycards.kartyaletrehozasscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nmate.studycards.adatbazis.Dao

class KartyaLetrehozasScreenViewModelFactory(private val db : Dao): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KartyaLetrehozasScreenViewModel(db) as T
    }
}