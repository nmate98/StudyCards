package com.nmate.studycards.feleletscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.modellek.Kartya
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeleletScreenViewModel(private val db: Dao, private val id: String) : ViewModel() {

    private val _kartyak = MutableLiveData(listOf(Kartya()))
    val kartyak: LiveData<List<Kartya>> = _kartyak

    private val _helyes = MutableLiveData<Long>(0)
    val helyes: LiveData<Long> = _helyes

    private val _ssz = MutableLiveData(0)
    val ssz : LiveData<Int> = _ssz


    private val _aktualisKerdes = MutableLiveData(Kartya())
    val aktualisKerdes: LiveData<Kartya> = _aktualisKerdes

    private val _kevertValaszok = MutableLiveData<List<String>>()
    val kevertValaszok: LiveData<List<String>> = _kevertValaszok

    private var _helyesValasz = ""

    private val _bevittValasz = MutableLiveData("")
    val bevittValasz : LiveData<String> = _bevittValasz

    private val _kesz = MutableLiveData(false)
    val kesz : LiveData<Boolean> = _kesz

    init{
        viewModelScope.launch {
            val idStringList = id.split("_")
            val list = arrayListOf<Long>()
            for (elem in idStringList) {
                list.add(elem.toLong())
            }
            _kartyak.value = _getKerdesek(list).shuffled()
            _aktualisKerdes.value = _kartyak.value!![0]
            if (_aktualisKerdes.value!!.tipus == Tipus.VALASZOLOS) {
                _helyesValasz = _aktualisKerdes.value!!.valasz!!
            } else {
                _kevertValaszok.value = _aktualisKerdes.value!!.valasz!!.split("**vege**")
                _helyesValasz = _kevertValaszok.value!![0]
                _kevertValaszok.value = _kevertValaszok.value!!.shuffled()
            }
        }
    }

    private suspend fun _getKerdesek(idList: ArrayList<Long>): List<Kartya> {
        return withContext(Dispatchers.IO) {
            db.getKartyaFromTag(idList)
        }
    }

    fun leptetes() : Boolean {
        _ssz.value = _ssz.value!!+1
        if (_ssz.value!! < _kartyak.value!!.size) {
            _aktualisKerdes.value = _kartyak.value!![_ssz.value!!]
            if (_aktualisKerdes.value!!.tipus == Tipus.VALASZOLOS) {
                _helyesValasz = _aktualisKerdes.value!!.valasz!!
            } else {
                _kevertValaszok.value = _aktualisKerdes.value!!.valasz!!.split("**vege**")
                _helyesValasz = _kevertValaszok.value!![0]
                _kevertValaszok.value = _kevertValaszok.value!!.shuffled()
            }
        }
        return _ssz.value!! == _kartyak.value!!.size
    }

    fun setKesz(kesz : Boolean){
        _kesz.value = kesz
    }

    fun osszehasonlitHelyesValasszal(text : String) : Boolean {
        return text == _helyesValasz
    }

    fun ellenorzes(): Boolean {
        if (_helyesValasz == _bevittValasz.value) {
            _helyes.value = helyes.value!! + 1
        }
        return _helyesValasz == _bevittValasz.value
    }

    fun setBevittValasz(bemenet : String){
        _bevittValasz.value = bemenet
    }

}


