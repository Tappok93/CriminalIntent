package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding

class CrimeFragment : Fragment() {

    private lateinit var bindingCrimeFragment: FragmentCrimeBinding
    private lateinit var crime: Crime

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
}


