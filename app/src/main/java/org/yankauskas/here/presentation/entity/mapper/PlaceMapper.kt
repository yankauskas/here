package org.yankauskas.here.presentation.entity.mapper


import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.data.entity.PlaceEntity
import org.yankauskas.here.presentation.entity.Place

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 19.03.2020.
 */
class PlaceMapper : EntityMapper<PlaceEntity, Place>() {
    override fun transform(entity: PlaceEntity) = with(entity) {
        Place(LatLng(position[0], position[1]), distance, title, icon, id)
    }
}