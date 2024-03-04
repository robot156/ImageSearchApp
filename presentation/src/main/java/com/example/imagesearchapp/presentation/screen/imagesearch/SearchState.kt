package com.example.imagesearchapp.presentation.screen.imagesearch

import com.example.imagesearchapp.designsystem.common.TextFieldState

class SearchState : TextFieldState(validator = ::isSearchQueryValid)

private fun isSearchQueryValid(searchQuery: String): Boolean {
    return searchQuery.isNotEmpty()
}