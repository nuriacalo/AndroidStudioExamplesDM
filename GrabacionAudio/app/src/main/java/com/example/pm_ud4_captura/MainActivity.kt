package com.example.pm_ud4_captura

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

/**
 * Estado relacionado con la grabación de audio
 */
data class RecordingState(
    val isRecording: Boolean,
    val recorderRef: MediaRecorder?
)

/**
 * Estado relacionado con la reproducción de audio
 */
data class PlaybackState(
    val isPlaying: Boolean,
    val playerRef: MediaPlayer?
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioRecorderApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioRecorderApp() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var statusText by remember { mutableStateOf("Listo") }
    var recordedFilePath by remember { mutableStateOf<String?>(null) }

    var recordingState by remember {
        mutableStateOf(
            RecordingState(
                isRecording = false,
                recorderRef = null
            )
        )
    }

    var playbackState by remember {
        mutableStateOf(
            PlaybackState(
                isPlaying = false,
                playerRef = null
            )
        )
    }

    /**
     * Lanzador para solicitar permiso de grabación
     */
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                statusText = "Permiso denegado. No es posible grabar."
            }
        }
    )

    /**
     * Solicitud inicial del permiso
     */
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Grabador de 5 segundos", style = MaterialTheme.typography.titleLarge)
            Text("Estado: $statusText")

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Button(
                    onClick = {
                        grabarAudio5s(
                            context = context,
                            coroutineScope = coroutineScope,
                            permissionLauncher = permissionLauncher,
                            recordingState = recordingState,
                            setRecordingState = { recordingState = it },
                            setStatusText = { statusText = it },
                            setRecordedFilePath = { recordedFilePath = it }
                        )
                    },
                    enabled = !recordingState.isRecording
                ) {
                    Text("Grabar 5s")
                }

                Button(
                    onClick = {
                        reproducirODetenerAudio(
                            recordedFilePath = recordedFilePath,
                            playbackState = playbackState,
                            setPlaybackState = { playbackState = it },
                            setStatusText = { statusText = it }
                        )
                    },
                    enabled = !recordingState.isRecording
                ) {
                    Text(if (playbackState.isPlaying) "Detener" else "Reproducir")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (recordedFilePath != null) {
                Text("Fichero: $recordedFilePath",  style = MaterialTheme.typography.bodySmall)
            }

        }
    }

    /**
     * Liberación de recursos al salir de composición
     */
    DisposableEffect(Unit) {
        onDispose {
            try {
                recordingState.recorderRef?.release()
            } catch (_: Exception) { }
            try {
                playbackState.playerRef?.release()
            } catch (_: Exception) { }
        }
    }
}

/**
 * Inicia una grabación de audio de 5 segundos
 */
fun grabarAudio5s(
    context: android.content.Context,
    coroutineScope: CoroutineScope,
    permissionLauncher: ActivityResultLauncher<String>,
    recordingState: RecordingState,
    setRecordingState: (RecordingState) -> Unit,
    setStatusText: (String) -> Unit,
    setRecordedFilePath: (String?) -> Unit
) {
    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

    coroutineScope.launch {
        try {
            val permGranted =
                ContextCompat.checkSelfPermission(
                    context,Manifest.permission.RECORD_AUDIO
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (!permGranted) {
                setStatusText("Solicitud de permiso enviada. Concede permiso y vuelve a pulsar.")
            } else if (!recordingState.isRecording) {

                setRecordingState(recordingState.copy(isRecording = true))
                setStatusText("Preparando grabación...")

                val fileName = "grabacion_5s.m4a"
                val outFile = File(context.filesDir, fileName)
                setRecordedFilePath(outFile.absolutePath)

                val recorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setOutputFile(outFile.absolutePath)
                    prepare()
                }

                setRecordingState(
                    recordingState.copy(
                        isRecording = true,
                        recorderRef = recorder
                    )
                )

                recorder.start()
                setStatusText("Grabando... (5s)")

                delay(5000L)

                try {
                    recorder.stop()
                } catch (e: RuntimeException) {
                    Log.e("AudioRecorder", "Error al detener recorder", e)
                }

                recorder.release()
                setRecordingState(
                    recordingState.copy(
                        isRecording = false,
                        recorderRef = null
                    )
                )
                setStatusText("Grabación finalizada: ${outFile.name}")
            }

        } catch (e: IOException) {
            Log.e("AudioRecorder", "Error grabando", e)
            setRecordingState(recordingState.copy(isRecording = false, recorderRef = null))
            setStatusText("Error: ${e.message}")
        }
    }
}

/**
 * Reproduce o detiene el audio grabado
 */
fun reproducirODetenerAudio(
    recordedFilePath: String?,
    playbackState: PlaybackState,
    setPlaybackState: (PlaybackState) -> Unit,
    setStatusText: (String) -> Unit
) {
    val path = recordedFilePath
    if (path == null) {
        setStatusText("No hay grabación para reproducir.")
        return
    }

    if (playbackState.isPlaying) {
        playbackState.playerRef?.stop()
        playbackState.playerRef?.release()
        setPlaybackState(
            playbackState.copy(isPlaying = false, playerRef = null)
        )
        setStatusText("Reproducción detenida")
    } else {
        try {
            val player = MediaPlayer().apply {
                setDataSource(path)
                prepare()
                start()
            }

            setPlaybackState(
                playbackState.copy(isPlaying = true, playerRef = player)
            )
            setStatusText("Reproduciendo...")

            player.setOnCompletionListener {
                it.release()
                setPlaybackState(
                    playbackState.copy(isPlaying = false, playerRef = null)
                )
                setStatusText("Reproducción completada")
            }

        } catch (e: IOException) {
            Log.e("AudioRecorder", "Error al reproducir", e)
            setStatusText("Error al reproducir: ${e.message}")
        }
    }
}
