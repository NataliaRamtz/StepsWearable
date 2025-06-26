package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.SwipeToDismissBoxState
import androidx.wear.compose.material.rememberSwipeToDismissBoxState
import com.example.steps.presentation.viewmodel.StepCounterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StepCounterScreen(viewModel: StepCounterViewModel = viewModel()) {
    val state: SwipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val pages = listOf("contador", "historial", "grafica")
    var currentPage by remember { mutableStateOf(0) }

    SwipeToDismissBox(
        state = state,
        onDismissed = {
            currentPage = (currentPage + 1) % pages.size
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            when (pages[currentPage]) {
                "contador" -> CounterScreen(viewModel)
                "historial" -> HistoryScreen(viewModel.registros)
                "grafica" -> CaloriesChartScreen(viewModel.registros)
            }
        }
    }
}

