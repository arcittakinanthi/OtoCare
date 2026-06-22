package com.arcittakinanthi.otocare.auth

import com.google.firebase.auth.FirebaseAuth

object AuthManager {

    private val auth = FirebaseAuth.getInstance()

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getUserName(): String {
        return auth.currentUser?.displayName ?: ""
    }

    fun getEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    fun logout() {
        auth.signOut()
    }
}