package com.Proyecto.coffeepalace.ui.Screens.AdminAdsPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.ads.DaoAdsImpl
import com.Proyecto.coffeepalace.Data.Model.ads
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class AdsViewModel  : ViewModel() {
    private val dao = DaoAdsImpl()

    private val _ads = MutableStateFlow<List<ads>>(emptyList())
    val advertisements = _ads.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadAds()
    }

    fun loadAds() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ads.value = dao.getAllAds()
            }catch (e: Exception){
                println("Error loading ads: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun addAds(description: String, image: String, name: String) {
        viewModelScope.launch {
            if (dao.addAds(description, image, name)) {
                loadAds()
            }
        }
    }

    fun deleteAds(id: Long) {
        viewModelScope.launch {
            if (dao.deleteAds(id)) {
                loadAds()
            }
        }
    }
}