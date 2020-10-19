package com.example.enru_translator.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.R
import com.example.enru_translator.data.db.WordDBProvider
import com.example.enru_translator.data.model.Word
import com.example.enru_translator.data.net.ApiProvider
import com.example.enru_translator.data.repo.RepoImpl
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private val api by lazy(LazyThreadSafetyMode.NONE) { ApiProvider.instance() }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_home.setOnClickListener {
            search()
        }
    }

    private fun search() {
        val text = text_home.text.toString()
        var word: Word? = null
        CoroutineScope(Dispatchers.IO).launch {
            word = searchWord(requireContext(), text)
            withContext(Dispatchers.Main) {
                if (word != null)
                    translate_home.text = word!!.wordRu
                else
                    translate_home.text = "null"
            }
        }
    }

    suspend fun searchWord(ctx: Context, it: String): Word? {
        val dao = WordDBProvider.instance(ctx)
        val repo = RepoImpl(api, dao.wordDao())
        return repo.search(it).value
    }
}