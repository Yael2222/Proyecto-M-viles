package com.Proyecto.coffeepalace.ui.Screens.CategoryPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Category
import com.Proyecto.coffeepalace.util.supabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    supabase.from("categoria").select().decodeList<Category>()
                }
                _categories.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addCategory(nombre: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    supabase.from("categoria").insert(Category(0, nombre))
                }
                loadCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editCategory(id: Int, newName: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    supabase.from("categoria")
                        .update(mapOf("nombre_categoria" to newName)) {
                            filter {
                                eq("id", id)
                            }
                        }
                }
                loadCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
