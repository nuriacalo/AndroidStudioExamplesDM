package com.example.acelerometro

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity(), SensorEventListener {

    // Sensor
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // Parámetros de shake
    private val shakeThreshold = 2f
    private val shakeCooldown = 1000L
    private var lastShakeTime = 0L

    // Estado para Compose
    private var shakeCount by mutableStateOf(0)
    private var gForceValue by mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            MaterialTheme {
                ShakeScreen(
                    gForce = gForceValue,
                    shakes = shakeCount
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

     /**
     * Maneja los eventos generados por el acelerómetro.
     *
     * Esta función se ejecuta automáticamente cada vez que el sensor detecta
     * cambios en la aceleración del dispositivo. Su objetivo es calcular la
     * fuerza G total y determinar si el usuario ha realizado una sacudida.
     *
     * Flujo de funcionamiento:
     * 1. Se obtiene la aceleración en los ejes X, Y y Z.
     * 2. Cada eje se normaliza dividiendo entre la gravedad estándar (9.81 m/s²),
     *    lo que convierte la aceleración en "fuerza G".
     * 3. Se calcula la magnitud del vector (fuerza total en G).
     * 4. Si la fuerza supera el umbral definido (`shakeThreshold`) y ha pasado
     *    suficiente tiempo desde la última detección, se considera una sacudida
     *    válida y se llama a `onShakeDetected()`.
     *
     * @param event Objeto con la información del sensor, incluyendo los valores
     *              de aceleración en cada eje. Si es null, la función se detiene.
     */

    override fun onSensorChanged(event: SensorEvent) {
        // Se obtiene la aceleración en los ejes X, Y y Z.
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        // Cada eje se normaliza dividiendo entre la gravedad estándar (9.81 m/s²),
        //  lo que convierte la aceleración en "fuerza G".
        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // Se calcula la magnitud del vector (fuerza total en G).
        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)
        gForceValue = gForce

         // Si la fuerza supera el umbral definido (`shakeThreshold`) y ha pasado
         // suficiente tiempo desde la última detección, se considera una sacudida
         // válida y se llama a `onShakeDetected()`.
        if (gForce > shakeThreshold) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTime >= shakeCooldown) {
                lastShakeTime = now
                shakeCount++
                onShakeDetected()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun onShakeDetected() {
        Toast.makeText(this, "Sacudida detectada", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShakeScreen(gForce: Float, shakes: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detector de Sacudidas") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Fuerza G detectada:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = String.format("%.2f g", gForce),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Número de sacudidas:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = shakes.toString(),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
