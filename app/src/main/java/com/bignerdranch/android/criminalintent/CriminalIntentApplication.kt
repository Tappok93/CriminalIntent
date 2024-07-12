package com.bignerdranch.android.criminalintent

import android.app.Application

class CriminalIntentApplication : Application() {

    /**
     * Инициализируем класс CrimeRepository при запуске приложения
     */
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}