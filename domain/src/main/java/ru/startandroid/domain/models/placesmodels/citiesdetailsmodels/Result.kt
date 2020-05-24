package ru.startandroid.domain.models.placesmodels.citiesdetailsmodels


data class Result (
        val geometry : Geometry,
        val placeId : String,
        val name : String,
        val id : String
)