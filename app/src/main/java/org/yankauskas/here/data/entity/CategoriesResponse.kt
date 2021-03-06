package org.yankauskas.here.data.entity

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
data class CategoriesResponse (
    val items: List<CategoryEntity>
)


data class CategoryEntity (
    val id: String,
    val title: String,
    val icon: String
)

