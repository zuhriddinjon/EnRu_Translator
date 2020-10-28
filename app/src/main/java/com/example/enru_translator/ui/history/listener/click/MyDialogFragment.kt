package com.example.enru_translator.ui.history.listener.click

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.enru_translator.R
import com.example.enru_translator.common.TextSpeechListener
import kotlinx.android.synthetic.main.item_dialog.view.*


class SimpleDialog : DialogFragment() {

    private var tsl: TextSpeechListener? = null
    private var textEn: String? = null
    private var textRu: String? = null

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): SimpleDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = SimpleDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle("salom")
        return inflater.inflate(R.layout.item_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tsl = TextSpeechListener(requireContext())

        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        textEn = arguments?.getString(KEY_TITLE)
        textRu = arguments?.getString(KEY_SUBTITLE)
        view.tv_dialog_en.text = textEn
        view.tv_dialog_rus.text = textRu
    }

    private fun setupClickListeners(view: View) {
        view.btn_dialog_speak_en.setOnClickListener {
            tsl?.speakFromText(textEn!!)
        }
        view.btn_dialog_speak_ru.setOnClickListener {
            tsl?.speakFromText(textRu!!)
        }
        view.btn_dialog_dismis.setOnClickListener {
            dismiss()
        }
    }

}
