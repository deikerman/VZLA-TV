VzlaTV (Android TV)
=====================

Quick notes
- The project shows a 3-column grid of channels on Android TV. Selecting a channel opens the player.
- Channel logos are configured in `app/src/main/java/com/example/vzlatv/ChannelRepository.kt` — add high-quality logo URLs there.
- Playback: app first tries to parse an M3U (if provided) and plays entries; on playback error it automatically falls back to the channel `primaryStreamUrl`.

How to test locally
1. Open the project in Android Studio.
2. Make sure you have an Android SDK and an emulator (or physical Android TV) available.
3. Sync Gradle (the app module now uses ExoPlayer, OkHttp, Coroutines and Glide).
4. Run the app on an Android TV device or emulator. The main screen shows channels in a 3-column grid.

Adding logos
- Edit `ChannelRepository.getChannels()` and set the third parameter (`logoUrl`) to a direct image URL (PNG/JPG/SVG hosted publicly). Example:

  Channel("1", "VTV", "https://example.com/vtv-logo.png", "https://.../live.m3u8", "")

Notes about building here
- I could not run a device/emulator build from this environment because an Android SDK and emulator are not available here. If you want, I can provide Gradle commands to run on your machine.
