package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.criminalintent.Crime

//Аннотируем класс который будет представлять базу данных
@Database(entities = [Crime::class], version = 1)
//Аннотируем, что мы будем использовать функции преобразованиея из класса CrimeTypeConverters
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}
