package com.allow.destinationsapp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Manages all value in a list displayed using [RecyclerView]. Use with [ItemAdapter].
 *
 * [RecyclerListAdapter] can be initialized and used without subclassing. However, subclasses
 * can be created to define custom methods for different lists.
 *
 * android layout ids are used to distinguish between item types. The limitation is that,
 * when two [ItemAdapter]s with the same inflated layout are used in the same list, the first
 * match is used while the others are ignored
 */
open class RecyclerListAdapter(
        var items: List<AnyItemAdapter> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    final override fun getItemCount(): Int = items.size

    final override fun getItemViewType(position: Int): Int = items[position].layoutId

    final override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return items.first { it.layoutId == layoutId }.onCreateViewHolder(itemView)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].bindViewHolder(holder)
    }

}
