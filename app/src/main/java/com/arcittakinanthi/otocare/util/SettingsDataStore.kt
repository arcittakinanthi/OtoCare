package com.arcittakinanthi.otocare.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class SettingsDataStore(
    private val context: Context
) {

    companion object {

        private val LAYOUT_KEY =
            booleanPreferencesKey("layout_key")

        private val LOGIN_KEY =
            booleanPreferencesKey("login_key")
    }

    val layoutFlow: Flow<Boolean> =
        context.dataStore.data.map {
            it[LAYOUT_KEY] ?: true
        }

    val loginFlow: Flow<Boolean> =
        context.dataStore.data.map {
            it[LOGIN_KEY] ?: false
        }

    suspend fun saveLayout(
        isList: Boolean
    ) {
        context.dataStore.edit {
            it[LAYOUT_KEY] = isList
        }
    }

    suspend fun saveLogin(
        isLogin: Boolean
    ) {
        context.dataStore.edit {
            it[LOGIN_KEY] = isLogin
        }
    }
}