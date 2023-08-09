package com.example.menutoday.data

sealed class Category(val category: String) {

    object Snacks: Category("Snacks")
    object SouthIndian: Category("South Indian")
    object MainCourse: Category("Main Course")
    object Beverages: Category("Beverages")
}