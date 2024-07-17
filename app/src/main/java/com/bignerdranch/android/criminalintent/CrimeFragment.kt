package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0

class CrimeFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var bindingCrimeFragment: FragmentCrimeBinding
    private lateinit var crime: Crime

    /**
     * Загрузка фрагмента в CrimeDetailViewModel
     */
    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    /**
     *Создаём фрагмент
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()

        //Получение аргуменетов
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG, "args bundle crime ID: $crimeId")
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
        return bindingCrimeFragment.root
    }

    /**
     * Наблюдаем за изменением данных в LiveData
     */
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

            //Этот метод вызывается перед изменением текста в EditText, можно записать условие проверки вводимого текста.
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //Это пространство оставлено пустым специально по заданию
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
            //Вызывается после инменения текста. Функция показывает все что мы введем в EditText, но её параметр редактируемый
            override fun afterTextChanged(sequence: Editable?) {
                // И это
            }
        }
        //Прикрепляем TextWatcher к EditText
        bindingCrimeFragment.crimeTitle.addTextChangedListener(titleWatcher)

        //Слушатель при нажатии на кнопку DateBTN который открывает диалоговое окно выбора даты
        bindingCrimeFragment.crimeDate.setOnClickListener {
            DatePickerFragment.newInstance(crime.date).apply {
                //Назначаем целевой фрагмент
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
    }

    /**
     * Обновление пользовательского интерфейса !!!!!! Возможно тут ошибка!!!!
     */
    private fun updateUI() {
        bindingCrimeFragment.crimeTitle.setText(crime.title)
        bindingCrimeFragment.crimeDate.text = crime.date.toString()

        bindingCrimeFragment.crimeSolved.apply {
            bindingCrimeFragment.crimeSolved.isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    companion object {
        /**
         * Присоединение агрументов к фрагменту
         */
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }


    /**
     * При переходе в onStop (когда фрагмент будет вне поля зрения пользователя), сохраняет изменения данных в БД
     */
    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    /**
     * Реализуем функция интерфейса Callbacks класса DatePickerFragment
     */
    override fun onDateSelected(date: Date) {
        crime.date = date
        updateUI()
    }

}


