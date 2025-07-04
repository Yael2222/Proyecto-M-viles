package com.Proyecto.coffeepalace.Data.Daos.discount

import com.Proyecto.coffeepalace.Data.Model.Anuncio

interface DaoAnuncio {
    suspend fun getAllAnuncios(): List<Anuncio>
}