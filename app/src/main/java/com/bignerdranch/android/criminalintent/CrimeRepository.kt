package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.CrimeDatabase
import java.util.UUID
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    /**
     * Создаём реализация класса CrimeDatabase
     */
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

    //Создаёт новый поток (гарантирует последовательное выполение задач не в основном потоке, а в созданном)
    private val executor = Executors.newSingleThreadExecutor()

    // Реализация функций интерфейса CrimeDao
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    /**
     * Реализация функции интерфейса CrimeDao по обновлению данных в строке преступлений.
     */
    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    /**
     * Реализация функции интерфейса CrimeDao по добавлению преступлений в БД.
     */
    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
            }
        }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        //Функция инициализирующая новый экземпляр
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        //Функция обеспечивает доступ к созданному экземпляру
        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}
