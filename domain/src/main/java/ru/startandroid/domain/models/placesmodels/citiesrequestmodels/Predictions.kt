package ru.startandroid.domain.models.placesmodels.citiesrequestmodels


data class Predictions (

        val description : String,
        val id : String,
        val matched_substrings : List<MatchedSubstrings>,
        val place_id : String,
        val reference : String,
        val structured_formatting : StructuredFormatting,
        val terms : List<Terms>,
        val types : List<String>
)