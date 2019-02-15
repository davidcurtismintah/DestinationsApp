package com.allow.destinationsapp.ui.picker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allow.destinationsapp.R
import com.allow.destinationsapp.domain.model.Airport
import com.allow.destinationsapp.ui.common.ItemAdapter
import com.allow.destinationsapp.ui.common.bindView
import com.allow.destinationsapp.ui.picker.AirportItemAdapter.AirportViewHolder

/**
 * creates a [AirportViewHolder] and binds it to a [MarvelAirport].
 */
class AirportItemAdapter(
    val airport: Airport,
    val clicked: (Airport) -> Unit
) : ItemAdapter<AirportViewHolder>(R.layout.item_airport) {

    /**
     * Creates a [RecyclerView.ViewHolder] and returns it
     * */
    override fun onCreateViewHolder(itemView: View) = AirportViewHolder(itemView)

    /**
     * Sets up views
     * */
    override fun AirportViewHolder.onBindViewHolder() {
        with(airport) {
            textView.text = name
            itemView.setOnClickListener { clicked(airport) }
        }
    }

    /**
     * Holds views used by [AirportItemAdapter]
     * */
    class AirportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView by bindView<TextView>(R.id.airport_name)
    }
}