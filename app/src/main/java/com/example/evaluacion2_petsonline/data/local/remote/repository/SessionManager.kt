package com.example.evaluacion2_petsonline.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SessionManager(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_session")
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_AVATAR = stringPreferencesKey("avatar_uri") // 🔹 NUEVO
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[KEY_TOKEN] }.first()
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(KEY_TOKEN) }
    }

    suspend fun saveAvatarUri(uri: String) {
        context.dataStore.edit { it[KEY_AVATAR] = uri }
    }

    suspend fun getAvatarUri(): String? {
        return context.dataStore.data.map { it[KEY_AVATAR] }.first()
    }

    suspend fun clearAvatar() {
        context.dataStore.edit { it.remove(KEY_AVATAR) }
    }
}

