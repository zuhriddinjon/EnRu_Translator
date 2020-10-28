package com.example.enru_translator.ui.adapter.listener

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.enru_translator.R
import com.example.enru_translator.data.local.DBHelperImpl
import com.example.enru_translator.data.local.DBbuilder
import com.example.enru_translator.ui.history.listener.ItemTouchHelperAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MySwipeCallback(
    private val adapter: ItemTouchHelperAdapter,
    private val ctx: Context
) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

    private val dbHelper = DBHelperImpl(DBbuilder.getInstance(ctx))
    private val backgroundLeft: Drawable = ColorDrawable(Color.GREEN)
    private val backgroundRigt: Drawable = ColorDrawable(Color.RED)

    private val iconArchive = ctx.getDrawable(R.drawable.ic_archive)
    private val iconDelete = ctx.getDrawable(R.drawable.ic_delete)


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        val word = adapter.onItemSwiped(pos)
        showSnackbar(viewHolder.itemView)
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.delete(word)
            Log.d("onSwiped: ", "${dbHelper.getAll().size}")
        }

    }

    private fun showSnackbar(view: View) {
        val snackbar = Snackbar.make(view, "Undo item", Snackbar.LENGTH_SHORT)
        snackbar.setAction("undo", View.OnClickListener {
            val word = adapter.onClickUndo()
            CoroutineScope(Dispatchers.IO).launch {
                dbHelper.insert(word)
                Log.d("onSwiped: ", "${dbHelper.getAll().size}")
            }

        })

        snackbar.show()
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val currentView = viewHolder.itemView
        val iconMargin: Int = (currentView.height - iconArchive!!.intrinsicHeight) / 2
        val iconTop = currentView.top + iconMargin
        val iconBottom: Int = iconTop + iconArchive.intrinsicHeight
        if (dX > 0) {
            val iconLeft = currentView.left + iconMargin
            val iconRight: Int = iconLeft + iconArchive.intrinsicWidth
            iconArchive.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            backgroundLeft.setBounds(
                currentView.left,
                currentView.top,
                recyclerView.left + dX.toInt(),
                currentView.bottom
            )
            backgroundLeft.draw(c)
            iconArchive.draw(c)
        } else if (dX < 0) {
            val iconRight = currentView.right - iconMargin
            val iconLeft: Int = iconRight - iconDelete!!.intrinsicWidth
            iconDelete.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            backgroundRigt.setBounds(
                currentView.right + dX.toInt(),
                currentView.top,
                recyclerView.right,
                currentView.bottom
            )
            backgroundRigt.draw(c)
            iconDelete.draw(c)
        } else {
            backgroundLeft.setBounds(0, 0, 0, 0)
            iconArchive.setBounds(0, 0, 0, 0)
            backgroundRigt.setBounds(0, 0, 0, 0)
            iconDelete!!.setBounds(0, 0, 0, 0)
        }
    }


}