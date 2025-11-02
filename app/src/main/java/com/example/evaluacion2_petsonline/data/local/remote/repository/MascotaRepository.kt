package com.example.evaluacion2_petsonline.data.local.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.evaluacion2_petsonline.data.local.model.Mascota
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.mascotaDataStore by preferencesDataStore("mascotas_store")

class MascotaRepository(private val context: Context) {
    private val gson = Gson()
    private val KEY = stringPreferencesKey("mascotas_list")

    fun getMascotas(): Flow<List<Mascota>> {
        return context.mascotaDataStore.data.map { prefs ->
            val json = prefs[KEY] ?: "[]"
            val type = object : TypeToken<List<Mascota>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun saveMascota(mascota: Mascota) {
        val list = getCurrentMascotas().toMutableList()
        list.add(mascota)
        saveList(list)
    }

    suspend fun deleteMascota(id: Int) {
        val list = getCurrentMascotas().filter { it.id != id }
        saveList(list)
    }

    private suspend fun saveList(list: List<Mascota>) {
        val json = gson.toJson(list)
        context.mascotaDataStore.edit { it[KEY] = json }
    }

    private suspend fun getCurrentMascotas(): List<Mascota> {
        val prefs = context.mascotaDataStore.data.map { it[KEY] ?: "[]" }.first()
        val type = object : TypeToken<List<Mascota>>() {}.type
        return gson.fromJson(prefs, type)
    }
}
