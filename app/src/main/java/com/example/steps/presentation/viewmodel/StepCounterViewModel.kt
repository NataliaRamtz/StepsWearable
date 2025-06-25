package com.example.steps.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StepCounterViewModel : ViewModel() {

    private val _steps = MutableStateFlow(0)
    val steps : StateFlow<Int> = _steps

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _steps.value += 1
            }
        }
    }

    fun resetSteps() {
        _steps.value = 0
    }
}

/*package com.example.steps.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.steps.presentation.service.StepCounterMonitorService
import kotlinx.coroutines.flow.collect

class StepCounterViewModel : ViewModel() {

    private val _steps = MutableStateFlow(0)
    val steps : StateFlow<Int> = _steps

    init {
        viewModelScope.launch {
            StepCounterMonitorService.stepCountFlow.collect { stepValue ->
                _steps.value = stepValue.toInt()
            }
        }
    }

    fun resetSteps() {
        _steps.value = 0
    }
}*/