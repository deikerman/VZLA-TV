VzlaTV (Android TV)
=====================

Notas rápidas
- El proyecto muestra una cuadrícula de canales de 3 columnas en Android TV. Al seleccionar un canal, se abre el reproductor.
- Reproducción: la aplicación intenta primero procesar un archivo M3U (si se proporciona) y reproducir las entradas; si ocurre un error de reproducción, recurre automáticamente a la `primaryStreamUrl` del canal.

Cómo probarlo localmente
1. Abre el proyecto en Android Studio.
2. Asegúrate de tener disponibles el SDK de Android y un emulador (o un dispositivo Android TV físico).
3. Sincroniza Gradle (el módulo de la aplicación ahora utiliza ExoPlayer, OkHttp, Coroutines y Glide).
4. Ejecuta la aplicación en un dispositivo Android TV o en un emulador. La pantalla principal muestra los canales en una cuadrícula de 3 columnas.
