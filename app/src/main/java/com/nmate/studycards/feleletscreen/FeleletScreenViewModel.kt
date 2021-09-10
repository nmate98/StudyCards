package com.nmate.studycards.feleletscreen

import android.util.Log
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

class FeleletScreenViewModel(private val db: Dao) : ViewModel() {

    private val _kartyak = MutableLiveData<List<Kartya>>()
    val kartyak: LiveData<List<Kartya>> = _kartyak

    private val _helyes = MutableLiveData<Long>(0)
    val helyes: LiveData<Long> = _helyes

    private var _ssz = -1

    private val _aktualisKerdes = MutableLiveData(Kartya())
    val aktualisKerdes: LiveData<Kartya> = _aktualisKerdes

    private val _kevertValaszok = MutableLiveData<List<String>>()
    val kevertValaszok: LiveData<List<String>> = _kevertValaszok

    private var _helyesValasz = ""

    private val _bevittValasz = MutableLiveData("")
    val bevittValasz : LiveData<String> = _bevittValasz

    private val _kesz = MutableLiveData(false)
    val kesz : LiveData<Boolean> = _kesz

    fun getKerdesek(idStringList: List<String>) {
        viewModelScope.launch {
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

    fun leptetes() {
        if (_ssz < _kartyak.value!!.size - 1) {
            _ssz++
            _aktualisKerdes.value = _kartyak.value!![_ssz]
            if (_aktualisKerdes.value!!.tipus == Tipus.VALASZOLOS) {
                _helyesValasz = _aktualisKerdes.value!!.valasz!!
            } else {
                _kevertValaszok.value = _aktualisKerdes.value!!.valasz!!.split("**vege**")
                _helyesValasz = _kevertValaszok.value!![0]
                _kevertValaszok.value = _kevertValaszok.value!!.shuffled()
            }
        }
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


