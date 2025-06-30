package com.Proyecto.coffeepalace.Data

import android.content.Context
import androidx.room.Room

object AppProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "coffee_palace_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}



/*import android.content.Context
import com.Proyecto.coffeepalace.Data.Database.AppDatabase
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginViewModel
import com.Proyecto.coffeepalace.ui.Screens.Forgot.ForgotPasswordViewModel
//import com.Proyecto.coffeepalace.ui.Screens.Home.HomeViewModel
//import com.Proyecto.coffeepalace.ui.Screens.RecipeDetail.RecipeDetailViewModel
//import com.Proyecto.coffeepalace.ui.Screens.Search.SearchViewModel
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpViewModel

object AppProvider {
    private lateinit var db: AppDatabase

    fun initialize(context: Context) {
        db = AppDatabase.getDatabase(context)
    }

    fun provideLoginViewModel() = LoginViewModel(db.userDao())
    fun provideForgotPasswordViewModel() = ForgotPasswordViewModel(db.userDao())
    /*fun provideRecipeDetailViewModel(recipeId: Int) =
        RecipeDetailViewModel(db.recipeDao(), db.commentDao(), recipeId)
    fun provideSearchViewModel() = SearchViewModel(db.recipeDao())*/
    fun provideSignUpViewModel() = SignUpViewModel(db.userDao())
    //fun provideHomeViewModel() = HomeViewModel(db.recipeDao())
}
*/