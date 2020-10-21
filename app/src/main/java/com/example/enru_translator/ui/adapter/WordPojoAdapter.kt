package com.example.enru_translator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enru_translator.R
import com.example.enru_translator.data.local.entity.Word
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_wordpojo.*

class WordPojoAdapter(
    private val list: ArrayList<Word>
) : RecyclerView.Adapter<WordPojoAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(list[position])

    fun addData(words: List<Word>) {
        list.clear()
        list.addAll(words)
    }


    class DataViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(word: Word) {
            tv_trans.text = word.translates
        }
    }

}