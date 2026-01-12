# PM_UD4_Captura – Grabador de Audio en Android

Aplicación Android desarrollada en Kotlin con Jetpack Compose que permite grabar audio durante 5 segundos y reproducir la grabación, gestionando permisos en tiempo de ejecución y el ciclo de vida de los recursos multimedia.

Proyecto orientado a la captura, procesamiento y almacenamiento de datos multimedia, correspondiente a la Unidad Didáctica 4.

---

## Funcionalidad principal

- Solicitud dinámica del permiso RECORD_AUDIO
- Grabación de audio desde el micrófono durante 5 segundos
- Almacenamiento del audio en formato M4A (AAC)
- Reproducción y detención del audio grabado
- Gestión segura de recursos MediaRecorder y MediaPlayer
- Interfaz desarrollada con Material 3 y Jetpack Compose

---

## Grabación de audio

La grabación de audio se implementa mediante la clase MediaRecorder con las siguientes características:

- Fuente de audio: micrófono
- Formato de salida: MPEG_4
- Códec de audio: AAC
- Duración controlada mediante corutinas (delay de 5 segundos)
- El archivo se almacena en el almacenamiento interno de la aplicación

---

## Reproducción de audio

La reproducción se realiza utilizando MediaPlayer y permite:

- Reproducir el audio grabado
- Detener manualmente la reproducción
- Liberar automáticamente los recursos al finalizar

---

## Permisos

La aplicación solicita en tiempo de ejecución el siguiente permiso:

android.permission.RECORD_AUDIO

Si el permiso no es concedido, la aplicación informa al usuario y no inicia la grabación.

---

## Gestión de estado

El estado de la aplicación se gestiona mediante data classes independientes para:

- Estado de grabación
- Estado de reproducción

Esto permite evitar conflictos como la grabación y reproducción simultáneas y facilita el control del ciclo de vida de los recursos.

---

## Liberación de recursos

Para evitar fugas de memoria y errores de uso del hardware multimedia:

- Se utiliza DisposableEffect en Jetpack Compose
- Se liberan explícitamente MediaRecorder y MediaPlayer mediante release()

---

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- Coroutines
- MediaRecorder
- MediaPlayer
