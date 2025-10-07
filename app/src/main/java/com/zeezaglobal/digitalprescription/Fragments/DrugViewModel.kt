package com.zeezaglobal.digitalprescription.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.digitalprescription.Entity.Drug
import com.zeezaglobal.digitalprescription.Repository.DrugRepository
import com.zeezaglobal.digitalprescription.Repository.DrugRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DrugViewModel @Inject constructor(
    private val repository: DrugRepository
) : ViewModel() {

    private val _drugs = MutableLiveData<List<Drug>>()
    val drugs: LiveData<List<Drug>> get() = _drugs

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun searchDrugs(keyword: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val result = repository.searchDrugs(keyword)
                _drugs.postValue(result)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}