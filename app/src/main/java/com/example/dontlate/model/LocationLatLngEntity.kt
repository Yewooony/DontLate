package com.example.dontlate.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize // 인텐트로 넘기기 위함
data class LocationLatLngEntity (
    val latitude: Float,
    val longitude: Float
) : Parcelable