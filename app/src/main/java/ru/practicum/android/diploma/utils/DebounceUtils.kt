package ru.practicum.android.diploma.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceUtils {
    private var searchJob: Job? = null
    private var isClickAllowed = true

    fun searchDebounce(scope: CoroutineScope, request: suspend () -> Unit) {
        searchJob?.cancel()
        searchJob = scope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            request()
        }
    }

    fun clickDebounce(scope: CoroutineScope): Boolean {
        val currentValue = isClickAllowed
        if (currentValue) {
            isClickAllowed = false
            scope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return currentValue
    }

    private companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}
