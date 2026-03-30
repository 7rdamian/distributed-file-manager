# Android Project

## Student Information
**Name:** Damian Rosu  
**Student ID:** 1325
**Course:** Android Development  

## Project Title
TrueNAS File Explorer App

## Description
This is my final project for the Android Development course[cite: 8, 12]. It is a client-server application that lets you connect to a private TrueNAS server to manage your files remotely[cite: 72]. Instead of just using a local database, the Android app connects to a custom Java Spring Boot server, which then reads and writes files directly to a physical NAS via an NFS mount. 

## Features
- **Firebase Login:** Users must log in using Firebase Authentication before accessing any files. The token is verified by the Spring Boot server.
- **Browse and Manage:** View the list of files on the server, create new folders, and delete files.
- **In-App Text Editor:** Tap on any `.txt` file to open a custom Jetpack Compose screen where you can edit and save the text directly to the server.
- **Open PDFs and Images:** If you tap a complex file (like a PDF), the app downloads it to the device's cache and uses Android's `FileProvider` to open it in the phone's default viewer app.
- **Live Sync:** Because the server uses an NFS mount, if you drop a file into the folder on your laptop, it shows up in the app immediately.

## Screenshots
![Screenshot_2026-03-30-06-28-08-158_com example fileexplorer](https://github.com/user-attachments/assets/c9e25549-b7af-40e3-83f3-95b428ebe6e9)
![Screenshot_2026-03-30-06-28-33-547_com example fileexplorer](https://github.com/user-attachments/assets/f994db1f-9be2-4358-9145-c1b0f836a3ee)
![Screenshot_2026-03-30-06-29-01-321_com example fileexplorer](https://github.com/user-attachments/assets/3208788f-9a68-45c7-bc3a-a455e6fe1c1f)
![Screenshot_2026-03-30-06-29-14-934_com example fileexplorer](https://github.com/user-attachments/assets/8850c31a-76a6-4ee7-8a8c-160f141ebce2)

## Technologies Used
- **Android Client:** Kotlin, Jetpack Compose, MVVM Architecture
- **Networking:** Retrofit, OkHttp (with custom Interceptors for Auth tokens)
- **Backend Server:** Java, Spring Boot, Spring Security 
- **Cloud Service:** Firebase Authentication
- **Infrastructure:** TrueNAS, Ubuntu, NFS, Git for version control 

## How to Run
1. **Clone the repository** to your local machine.
2. **Start the Backend:** - Open the `backend` folder in your Java IDE.
   - You will need to update the `application.properties` file with a valid local folder path or NFS mount.
   - Run the Spring Boot application.
3. **Configure the App:**
   - Open the Android project in Android Studio.
   - Go to `RetrofitClient.kt` and change the `BASE_URL` to match the local IP address of the machine running the Spring Boot server.
4. **Run:** Build and run the app on an Android emulator or physical device.
