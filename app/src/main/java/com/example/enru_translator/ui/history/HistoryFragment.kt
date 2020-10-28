package com.example.enru_translator.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.enru_translator.R
import com.example.enru_translator.data.local.DBHelperImpl
import com.example.enru_translator.data.local.DBbuilder
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.ui.adapter.WordAdapter
import com.example.enru_translator.ui.adapter.listener.MySwipeCallback
import com.example.enru_translator.ui.history.listener.click.*
import com.example.enru_translator.utils.Status
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.item_dialog.*


class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private var adapter: WordAdapter? = null
    private lateinit var dbHelper: IDBHelper
    private var list: ArrayList<Word>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        adapter = WordAdapter(arrayListOf())
        dbHelper = DBHelperImpl(DBbuilder.getInstance(requireContext()))
        viewModel =
            ViewModelProvider(
                this, HistoryVMFactory(
                    dbHelper
                )
            ).get(HistoryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()

        itemClicked()
    }

    private fun itemClicked() {
        // Usage:
        rv_history.addOnItemClickListener(object : ItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val texten = adapter!!.getWord(position).wordEn
                val textru = adapter!!.getWord(position).wordRu
               val fragment=SimpleDialog.newInstance(texten,textru)
                fragment.show(parentFragmentManager, SimpleDialog.TAG)

            }
        })
    }


    private fun setupSwipeListener() {
        val callback = MySwipeCallback(adapter!!, requireContext())
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rv_history)
    }

    private fun setupObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    pb_history.visibility = View.GONE
                    it.data?.let {
                        adapter = WordAdapter(it as ArrayList<Word>)
                        list = it
                        rv_history.adapter = adapter
                        Log.d("setupObserver: ", "yana")
                        setupSwipeListener()
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
