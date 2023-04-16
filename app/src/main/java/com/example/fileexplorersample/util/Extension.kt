package com.example.fileexplorersample.util

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.fileexplorersample.R

fun WTF(msg: String) {
    Log.e("WTF", msg)
}

inline fun <reified T : FragmentActivity> T?.pushFragment
            (
    fragment : Fragment,
    container : Int = R.id.container
)
{
    this?.supportFragmentManager
        ?.beginTransaction()
        ?.addToBackStack(T::class.java.simpleName)
        ?.add(container, fragment, T::class.java.simpleName)
        ?.commit()
}

