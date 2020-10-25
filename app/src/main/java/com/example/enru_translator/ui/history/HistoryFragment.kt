package com.example.enru_translator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.enru_translator.R
import com.example.enru_translator.data.local.DBHelperImpl
import com.example.enru_translator.data.local.DBbuilder
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.ui.adapter.WordAdapter
import com.example.enru_translator.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: WordAdapter
    private lateinit var dbHelper: IDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        setupSwipeListener()
    }

    private fun setupSwipeListener() {
        val callback = /*MySwipeCallback(adapter, requireContext().applicationContext)*/
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    val word = adapter.removedItemByPos(pos)
                    showSnackbar()
                lifecycleScope.launch { dbHelper.delete(word) }
//                CoroutineScope(Dispatchers.IO).launch { dbHelper.delete(word) }

                }

                private fun showSnackbar() {
                    val snackbar = Snackbar.make(view!!, "Undo item", Snackbar.LENGTH_SHORT)
                    snackbar.setAction("undo", View.OnClickListener {
                        val word = adapter.undoLastRemovedItem()
                        lifecycleScope.launch { dbHelper.insert(word) }
                    })
                    snackbar.show()
                }

            }

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
