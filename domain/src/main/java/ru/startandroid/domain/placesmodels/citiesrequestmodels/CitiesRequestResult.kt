package ru.startandroid.domain.models.placesmodel.citiesrequestmodels



data class CitiesRequestResult (

	val predictions : List<Predictions>,
	val status : String
)