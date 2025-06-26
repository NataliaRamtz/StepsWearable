package com.example.steps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
//import com.example.steps.presentation.notifications.NotificationHelper

class StepCounterViewModel : ViewModel() {

    private val _pasos = MutableStateFlow(0)
    val pasos: StateFlow<Int> = _pasos

    private val _calorias = MutableStateFlow(0f)
    val calorias: StateFlow<Float> = _calorias

    private val _millas = MutableStateFlow(0f)
    val millas: StateFlow<Float> = _millas

    private val _metaAlcanzada = MutableStateFlow(false)
    val metaAlcanzada: StateFlow<Boolean> = _metaAlcanzada

    private val _mostrarReiniciar = MutableStateFlow(false)
    val mostrarReiniciar: StateFlow<Boolean> = _mostrarReiniciar

    private val _eventFlow = MutableSharedFlow<ViewModelEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val metaPasos = 100
    private var contando = false

    val registros = mutableStateListOf(
        RegistroDia("Lun", 3.5f, false),
        RegistroDia("Mar", 4.8f, true),
        RegistroDia("Mié", 2.1f, false),
        RegistroDia("Jue", 4.5f, true),
        RegistroDia("Vie", 1.9f, false)
    )

    fun iniciarConteo() {
        if (contando) return
        contando = true
        _mostrarReiniciar.value = true

        viewModelScope.launch {
            while (_pasos.value < metaPasos && contando) {
                delay(300L)
                _pasos.value += 1
                _calorias.value = _pasos.value * 0.045f
                _millas.value = _pasos.value * 0.00041f
                if (_pasos.value >= metaPasos && !_metaAlcanzada.value) {
                    _metaAlcanzada.value = true
                    registros.add(RegistroDia("Hoy", _calorias.value, true))
                    //NotificationHelper.sendStepGoalNotification(context)
                    _eventFlow.emit(ViewModelEvent.MetaAlcanzada)
                }
            }
        }
    }

    fun reiniciar() {
        contando = false

        if (!_metaAlcanzada.value) {
            registros.add(RegistroDia("Hoy", _calorias.value, false))
        }

        // Reiniciar todos los valores
        _pasos.value = 0
        _calorias.value = 0f
        _millas.value = 0f
        _metaAlcanzada.value = false
        _mostrarReiniciar.value = false

        iniciarConteo()
    }
    sealed class ViewModelEvent {
        object MetaAlcanzada : ViewModelEvent()
    }

}
