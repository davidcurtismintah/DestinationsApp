package com.allow.destinationsapp.ui.picker

import com.allow.destinationsapp.ui.common.AnyItemAdapter
import com.allow.destinationsapp.ui.common.RecyclerListAdapter

/**
 * Adapter for character list.
 */
class MainListAdapter(items: List<AnyItemAdapter>) : RecyclerListAdapter(items) {

    /**
     * Adds an item to the list
     * */
    fun add(itemAdapter: AnyItemAdapter) {
        itemAdapter.ifSuccess(
                list = items,
                before = { items += this },
                after = { index -> notifyItemInserted(index) }
        )
    }

    /**
     * Removes an item from the list
     * */
    fun delete(itemAdapter: AnyItemAdapter) {
        itemAdapter.ifSuccess(
                list = items,
                after = { index -> items -= itemAdapter; notifyItemRemoved(index) }
        )
    }

    /**
     * Performs an action and then searches the list for an item. Performs another action
     * if item is found
     * @param list The list on which action is performed
     * @param before Action to perform before searching list
     * @param after Action to perform after searching list
     * */
    private fun AnyItemAdapter.ifSuccess(list: List<AnyItemAdapter>, before: (AnyItemAdapter.() -> Unit)? = null,
                                          after: (AnyItemAdapter.(index: Int) -> Unit)? = null) {
        if ((before == null) and (after == null)) return
        before?.invoke(this)
        val index = list.indexOf(this)
        if (index == -1) return
        after?.invoke(this, index)
    }
}
