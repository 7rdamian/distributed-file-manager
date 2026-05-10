# TrueNAS Cloud File Explorer

A full-stack distributed cloud storage management system. This project consists of a custom Spring Boot REST API backend that interfaces with storage systems (like TrueNAS), and a modern native Android application built with Kotlin and Jetpack Compose for seamless user access.

## Key Features

* **Full-Stack Architecture:** Clean separation of concerns between the Java backend API and the Kotlin mobile client.
* **Secure Authentication:** Integrates **Firebase Authentication**. The Android client retrieves a JWT token which is then strictly validated by a custom Spring Security filter chain on the backend before allowing file access.
* **Complete File Management:** Users can view, add, edit, and delete files stored on the distributed cloud directly from their mobile device.
* **Reactive Mobile UI:** The frontend is built entirely using Jetpack Compose, utilizing the MVVM (Model-View-ViewModel) pattern and Retrofit for asynchronous network calls.
* **Robust Backend:** A Java Spring Boot application utilizing Spring Web and Firebase Admin SDK to handle secure API routing.

## Tech Stack

* **Frontend (Mobile):** Kotlin, Android SDK, Jetpack Compose, Retrofit, ViewModel
* **Backend:** Java, Spring Boot, Spring Security, Maven
* **Authentication:** Firebase Auth & Firebase Admin SDK
* **Storage:** TrueNAS / Distributed File System

## How to Run

### 1. Backend Setup (Spring Boot)
1. Navigate to the `backend/demo` directory.
2. You will need to update the `application.properties` file with a valid local folder path or NFS mount.
3. Ensure you have your Firebase Admin Service Account JSON key configured in the project.
4. Run the backend using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
5. The API will start on localhost:8080.

### 2. Frontend Setup (Android)
1. Open the android_app folder in Android Studio.
2. Update the BASE_URL in RetrofitClient.kt to match your backend's IP address (use 10.0.2.2 if testing on an Android Emulator).
3. Connect your Firebase project via the google-services.json file.
4. Build and run the app on an emulator or physical device.

## Screenshots
<div align="center">
  <img src="https://github.com/user-attachments/assets/c9e25549-b7af-40e3-83f3-95b428ebe6e9" width="250" />
  <img src="https://github.com/user-attachments/assets/f994db1f-9be2-4358-9145-c1b0f836a3ee" width="250" />
  <img src="https://github.com/user-attachments/assets/3208788f-9a68-45c7-bc3a-a455e6fe1c1f" width="250" />
</div>

## What I Learned

This project bridged the gap between frontend and backend development. I learned how to architect a RESTful API using Spring Boot, secure endpoints using custom JWT token filters with Spring Security, and access those APIs asynchronously on a mobile device using Retrofit and Kotlin Coroutines.
