package com.nmate.studycards.kartyamodositvalaszto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nmate.studycards.adatbazis.Dao

class KartyaModositValasztoScreenViewModelFactory(private val db: Dao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KartyaModositValasztoScreenViewModel(db) as T
    }

}