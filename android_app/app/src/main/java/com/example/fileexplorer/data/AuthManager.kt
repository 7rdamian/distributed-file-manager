package com.example.fileexplorer.data

import com.google.firebase.auth.FirebaseAuth

object AuthManager {
    fun isUserLoggedIn(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }
}