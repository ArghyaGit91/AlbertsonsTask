package com.example.albertsonstask.presentation.utils

import com.example.albertsonstask.presentation.utils.ProductConstants.MIN_TEXT_LENGTH_SEARCH


fun String.validateSearch() = this.length >= MIN_TEXT_LENGTH_SEARCH || this.isEmpty()