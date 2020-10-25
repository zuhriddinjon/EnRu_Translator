package com.example.enru_translator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.R
import com.example.enru_translator.data.local.DBHelperImpl
import com.example.enru_translator.data.local.DBbuilder
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.ui.adapter.WordAdapter
import com.example.enru_translator.utils.Status
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(
                this, HistoryVMFactory(
                    DBHelperImpl(DBbuilder.getInstance(requireContext()))
                )
            ).get(HistoryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    pb_history.visibility = View.GONE
                    it.data?.let {
                        adapter = WordAdapter(it as ArrayList<Word>)
                        rv_history.adapter = adapter
                    }
                    rv_history.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    pb_history.visibility = View.VISIBLE
                    rv_history.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    pb_history.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


}