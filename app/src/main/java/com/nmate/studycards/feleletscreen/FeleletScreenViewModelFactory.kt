package com.nmate.studycards.feleletscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModel

class FeleletScreenViewModelFactory(private val db: Dao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeleletScreenViewModel(db) as T
    }
}