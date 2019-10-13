package ru.startandroid.domain.models.placesmodel.citiesrequestmodels


data class Structured_formatting (

	val main_text : String,
	val main_text_matched_substrings : List<Main_text_matched_substrings>,
	val secondary_text : String
)