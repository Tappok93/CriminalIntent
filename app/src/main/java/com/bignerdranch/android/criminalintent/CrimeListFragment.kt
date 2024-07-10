package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeListBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    lateinit var binding: FragmentCrimeListBinding
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    /**
     * Связываем фрагмент с классом CrimeListViewModel
     */
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this)[CrimeListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeRecyclerView = binding.crimeRecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return binding.root
    }

    /**
     * Создаём ViewHolder и помещаем в него TextView из list_item_crime
     */
    private inner class CrimeHolder(view: View) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private val crime = Crime()
        private val bindingItemCrime = ListItemCrimeBinding.bind(view)
        private val titleTextView = bindingItemCrime.crimeTitle
        private val dateTextView = bindingItemCrime.crimeDate
        private val solvedImageView = bindingItemCrime.crimeSolved

        init {
            itemView.setOnClickListener(this)
        }

        fun setHolder(crime: Crime) {
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()

            //Если преступление раскрыто отображается ImageView
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }

        }

        // Слушатель нажатий на View
        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                .show()
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
    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}