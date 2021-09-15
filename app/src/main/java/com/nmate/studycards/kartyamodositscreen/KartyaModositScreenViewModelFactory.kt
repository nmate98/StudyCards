package com.nmate.studycards.kartyamodositscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nmate.studycards.adatbazis.Dao

class KartyaModositScreenViewModelFactory(private val db: Dao, private val id : Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KartyaModositScreenViewModel(db, id) as T
    }


}