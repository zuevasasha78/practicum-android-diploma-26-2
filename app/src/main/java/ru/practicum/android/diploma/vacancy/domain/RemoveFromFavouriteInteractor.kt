package ru.practicum.android.diploma.vacancy.domain

class RemoveFromFavouriteInteractor() {

    fun execute(vacancyId: String): Boolean {
        return try {
            // TODO favouriteRepository.removeFromFavourite(vacancyId)
            true
        } catch (e: Exception) {
            false
        }
    }
}
