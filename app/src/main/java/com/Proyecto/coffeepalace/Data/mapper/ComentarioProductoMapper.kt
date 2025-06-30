package com.Proyecto.coffeepalace.Data.mapper

import com.Proyecto.coffeepalace.Data.Entity.ComentarioProductoEntity
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto

fun ComentarioProductoEntity.toModel() = ComentarioProducto(
    id = id,
    idProducto = idProducto,
    idUsuario = idUsuario,
    text = text,
    rating = rating,
)

fun ComentarioProducto.toEntity() = ComentarioProductoEntity(
    id = id,
    idProducto = idProducto,
    idUsuario = idUsuario,
    text = text,
    rating = rating,
)
