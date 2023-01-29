package com.example.dontlate.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize //인텐트로 넘기기 위함
data class SearchResultEntity (
    val fullAddress: String,
    val name: String,
    val locationLatLng: LocationLatLngEntity
) : Parcelable