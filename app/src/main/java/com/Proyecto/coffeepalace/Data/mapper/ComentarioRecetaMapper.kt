package com.Proyecto.coffeepalace.Data.mapper

import com.Proyecto.coffeepalace.Data.Entity.ComentarioRecetaEntity
import com.Proyecto.coffeepalace.Data.Model.ComentarioReceta

fun ComentarioRecetaEntity.toModel(): ComentarioReceta {
    return ComentarioReceta(
        id = id,
        idReceta = idReceta,
        idUsuario = idUsuario,
        text = text,
        rating = rating
    )
}

fun ComentarioReceta.toEntity(): ComentarioRecetaEntity {
    return ComentarioRecetaEntity(
        id = id,
        idReceta = idReceta,
        idUsuario = idUsuario,
        text = text,
        rating = rating,
        timestamp = System.currentTimeMillis()
    )
}
