package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.steps.presentation.viewmodel.StepCounterViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import com.example.steps.presentation.notifications.NotificationHelper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(viewModel: StepCounterViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event: StepCounterViewModel.ViewModelEvent ->
            when (event) {
                is StepCounterViewModel.ViewModelEvent.MetaAlcanzada -> {
                    NotificationHelper.sendStepGoalNotification(context)
                }
            }
        }
    }

    val pasos by viewModel.pasos.collectAsState()
    val calorias by viewModel.calorias.collectAsState()
    val millas by viewModel.millas.collectAsState()
    val metaAlcanzada by viewModel.metaAlcanzada.collectAsState()
    val mostrarReiniciar by viewModel.mostrarReiniciar.collectAsState()

    val colorBarra = Color(0xFF00F0FF)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { viewModel.iniciarConteo() },
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = colorBarra,
                    startAngle = 270f,
                    sweepAngle = 360f * (pasos / viewModel.metaPasos.toFloat()),
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("$pasos", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("steps", fontSize = 12.sp, color = Color.White)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("${"%.1f".format(calorias)} CAL", fontSize = 10.sp, color = Color(0xFFFF6F00))
                    Text("${"%.2f".format(millas)} MI", fontSize = 10.sp, color = Color(0xFF00B8D4))
                }

                if (metaAlcanzada) {
                    Text("✓ Meta Alcanzada", fontSize = 12.sp, color = Color(0xFF00FF00), fontWeight = FontWeight.Bold)
                }

                LaunchedEffect(metaAlcanzada) {
                    if (metaAlcanzada) {
                        NotificationHelper.sendStepGoalNotification(context)
                    }
                }

                if (mostrarReiniciar) {
                    Button(
                        onClick = { viewModel.reiniciar() },
                        modifier = Modifier
                            .width(80.dp)
                            .height(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF1744),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Reiniciar", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}
