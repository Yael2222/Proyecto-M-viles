package com.Proyecto.coffeepalace.Data.Daos.ads

import com.Proyecto.coffeepalace.Data.Model.ads

interface DaoAds {
    suspend fun getAllAds(): List<ads>
    suspend fun addAds(description: String, image: String, name: String): Boolean
    suspend fun deleteAds(id: Long): Boolean
}