package com.example.steps.presentation.ui.compose
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsWalk
import kotlinx.coroutines.delay
import com.example.steps.presentation.viewmodel.StepCounterViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.wear.compose.material.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StepCounterScreen() {
    var pasos by remember { mutableStateOf(0) }
    var calorias by remember { mutableStateOf(0f) }
    var millas by remember { mutableStateOf(0f) }
    var contando by remember { mutableStateOf(false) }
    var mostrarReiniciar by remember { mutableStateOf(false) }
    var metaAlcanzada by remember { mutableStateOf(false) }

    val metaPasos = 100
    val scope = rememberCoroutineScope()
    val colorBarra = Color(0xFF00F0FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!contando) {
                    contando = true
                    mostrarReiniciar = true
                    scope.launch {
                        while (pasos < metaPasos && contando) {
                            delay(300L)
                            pasos += 1
                            calorias = pasos * 0.045f
                            millas = pasos * 0.00041f
                            if (pasos >= metaPasos) metaAlcanzada = true
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Fondo negro
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = Color.Black)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(160.dp)
        ) {
            // Barra de progreso neón
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = colorBarra,
                    startAngle = 270f,
                    sweepAngle = 360f * (pasos.toFloat() / metaPasos),
                    useCenter = false,
                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Contenido central
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Pasos
                Text(
                    "$pasos",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "steps",
                    fontSize = 12.sp,
                    color = Color.White
                )

                // CAL y MI
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        "${"%.1f".format(calorias)} CAL",
                        fontSize = 10.sp,
                        color = Color(0xFFFF6F00)
                    )
                    Text(
                        "${"%.2f".format(millas)} MI",
                        fontSize = 10.sp,
                        color = Color(0xFF00B8D4)
                    )
                }

                // Botón y mensaje
                Box(
                    modifier = Modifier.padding(top = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (metaAlcanzada) {
                        Text(
                            "✓ Meta Alcanzada",
                            fontSize = 12.sp,
                            color = Color(0xFF00FF00),
                            fontWeight = FontWeight.Bold
                        )
                    } else if (mostrarReiniciar) {
                        Button(
                            onClick = {
                                // Detener el contador antes de reiniciar
                                contando = false
                                // Reiniciar todos los valores
                                pasos = 0
                                calorias = 0f
                                millas = 0f
                                mostrarReiniciar = false
                                metaAlcanzada = false
                            },
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
}




/*package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsWalk
import kotlinx.coroutines.delay
import com.example.steps.presentation.viewmodel.StepCounterViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StepCounterScreen(viewModel: StepCounterViewModel = viewModel()) {
    val steps by viewModel.steps.collectAsState()

    val progress = (steps % 10000)/10000f

    Scaffold (
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(5.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(70.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(
                        color = Color.LightGray,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = Color.Green,
                        startAngle = -90f,
                        sweepAngle = progress * 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.DirectionsWalk,
                    contentDescription = "Caminando",
                    tint = Color.LightGray,
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "$steps",
                    //textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.display1
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Pasos",
                    //textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.display1

                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = { viewModel.resetSteps() },
                modifier = Modifier
                    .width(80.dp)
                    .height(25.dp),
                shape = RoundedCornerShape(10.dp),
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),

            ) {
                Text(text = "Reiniciar",
                    fontSize = 16.sp,
                )
            }
        }

    }
}*/

/*package com.example.steps.presentation.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsWalk
import kotlinx.coroutines.delay
import com.example.steps.presentation.viewmodel.StepCounterViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StepCounterScreen(viewModel: StepCounterViewModel = viewModel()) {
    val steps by viewModel.steps.collectAsState()

    val progress = (steps % 10000)/10000f

    Scaffold (
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(5.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(70.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(
                        color = Color.LightGray,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = Color.Green,
                        startAngle = -90f,
                        sweepAngle = progress * 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.DirectionsWalk,
                    contentDescription = "Caminando",
                    tint = Color.LightGray,
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "$steps",
                    //textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.display1
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Pasos",
                    //textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.display1

                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = { viewModel.resetSteps() },
                modifier = Modifier
                    .width(80.dp)
                    .height(25.dp),
                shape = RoundedCornerShape(10.dp),
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),

            ) {
                Text(text = "Reiniciar",
                    fontSize = 16.sp,
                )
            }
        }

    }
}*/