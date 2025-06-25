/*package com.example.steps.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveMonitoringClient
import androidx.health.services.client.data.DataPoint
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveMonitoringUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import androidx.health.services.client.CallbackPassiveListenerService

class StepUpdateService : CallbackPassiveListenerService() {

    companion object {
        val stepFlow = MutableStateFlow(0L)
    }

    override fun onUpdateReceived(update: PassiveMonitoringUpdate) {
        update.dataPoints.forEach { point: DataPoint ->
            if (point.dataType == DataType.STEP_COUNT_CUMULATIVE) {
                val steps = point.value.asLong()
                Log.d("StepUpdateService", "Steps: $steps")
                stepFlow.value = steps
            }
        }
    }

    override fun onAvailabilityChanged(dataType: DataType, isAvailable: Boolean) {
        Log.d("StepUpdateService", "$dataType available = $isAvailable")
    }
}*/
