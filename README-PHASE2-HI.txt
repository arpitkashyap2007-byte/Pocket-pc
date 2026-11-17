POCKET PC — PHASE 2
===================

This phase adds:
- Android document picker entry point in File Explorer.
- A basic touch trackpad with an on-screen pointer.
- Updated desktop/start menu/taskbar UI.
- GitHub Actions APK build workflow.

Important limitations:
- This is still a desktop-shell prototype, NOT a Windows emulator.
- File Explorer invokes Android's system document picker; it does not yet browse a Windows filesystem.
- The pointer is a visual UI pointer only; it does not inject real mouse events into Android or Windows apps.
- No EXE execution, Wine, Box64, DXVK, Linux rootfs, or Roblox Studio support is included yet.
- The selected file name is displayed; this phase does not read or execute file contents.

Build through GitHub Actions (recommended for ARM phone):
1. Replace the repository contents with this ZIP's contents, including the hidden .github folder.
2. Commit and push to main.
3. Open Actions and wait for Build Pocket PC APK to finish.
4. Download the Pocket-PC-APK artifact and extract app-debug.apk.
