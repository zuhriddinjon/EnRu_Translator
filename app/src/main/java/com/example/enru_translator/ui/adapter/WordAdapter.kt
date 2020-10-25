package com.example.enru_translator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enru_translator.R
import com.example.enru_translator.data.local.entity.Word
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.*

class WordAdapter(
    private val words: ArrayList<Word>
) : RecyclerView.Adapter<WordAdapter.DataViewHolder>() {
    private var lastRemovedPos: Int = 0
    private var lastRemovedWord: Word? = null

    class DataViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(word: Word) {
            tv_eng.text = word.wordEn
            tv_ru.text = word.wordRu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(words[position])

    fun addData(it: List<Word>) {
        words.addAll(it)
    }

    fun removedItemByPos(pos: Int): Word {
//        val word = words.removeAt(pos)
        lastRemovedWord = words.removeAt(pos)
        lastRemovedPos = pos
//        notifyDataSetChanged()
        notifyItemRemoved(pos)
        return lastRemovedWord!!
    }

    fun undoLastRemovedItem(): Word {
        words.add(lastRemovedPos, lastRemovedWord!!)
        notifyItemInserted(lastRemovedPos)
        return lastRemovedWord!!
    }

}