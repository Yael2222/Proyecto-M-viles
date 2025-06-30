package com.Proyecto.coffeepalace.Data.Daos.receta


import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente // ¡Necesitas un modelo para esta tabla!


interface DaoReceta {

    suspend fun getAllIngredientesReceta(): List<ingrediente>
    suspend fun addReceta(receta: receta, ingredientesIds: List<Long>): Boolean
    suspend fun getAllRecetas(): List<receta>
    suspend fun deleteReceta(idReceta: Long?): Boolean
    suspend fun getAllRecetaIngredienteRelations(): List<receta_ingrediente>

}