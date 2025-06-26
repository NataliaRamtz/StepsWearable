package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.*
import com.example.steps.presentation.viewmodel.RegistroDia
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn

@Composable
fun HistoryScreen(registros: List<RegistroDia>) {
    if (registros.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay registros aún", color = Color.White)
        }
    } else {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Historial de Calorías",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            items(registros.size) { index ->
                val registro = registros[registros.size - 1 - index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 4.dp),
                    onClick = {},
                    backgroundPainter = CardDefaults.cardBackgroundPainter(
                        startBackgroundColor = if (registro.metaAlcanzada) Color(0xFF004D40) else Color(0xFF4E342E),
                        endBackgroundColor = if (registro.metaAlcanzada) Color(0xFF00796B) else Color(0xFF6D4C41)
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
                        Text(registro.dia, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("${"%.1f".format(registro.calorias)} calorías", fontSize = 11.sp, color = Color(0xFFFFCC80))
                        Text(
                            if (registro.metaAlcanzada) "✓ Meta alcanzada" else "✗ Meta no alcanzada",
                            fontSize = 10.sp,
                            color = if (registro.metaAlcanzada) Color(0xFF00FF00) else Color(0xFFFF5252)
                        )
                    }
                }
            }
        }
    }
}
