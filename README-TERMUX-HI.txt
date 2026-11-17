POCKET PC — Phase 1 starter
===========================

IMPORTANT:
- This ZIP is the Phase 1 Android app shell + Windows-like desktop preview.
- It is NOT Windows, Wine, Box64, or a working EXE emulator yet.
- Roblox Studio compatibility is not guaranteed; it will be a later research/test phase.
- No Android build can be guaranteed error-free on every device. Follow steps exactly and share the complete error text if a command fails.

PHONE:
Redmi 12 5G, 6 GB RAM.

BUILD ROUTE:
Build inside Ubuntu running in Termux (PRoot). Android SDK/Gradle setup on a phone is demanding and may need several GB free storage.

STEP 1 — Install Termux
Install Termux from its official F-Droid or GitHub release source. Do not mix Termux builds from different sources.

STEP 2 — Update Termux
pkg update && pkg upgrade -y
pkg install proot-distro wget unzip git -y

STEP 3 — Install Ubuntu
proot-distro install ubuntu
proot-distro login ubuntu

STEP 4 — Ubuntu packages
apt update && apt upgrade -y
apt install -y wget unzip git curl ca-certificates openjdk-17-jdk

Check Java:
java -version

STEP 5 — Install Gradle 8.9
cd /root
wget https://services.gradle.org/distributions/gradle-8.9-bin.zip
unzip gradle-8.9-bin.zip
export PATH=/root/gradle-8.9/bin:$PATH
gradle --version

STEP 6 — Install Android command-line tools
Create SDK folder:
mkdir -p /root/android-sdk/cmdline-tools
cd /root

Download the current Linux command-line tools ZIP from Google's official Android Studio download page:
https://developer.android.com/studio#command-line-tools-only

Upload/copy that ZIP into /root, then:
unzip commandlinetools-linux-*.zip -d /root/android-sdk/cmdline-tools
mkdir -p /root/android-sdk/cmdline-tools/latest
# If unzip created a 'cmdline-tools' folder, move its contents:
mv /root/android-sdk/cmdline-tools/cmdline-tools/* /root/android-sdk/cmdline-tools/latest/

Set environment for this terminal session:
export ANDROID_HOME=/root/android-sdk
export ANDROID_SDK_ROOT=/root/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

Install SDK packages:
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.0"

NOTE: If Google changes the command-line tools ZIP filename/layout, inspect the extracted folder and adjust the move command rather than running it blindly.

STEP 7 — Copy and unzip this project
Copy POCKET-PC.zip to Ubuntu /root (for example through Android shared storage).
If using shared storage, in Termux first run:
termux-setup-storage
Then in Ubuntu, shared storage is commonly available at:
 /storage/emulated/0/Download/
If it is not visible in PRoot, copy the ZIP using a file manager to a PRoot-accessible folder or use Termux's shared-storage path.

Example (adjust path if needed):
cd /root
unzip /storage/emulated/0/Download/POCKET-PC.zip
cd /root/POCKET-PC

STEP 8 — Build debug APK
export ANDROID_HOME=/root/android-sdk
export ANDROID_SDK_ROOT=/root/android-sdk
export PATH=$PATH:/root/gradle-8.9/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

gradle assembleDebug

If successful, APK will be at:
app/build/outputs/apk/debug/app-debug.apk

STEP 9 — Install
Copy the APK to Downloads and install it using Android's package installer. Allow installation from the file manager when Android asks.

TROUBLESHOOTING:
- "SDK location not found": confirm ANDROID_HOME and ANDROID_SDK_ROOT are exported in the same terminal session.
- "Java 17 required": run java -version; ensure openjdk-17-jdk is installed and JAVA_HOME points to its actual path.
- "licenses not accepted": run yes | sdkmanager --licenses again.
- "platform android-35 not found": run sdkmanager "platforms;android-35".
- "AAPT2 / exec format error": Android SDK build tools may not run correctly in the phone's CPU/PRoot environment. This is a known risk of building Android apps directly on ARM phones; do not assume the project source is broken. We may need a different build route.
- "Could not resolve plugin": check internet connection, date/time, and retry Gradle after network is stable.

PHASE 1 SCOPE:
- Full-screen Android app shell.
- Windows-inspired desktop preview.
- Start panel, taskbar, clock, desktop shortcuts.
- Placeholder messages for features not yet implemented.

NEXT PHASES:
2. Native touch mouse/keyboard and file manager.
3. Linux rootfs/runtime investigation.
4. Wine + Box64 integration.
5. Vulkan/DXVK investigation.
6. Test individual Windows applications.
7. Roblox Studio feasibility test.

Never download random "Windows emulator APKs" or unknown EXE/rootfs packages. Use trusted project releases and verify checksums where available.
