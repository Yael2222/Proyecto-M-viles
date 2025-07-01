package com.Proyecto.coffeepalace.Data.Daos.comentarioProd

import com.Proyecto.coffeepalace.Data.Model.comentario

interface DaoComentario {
    suspend fun getAllComentarios(): List<comentario>
}
