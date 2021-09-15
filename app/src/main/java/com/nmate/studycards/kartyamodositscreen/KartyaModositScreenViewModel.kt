package com.nmate.studycards.kartyamodositscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmate.studycards.adatbazis.Dao
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag
import com.nmate.studycards.modellek.TagKartyaKapcsolo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KartyaModositScreenViewModel(private val db: Dao, private val id: Long) : ViewModel() {

    private val _tagek = MutableLiveData<List<Tag>>(listOf())
    val tagek: LiveData<List<Tag>> = _tagek

    private val _kartya = MutableLiveData(Kartya())
    val kartya: LiveData<Kartya> = _kartya

    private val _kivalasztottTagek = MutableLiveData(arrayListOf<Long>())
    val kivalasztottTagek: LiveData<ArrayList<Long>> = _kivalasztottTagek

    private val _kivalasztottKezdoTagek = arrayListOf<Long>()
    private var _regiTagek = arrayListOf<Long>()
    private var _eredetiKartya = Kartya()

    init {
        viewModelScope.launch {
            _kartya.value = _getKartya(id)
            _eredetiKartya = _kartya.value!!.copy()
            _kivalasztottTagek.value = _getKartyaTagek(_kartya.value!!.id!!)
            _tagek.value = _getTagek()
            for (elem in _kivalasztottTagek.value!!) {
                _kivalasztottKezdoTagek.add(elem)
            }
        }

    }

    fun getTagek(){
        viewModelScope.launch {
            _tagek.value = _getTagek()
        }
    }

    private suspend fun _getKartyaTagek(id: Long): ArrayList<Long> {
        return withContext(Dispatchers.IO) {
            db.getTagOfKartya(id) as ArrayList<Long>
        }
    }

    private suspend fun _getKartya(id: Long): Kartya {
        return withContext(Dispatchers.IO) {
            db.getKartyaFromId(id)
        }
    }

    private suspend fun _getTagek(): List<Tag> {
        return withContext(Dispatchers.IO) {
            db.getTagek()
        }
    }

    fun tagKivalaszt(id: Long) {
        val list = arrayListOf<Long>()
        for (elem in _kivalasztottTagek.value!!) {
            list.add(elem)
        }
        list.add(id)
        _kivalasztottTagek.value = list
    }

    fun tagTorol(id: Long) {
        val list = arrayListOf<Long>()
        for (elem in _kivalasztottTagek.value!!) {
            list.add(elem)
        }
        list.remove(id)
        _kivalasztottTagek.value = list
    }

    fun tagVisszaallit() {
        _kivalasztottTagek.value = _regiTagek
        _kivalasztottTagek.value = arrayListOf()
        for (tag in _regiTagek) {
            _kivalasztottTagek.value!!.add(tag)
        }
    }

    fun saveTag(tag: Tag) {
        viewModelScope.launch {
            tagKivalaszt(_saveTag(tag))
        }
    }


    private suspend fun _saveTag(tag: Tag): Long {
        return withContext(Dispatchers.IO) {
            db.insertTag(tag)
        }
    }

    fun tagMasol() {
        _regiTagek = arrayListOf()
        for (tag in _kivalasztottTagek.value!!) {
            _regiTagek.add(tag)
        }
    }

    fun tagListaUrit() {
        _kivalasztottTagek.value = arrayListOf()
    }

    fun updateKartya() {
        viewModelScope.launch {
            _updateKartya()
        }
    }

    private suspend fun _updateKartya() {
        withContext(Dispatchers.IO) {
            db.updateKartya(_kartya.value!!)
            db.tagTorles(_kartya.value!!.id!!, _kivalasztottTagek.value!!)
            for (elem in _kivalasztottTagek.value!!.minus(_kivalasztottKezdoTagek)) {
                db.insertTagOfCard(TagKartyaKapcsolo(elem, _kartya.value!!.id!!))
            }
        }
    }

    fun setKerdes(kerdes: String) {
        _kartya.value = _kartya.value!!.copy(kerdes = kerdes)
    }

    fun setFVValasz(modValasz: String, poz: Int) {
        val valaszok = _kartya.value!!.valasz!!.split("**vege**") as ArrayList
        valaszok[poz] = modValasz
        val valasz =
            valaszok[0] + "**vege**" + valaszok[1] + "**vege**" + valaszok[2] + "**vege**" + valaszok[3]
        _kartya.value = _kartya.value!!.copy(valasz = valasz)
    }

    fun setVValasz(modValasz: String) {
        _kartya.value = _kartya.value!!.copy(valasz = modValasz)
    }

    fun torolKartya() {
        viewModelScope.launch {
            _kartyaTorles(id)
        }
    }

    private suspend fun _kartyaTorles(id: Long) {
        withContext(Dispatchers.IO) {
            db.torolTag(id)
            db.deleteKartya(_eredetiKartya)
        }
    }
}