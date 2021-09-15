package com.nmate.studycards.kartyamodositvalaszto

import android.util.LayoutDirection
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.cert.LDAPCertStoreParameters

class KartyaModositValasztoScreenViewModel(private val db : Dao) : ViewModel() {

    private val _tagek = MutableLiveData(listOf(Tag(0L, "Válasszon egy címkét")))
    val tagek: LiveData<List<Tag>> = _tagek

    private val _kivalasztottTag = MutableLiveData(0)
    val kivalasztottTag: LiveData<Int> = _kivalasztottTag

    private val _kartyak = MutableLiveData(listOf<Kartya>())
    val kartyak : LiveData<List<Kartya>> = _kartyak


    init{
        viewModelScope.launch {
            val list = arrayListOf(Tag(0L, "Válasszon egy címkét"))
            for (elem in  _getTagek()){
                list.add(elem)
            }
            _tagek.value = list
        }
    }

    private suspend fun _getTagek() : List<Tag>{
        return withContext(Dispatchers.IO){
            db.getTagek()
        }
    }

    fun kivalaszt(id : Int){
        _kivalasztottTag.value = id
        viewModelScope.launch {
            _kartyak.value = _getKartyak()
        }
    }

    private suspend fun _getKartyak(): List<Kartya> {
        return withContext(Dispatchers.IO){
            db.getKartyaFromTag(listOf(_tagek.value!![_kivalasztottTag.value!!].id!!))
        }
    }

}