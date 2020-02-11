package ru.startandroid.domain.models.placesmodels.citiesdetailsmodels


data class Result (
        val geometry : Geometry,
        val icon : String,
        val id : String,
        val name : String,
        val photos : List<Photos>,
        val place_id : String
)