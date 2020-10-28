package com.example.enru_translator.ui.translator

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.R
import com.example.enru_translator.common.TextSpeechListener
import com.example.enru_translator.data.api.ApiHelperImpl
import com.example.enru_translator.data.api.RetrofitBuilder
import com.example.enru_translator.data.local.DBHelperImpl
import com.example.enru_translator.data.local.DBbuilder
import com.example.enru_translator.utils.Status
import kotlinx.android.synthetic.main.fragment_translator.*

class TranslatorFragment : Fragment() {

    private lateinit var viewModel: TranslatorViewModel
    private var tsl: TextSpeechListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_translator, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tsl = TextSpeechListener(requireContext())

        setupViewModel()
        setupObserver()

        btn_search.setOnClickListener { search() }
        et_search.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                search()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        btn_speak_en.setOnClickListener { tsl!!.speakFromText(tv_en.text) }
        btn_speak_ru.setOnClickListener { tsl!!.speakFromText(tv_rus.text) }
    }

    private fun search() {
        val text = et_search.text
        if (text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "enter searched text", Toast.LENGTH_SHORT).show()
//                Snackbar.make(view, "enter searched text", Snackbar.LENGTH_SHORT).show()
        } else {
            viewModel.fetchWord(text.toString())
        }

    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            TranslatorVMFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DBHelperImpl(DBbuilder.getInstance(requireContext()))
            )
        ).get(TranslatorViewModel::class.java)

    }

    private fun setupObserver() {
        viewModel.getWord().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    pb.visibility = View.GONE
                    it.data?.let {
                        tv_en.text = it.wordEn
                        tv_rus.text = it.wordRu
                    }
                    ll.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    pb.visibility = View.VISIBLE
                    ll.visibility = View.GONE
                }
                Status.ERROR -> {
                    pb.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
//                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }

        })
    }


}