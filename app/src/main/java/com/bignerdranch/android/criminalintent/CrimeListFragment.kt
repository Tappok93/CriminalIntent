package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeListBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    lateinit var binding: FragmentCrimeListBinding
    lateinit var bindingItemCrime: ListItemCrimeBinding
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    /**
     * Связываем фрагмент с классом CrimeListViewModel
     */
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Раздуваем фрагмент и передаём для работы LayoutManager
        binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeRecyclerView = binding.crimeRecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        Toast.makeText(requireContext(), "Activiti", Toast.LENGTH_SHORT).show()

        updateUI()

        return view
    }

    /**
     * Создаём ViewHolder и помещаем в него TextView из list_item_crime
     */
    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView = bindingItemCrime.crimeTitle
        private val dateTextView = bindingItemCrime.crimeDate

        fun setHolder(crime: Crime) {
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        //Функция создаёт представление, добавляет в ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        //Возвращает количество преступлений в списке.
        override fun getItemCount() = crimes.size

        //Функция заполняет переданный холдер преступлениями.
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                setHolder(crime)
            }
        }
    }

    /**
     * Создаём адаптер и подключаем к RecycleView
     */
    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}