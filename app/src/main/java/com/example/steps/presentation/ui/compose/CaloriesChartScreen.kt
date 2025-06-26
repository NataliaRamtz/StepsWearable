package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.wear.compose.material.Text
import com.example.steps.presentation.viewmodel.RegistroDia
import androidx.compose.ui.Alignment

@Composable
fun CaloriesChartScreen(registros: List<RegistroDia>) {
    val maxCalorias = registros.maxOfOrNull { it.calorias }?.coerceAtLeast(1f) ?: 1f
    val totalBarras = registros.size

    val barSpacingDp = 8.dp
    val barWidthDp = 16.dp
    val scrollState = rememberScrollState()

    // 🟡 Este padding se asegura de respetar la curvatura de la pantalla
    val screenPadding = PaddingValues(horizontal = 26.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(screenPadding),
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Gráfica de Calorías",
            fontSize = 12.sp,
            color = Color.White,
        )

        // GRÁFICA
        Box(
            modifier = Modifier
                .height(80.dp)
                .horizontalScroll(scrollState)
                .padding(horizontal = 10.dp), // margen suave entre Canvas y borde
            contentAlignment = Alignment.CenterStart
        ) {
            Canvas(
                modifier = Modifier
                    .width((barWidthDp + barSpacingDp) * totalBarras)
                    .height(100.dp)
            ) {
                val barWidth = barWidthDp.toPx()
                val space = barSpacingDp.toPx()

                registros.forEachIndexed { index, registro ->
                    val barHeight = (registro.calorias / maxCalorias) * size.height
                    val left = index * (barWidth + space)
                    drawRect(
                        color = if (registro.metaAlcanzada) Color(0xFF00E676) else Color(0xFFFF8A65),
                        topLeft = Offset(left, size.height - barHeight),
                        size = Size(barWidth, barHeight)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // ETIQUETAS
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(barSpacingDp)
        ) {
            registros.forEach {
                Text(
                    text = it.dia.take(1),
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier.width(barWidthDp)
                )
            }
        }
    }
}

