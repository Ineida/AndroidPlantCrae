package com.projectPlant.model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

open class Storage(var context: Context) : ViewModel() {
    private var mPreferences: SharedPreferences =
        context.getSharedPreferences("appUser", Context.MODE_PRIVATE)

    private var mSharedEditor = mPreferences.edit()!!

    fun setElementString(name: String, value: String) {
        mSharedEditor.putString(name, value)
        mSharedEditor.commit()
    }

    fun setElementInt(name: String, value: Int) {
        mSharedEditor.putInt(name, value)
        mSharedEditor.commit()
    }

    fun getStringElement(name: String): String {
        return mPreferences.getString(name, "")!!
    }

    fun getToken(): String {
        return "Bearer " + mPreferences.getString("_TOKEN", null)!!
    }

    fun getPersonId(): Int {
        return mPreferences.getInt("_PERSON", 0)
    }
}