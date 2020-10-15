package com.thishkt.pharmacydemo.data

import com.google.gson.annotations.SerializedName

class PharmacyInfo(
    @SerializedName("features")
    val features: List<Feature>
)

class Feature(
    @SerializedName("properties")
    val property: Property
)

class Property(
    @SerializedName("name")
    val name: String,

    @SerializedName("mask_adult")
    val mask_adult: String,

    @SerializedName("mask_child")
    val mask_child: String

)