package com.arcittakinanthi.otocare.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcittakinanthi.otocare.database.OtoCareDb
import com.arcittakinanthi.otocare.screen.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = OtoCareDb.getInstance(context).dao

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}