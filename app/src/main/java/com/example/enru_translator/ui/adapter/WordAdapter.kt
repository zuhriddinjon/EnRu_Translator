package com.example.enru_translator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enru_translator.R
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.ui.history.listener.swipe.ItemTouchHelperAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.*
import java.util.*

class WordAdapter(
    private val words: ArrayList<Word>
) : RecyclerView.Adapter<WordAdapter.DataViewHolder>(), ItemTouchHelperAdapter {

    private var lastRemovedPos: Int = 0
    private var lastRemovedWord: Word? = null

    class DataViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(word: Word) {
            tv_eng.text = word.wordEn
            tv_ru.text = word.wordRu
//            containerView.setOnClickListener { }
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

    fun getWord(position: Int) = words[position]

    fun addData(it: List<Word>) {
        words.addAll(it)
    }


    /*ItemTouchHelperAdapter*/
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(words, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(words, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwiped(position: Int): Word {
        lastRemovedPos = position
        lastRemovedWord = words.removeAt(position)
        notifyItemRemoved(position)
        return lastRemovedWord!!
    }

    override fun onClickUndo(): Word {
        words.add(lastRemovedPos, lastRemovedWord!!)
        notifyItemInserted(lastRemovedPos)
        return lastRemovedWord!!
    }

}