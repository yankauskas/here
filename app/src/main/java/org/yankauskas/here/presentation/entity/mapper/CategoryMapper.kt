package org.yankauskas.here.presentation.entity.mapper

import org.yankauskas.here.data.entity.CategoryEntity
import org.yankauskas.here.presentation.entity.Category

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 19.03.2020.
 */
class CategoryMapper: EntityMapper<CategoryEntity, Category>() {
    override fun transform(entity: CategoryEntity) = with(entity) {Category(id, title, icon)}
}