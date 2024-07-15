package com.bignerdranch.android.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.UUID


class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<Crime?> =
        crimeIdLiveData.switchMap{ crimeId ->
            crimeRepository.getCrime(crimeId)
        }

    /**
     * Функция сохранения преступления в списке БД
     */
    fun saveCrime(crime: Crime) {
        crimeRepository.updateCrime(crime)
        }

    /**
     * Функция загрузки преступления по id
     */
    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }
}
