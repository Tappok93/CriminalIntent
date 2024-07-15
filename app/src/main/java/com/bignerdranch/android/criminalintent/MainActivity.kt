package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.criminalintent.databinding.ActivityMainBinding
import java.util.UUID

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),
CrimeListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        //Добавляем фрагмент на экран, при условии, что он не null
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID) {
        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, fragment)
        //Добавляем возможность возврата из фрагмента обратно в список преступлений
        .addToBackStack(null)
        .commit()
    }
}