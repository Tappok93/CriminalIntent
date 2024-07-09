package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {

    //Создаем переменную хранящую изменяемый список преступлений.
    val crimes = mutableListOf<Crime>()

    /**
     * Сохраняет в созданный список добавленное преступление.
     */
    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
    }
}