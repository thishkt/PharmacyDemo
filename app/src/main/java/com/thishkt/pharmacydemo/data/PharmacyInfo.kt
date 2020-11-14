package com.thishkt.pharmacydemo.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PharmacyInfo(
    @SerializedName("features")
    val features: List<Feature>
) : Serializable

class Feature(
    @SerializedName("properties")
    val property: Property,
    @SerializedName("geometry")
    val geometry: Geometry
) : Serializable

class Property(
    @SerializedName("name")
    val name: String,

    @SerializedName("mask_adult")
    val mask_adult: String,

    @SerializedName("mask_child")
    val mask_child: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("county")
    val county: String,

    @SerializedName("town")
    val town: String


) : Serializable

class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<Double>
): Serializable