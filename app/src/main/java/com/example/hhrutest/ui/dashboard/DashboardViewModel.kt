package com.example.hhrutest.ui.dashboard

import android.util.Log
import androidx.compose.ui.util.fastFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DownloadResult
import com.example.domain.model.Response
import com.example.domain.model.Vacancy
import com.example.domain.usecase.GetAllResponseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val getAllResponseUseCase : GetAllResponseUseCase) : ViewModel() {

    private val _response = MutableStateFlow(Response(emptyList(), emptyList()))
    val response = _response.asStateFlow()

    private var _isServerError = MutableStateFlow(false)
    val isServerError: StateFlow<Boolean> = _isServerError.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _favorite = MutableStateFlow<List<Vacancy>>(emptyList())
    val favorite: StateFlow<List<Vacancy>> = _favorite.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            getAllResponseUseCase
                .execute()
                .stateIn(scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    DownloadResult.Progress(Unit)).collect {
                        when (it) {
                            is DownloadResult.Success<*> -> {
                                val finalData = it.value as Response
                                Log.d("CHECK", finalData.vacancies.toString())
                                _response.update { response ->
                                    response.copy(finalData.offers, finalData.vacancies)
                                }
                                _favorite.update {
                                    getAllFavorite()
                                }
                                _isServerError.update { false }
                                _isLoading.update { false }
                            }
                            is DownloadResult.Error -> {
                                _isServerError.update { true }
                            }
                            is DownloadResult.Progress -> {
                                _isLoading.update { true }
                            }
                        }
                }
        }
    }

    fun getVacancy(id: String?) : List<Vacancy> {
        return id.let { response.value.vacancies.filter { it.id == id } }
    }

    fun getAllFavorite() : List<Vacancy> {
        if (response.value.vacancies.isEmpty()) {
            getData()
        }
        return response.value.vacancies.filter { it.isFavorite }
    }

    fun favoriteButtonClick(id : String?) {
        val temp = response.value.vacancies
        temp.filter { it.id == id }.map { vacancy -> vacancy.isFavorite = !vacancy.isFavorite }
        _response.update {
            it.copy(vacancies = temp)
        }
        _favorite.update {
            getAllFavorite()
        }
    }
}