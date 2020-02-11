package ru.startandroid.domain.models.placesmodels.citiesrequestmodels



data class CitiesRequestResult (

	val predictions : List<Predictions>,
	val status : String
)