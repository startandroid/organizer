package ru.startandroid.domain.models.placesmodel.citiesdetailsmodels


data class Result (

	val address_components : List<Address_components>,
	val adr_address : String,
	val formatted_address : String,
	val geometry : Geometry,
	val icon : String,
	val id : String,
	val name : String,
	val photos : List<Photos>,
	val place_id : String,
	val reference : String,
	val scope : String,
	val types : List<String>,
	val url : String,
	val utc_offset : Int,
	val vicinity : String
)