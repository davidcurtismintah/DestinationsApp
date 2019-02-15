package com.allow.destinationsapp.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Returns a lazy delegate which provides a view found by its id.
 */
fun <T : View> RecyclerView.ViewHolder.bindView(layoutId: Int) = lazy { itemView.findViewById<T>(layoutId)!! }

/**
 * Get intent from specified class
 * */
inline fun <reified T : Activity> Context.getIntent() = Intent(this, T::class.java)

/**
 * Get an extra with a default value from an intent
 * */
fun <T : Parcelable> Activity.extra(key: String, default: T? = null): Lazy<T> =
        lazy { intent?.extras?.getParcelable<T>(key) ?: default ?: throw Error("No value $key in extras") }

/**
 * Make a toast
 * */
fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

/**
 * Make a snackbar
 * */
fun Context.snack(anchor: View, message: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(anchor, message, length).show()
}