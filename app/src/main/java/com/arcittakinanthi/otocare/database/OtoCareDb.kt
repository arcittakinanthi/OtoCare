package com.arcittakinanthi.otocare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arcittakinanthi.otocare.model.ServiceRecord

@Database(
    entities = [ServiceRecord::class],
    version = 1,
    exportSchema = false
)
abstract class OtoCareDb : RoomDatabase() {

    abstract val dao: ServiceDao

    companion object {
        @Volatile
        private var INSTANCE: OtoCareDb? = null

        fun getInstance(context: Context): OtoCareDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OtoCareDb::class.java,
                        "otocare.db"
                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}