package com.example.albertsonstask.presentation.utils

import com.example.albertsonstask.presentation.utils.ProductConstants.MIN_TEXT_LENGTH_SEARCH

/**
 * @Author: Sourav PC
 * @Date: 20-09-2023
 */

fun String.validateSearch() = this.length >= MIN_TEXT_LENGTH_SEARCH || this.isEmpty()