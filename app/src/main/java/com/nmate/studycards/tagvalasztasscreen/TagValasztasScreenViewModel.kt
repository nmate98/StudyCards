package com.nmate.studycards.tagvalasztasscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.modellek.Tag
import kotlinx.coroutines.*

class TagValasztasScreenViewModel(private val db : Dao) : ViewModel() {


    private val _tagek = MutableLiveData(listOf<Tag>())
    val tagek: LiveData<List<Tag>> = _tagek

    private val _kivalasztottTagek = MutableLiveData(arrayListOf<Long>())
    val kivalasztottTagek: LiveData<ArrayList<Long>> = _kivalasztottTagek


    init{
        viewModelScope.launch {
            _tagek.value = _getTagek()
        }
    }


    private suspend fun _getTagek() : List<Tag>{
        return withContext(Dispatchers.IO){
             db.getTagek()
        }
    }

    fun kivalaszt(id: Long){
        val list = arrayListOf<Long>()
        for(elem in _kivalasztottTagek.value!!){
            list.add(elem)
        }
        list.add(id)
        _kivalasztottTagek.value = list
    }

    fun torol(id: Long){
        val list = arrayListOf<Long>()
        for(elem in _kivalasztottTagek.value!!){
            list.add(elem)
        }
        list.remove(id)
        _kivalasztottTagek.value = list
    }


}