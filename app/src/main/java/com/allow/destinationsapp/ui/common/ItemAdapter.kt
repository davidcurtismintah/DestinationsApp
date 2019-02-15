package com.allow.destinationsapp.ui.common

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for a single item type.
 * Create subclasses for use with [RecyclerListAdapter] to display a list of elements.
 */
abstract class ItemAdapter<T : RecyclerView.ViewHolder>(
        @LayoutRes open val layoutId: Int
) {

    /**
    * [RecyclerView.ViewHolder]s should be created here
    * */
    abstract fun onCreateViewHolder(itemView: View): T

    /**
     * Call this method to perform binding of views.
     * Actual binding should be implemented in [onBindViewHolder]
     * */
    @Suppress("UNCHECKED_CAST")
    fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        (holder as T).onBindViewHolder()
    }

    /**
     * set all values on [RecyclerView.ViewHolder] here
    */
    abstract fun T.onBindViewHolder()
}