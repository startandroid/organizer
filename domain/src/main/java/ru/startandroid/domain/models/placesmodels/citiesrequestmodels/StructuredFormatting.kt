package ru.startandroid.domain.models.placesmodels.citiesrequestmodels


data class StructuredFormatting (

	val main_text : String,
	val main_text_matched_substrings : List<MainTextMatchedSubstrings>,
	val secondary_text : String
)