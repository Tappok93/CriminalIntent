package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Crime
import java.util.UUID

@Dao
interface CrimeDao {
    /**
     * Функции которые позволяют получить список всех преступрений и вытащить одно по id
     */
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    //Аннотация Query для извлечения данных из БД, но не вставки
    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

    //Аннотация Update для обновления данных в строке
    @Update
    fun updateCrime(crime: Crime)

    //Аннотация Insert для добавления преступления в таблицу БД
    @Insert
    fun addCrime(crime: Crime)
}