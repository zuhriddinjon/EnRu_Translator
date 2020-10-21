package com.example.enru_translator.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.R
import com.example.enru_translator.data.api.ApiHelperImpl
import com.example.enru_translator.data.api.RetrofitBuilder
import com.example.enru_translator.data.local.DatabaseBuilder
import com.example.enru_translator.data.local.DatabaseHelperImpl
import com.example.enru_translator.data.repo.IRepo
import com.example.enru_translator.data.repo.RepoImpl
import com.example.enru_translator.ui.adapter.WordAdapter
import com.example.enru_translator.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var repo: IRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_search.setOnClickListener {
            val text = et_search.text.toString()
            setupUI()
            setupViewModel(text)
            setupObserver()
//            setupObserver(text)
        }
    }

//    private fun setupObserver(text: String) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            repo.search(text).observe(
//                viewLifecycleOwner, Observer {
//                    when (it.status) {
//                        Status.SUCCESS -> {
//                            pb.visibility = View.GONE
//                            it.data?.let { /*word -> renderList(word)*/
//
//                                tv_tr.text = it.translates
//                                Log.d("onCreate:2 ", "${tv_tr.text}")
//                            }
//                            tv_tr.visibility = View.VISIBLE
//                        }
//                        Status.LOADING -> {
//                            pb.visibility = View.VISIBLE
//                            tv_tr.visibility = View.GONE
//                        }
//                        Status.ERROR -> {
//                            //Handle Error
//                            pb.visibility = View.GONE
//                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    }
//                }
//            )
//        }
//    }


    private fun setupUI() {
        adapter = WordAdapter(arrayListOf())
        rv.adapter = adapter
    }

    private fun setupViewModel(text: String) {
        viewModel = ViewModelProvider(
            this,
            HomeVMFactory(
                ApiHelperImpl(RetrofitBuilder.apiService, text),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()), text)
            )
        ).get(HomeViewModel::class.java)

        repo = RepoImpl(
            ApiHelperImpl(RetrofitBuilder.apiService, text),
            DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()), text)
        )
    }

    private fun setupObserver() {

        viewModel.getWord().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    pb.visibility = View.GONE
                    it.data?.let { /*word -> renderList(word)*/
                        tv_tr.text = it.translates
                        Log.d("onCreate:2 ", "${tv_tr.text}")
                    }
                    tv_tr.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    pb.visibility = View.VISIBLE
                    tv_tr.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    pb.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

//    private fun renderList(words: Word) {
//        adapter.addData(words)
//        adapter.notifyDataSetChanged()
//    }


}