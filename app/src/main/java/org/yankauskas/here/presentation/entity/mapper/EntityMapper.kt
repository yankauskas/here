package org.yankauskas.here.presentation.entity.mapper

/**
 * Created by alex_litvinenko on 04.08.17.
 */
abstract class EntityMapper<Entity : Any, ViewModel> {

    abstract fun transform(entity: Entity): ViewModel

    fun transform(collection: Collection<Entity>): ArrayList<ViewModel> {
        val list = arrayListOf<ViewModel>()
        var model: ViewModel
        for (entity in collection) {
            model = transform(entity)
            if (model != null) {
                list.add(model)
            }
        }
        return list
    }
}