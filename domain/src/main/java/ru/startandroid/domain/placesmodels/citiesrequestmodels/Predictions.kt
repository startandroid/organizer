package ru.startandroid.domain.models.placesmodel.citiesrequestmodels


data class Predictions (

	val description : String,
	val id : String,
	val matched_substrings : List<Matched_substrings>,
	val place_id : String,
	val reference : String,
	val structured_formatting : Structured_formatting,
	val terms : List<Terms>,
	val types : List<String>
)