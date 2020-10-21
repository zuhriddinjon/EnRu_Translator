package com.example.enru_translator.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.enru_translator.R
import com.example.enru_translator.data.local.DatabaseBuilder
import com.example.enru_translator.data.local.DatabaseHelperImpl
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.ui.adapter.WordAdapter
import com.example.enru_translator.utils.Status
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private lateinit var viewModel: GalleryViewModel
    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(
                this, GalleryVMFactory(
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
                )
            ).get(GalleryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupUI()
//        setupViewModel()
        setupObserver()
    }

//    private fun setupUI() {
//
//        rv_gallery.layoutManager = LinearLayoutManager(requireContext())
//        adapter =
//            WordAdapter(
//                arrayListOf()
//            )
//        rv_gallery.addItemDecoration(
//            DividerItemDecoration(
//                rv_gallery.context,
//                (rv_gallery.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        rv_gallery.adapter = adapter
//    }

    private fun setupObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    pb_gallery.visibility = View.GONE
                    it.data?.let {
                        adapter = WordAdapter(it as ArrayList<Word>)
                        rv_gallery.adapter = adapter
                    }
                    rv_gallery.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    pb_gallery.visibility = View.VISIBLE
                    rv_gallery.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    pb_gallery.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(list: List<Word>) {
        adapter.addData(list)
        adapter.notifyItemRangeInserted(0, list.size)
    }

}