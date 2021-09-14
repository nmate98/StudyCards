package com.nmate.studycards.kartyaletrehozasscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag
import com.nmate.studycards.modellek.TagKartyaKapcsolo
import kotlinx.coroutines.*

class KartyaLetrehozasScreenViewModel(private val db: Dao) : ViewModel() {

    private val _tagek = MutableLiveData<List<Tag>>(listOf())
    val tagek: LiveData<List<Tag>> = _tagek

    private var regiTagek = arrayListOf<Long>()

    private val _kivalasztottTagek = MutableLiveData(arrayListOf<Long>())
    val kivalasztottTagek: LiveData<ArrayList<Long>> = _kivalasztottTagek

    init{
        viewModelScope.launch {
            _tagek.value = _getTagek()
        }
    }

    private suspend fun _getTagek(): List<Tag> {
        return withContext(Dispatchers.IO) {
            db.getTagek()
        }
    }

    fun tagKivalaszt(id: Long) {
        val list = arrayListOf<Long>()
        for(elem in _kivalasztottTagek.value!!){
            list.add(elem)
        }
        list.add(id)
        _kivalasztottTagek.value = list
    }

    fun tagTorol(id: Long) {
        val list = arrayListOf<Long>()
        for(elem in _kivalasztottTagek.value!!){
            list.add(elem)
        }
        list.remove(id)
        _kivalasztottTagek.value = list
    }

    fun tagVisszaallit(){
        _kivalasztottTagek.value = regiTagek
        _kivalasztottTagek.value = arrayListOf()
        for(tag in regiTagek){
            _kivalasztottTagek.value!!.add(tag)
        }
    }

    fun saveKartya(kartya: Kartya) {
        viewModelScope.launch {
            _saveKartya(kartya)
        }
    }

    fun saveTag(tag: Tag) {
        viewModelScope.launch {
            tagKivalaszt(_saveTag(tag))
        }
    }

    private suspend fun _saveKartya(kartya: Kartya){
        withContext(Dispatchers.IO){
            val id = db.insertKartya(kartya)
            for (tag in _kivalasztottTagek.value!!) {
                db.insertTagOfCard(TagKartyaKapcsolo(TagID = tag, KartyaID = id))
            }
        }
    }

    private suspend fun _saveTag(tag: Tag): Long {
        return withContext(Dispatchers.IO) {
            db.insertTag(tag)
        }
    }

    fun tagMasol() {
        regiTagek = arrayListOf()
        for(tag in _kivalasztottTagek.value!!){
            regiTagek.add(tag)
        }
    }

    fun tagListaUrit(){
        _kivalasztottTagek.value = arrayListOf()
    }
}