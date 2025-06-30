package com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered

import com.Proyecto.coffeepalace.Data.Model.ItemFeatureModel

enum class FilteredTypes(
    val id: Int,
    val title: String,
    val category: String,
    val image: String
) {

    DRINKS(
        1,
        "Drinks",
        "Drinks",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    FOOD(
        2,
        "Food",
        "Food",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    AT_HOME(
        3,
        "At Home Coffee",
        "Coffee Shop",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    MERCHANDISE(
        4,
        "Merchandise",
        "Dishes",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    );

    companion object {
        fun fromId(id: Int): FilteredTypes? {
            return FilteredTypes.entries.firstOrNull { it.id == id }
        }
    }
}


fun FilteredTypes.toDomain() = ItemFeatureModel(
    id = id,
    title = title,
    category = category,
    image = image
)