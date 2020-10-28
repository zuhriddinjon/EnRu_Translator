package com.example.enru_translator.ui.history.listener.swipe

import com.example.enru_translator.data.local.entity.Word

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemSwiped(position: Int): Word

    fun onClickUndo(): Word

}
