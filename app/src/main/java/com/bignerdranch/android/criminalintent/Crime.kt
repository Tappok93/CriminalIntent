package com.bignerdranch.android.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

//Аннотируем класс  для создания таблицы базы данных
@Entity
data class Crime(
    //Аннотируем первичный ключ для поля id
    @PrimaryKey
    var id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
)

