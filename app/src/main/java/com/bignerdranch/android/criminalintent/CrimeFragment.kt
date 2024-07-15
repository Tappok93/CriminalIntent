package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {

    private lateinit var bindingCrimeFragment: FragmentCrimeBinding
    private lateinit var crime: Crime

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeDetailViewModel::class.java)
    }

    /**
     *Создаём фрагмент
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    /**
     * Создаём представление связанную с фрагментом
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingCrimeFragment = FragmentCrimeBinding.inflate(inflater, container, false)

        // Выключение кнопки
        bindingCrimeFragment.crimeDate.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        return bindingCrimeFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer { crime ->
                crime?.let {
                    this.crime = crime
                    updateUI()
                }
            })
    }

    /**
     * Делаем фрагмент видимым для пользователя
     */
    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            // Этот метод вызывается перед изменением текста в EditText, можно записать условие проверки вводимого текста.
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Это пространство оставлено пустым специально по заданию
            }

            //Вызывается при изменении текста.
            //Функция возвращает строку и использует её для названия заголовка, показывает все что мы введем в EditText
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                crime.title = sequence.toString()
            }

            //Вызывается после инменения текста
            //Функция показывает все что мы введем в EditText, но её параметр редактируемый
            override fun afterTextChanged(sequence: Editable?) {
                // И это
            }
        }
        //Прикрепляем TextWatcher к EditText
        bindingCrimeFragment.crimeTitle.addTextChangedListener(titleWatcher)
    }

    private fun updateUI() {
        bindingCrimeFragment.crimeTitle.setText(crime.title)
        bindingCrimeFragment.crimeDate.text = crime.date.toString()
        bindingCrimeFragment.crimeSolved.isChecked = crime.isSolved
    }

    companion object {

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}


