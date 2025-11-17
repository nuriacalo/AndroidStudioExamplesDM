package com.example.ejemploviewmodel.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ejemploviewmodel.model.EjemploUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class EjemploAppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EjemploUIState())
    val uiState: StateFlow<EjemploUIState> = _uiState.asStateFlow()

    fun incrementar() {
        _uiState.value = _uiState.value.copy(contador = _uiState.value.contador + 1)
    }

    fun incrementarWithUpdate() {
        _uiState.update { current -> current.copy(contador = current.contador + 1) }
    }

    fun cambiarEstadoSwitch(){
        _uiState.value = _uiState.value.copy(estadoSwitch = !_uiState.value.estadoSwitch)

    }

    fun cambiarEstadoSwitchWithUpdate(){
        _uiState.update { current -> current.copy(estadoSwitch = !current.estadoSwitch) }
    }
}

