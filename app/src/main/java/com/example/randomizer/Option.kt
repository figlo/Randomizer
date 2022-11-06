package com.example.randomizer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val text: String
) : Parcelable