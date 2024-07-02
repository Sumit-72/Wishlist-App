package eu.tutorials.mywishlistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whishlistapp.AddEditView
import com.example.whishlistapp.HomeView
import com.example.whishlistapp.Screen
import com.example.whishlistapp.WishViewModel

@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),
               navController: NavHostController = rememberNavController()){


    NavHost(
        navController= navController,
        startDestination = Screen.HomeScreen.route
    ){
        composable(Screen.HomeScreen.route){
            HomeView(navController,viewModel)
        }

        composable(Screen.AddScreen.route + "/{id}",
            arguments= listOf(
                navArgument("id"){
                    type= NavType.LongType
                    defaultValue = 0L
                    nullable =false
                }
            )
        ){entry->
            val id =if(entry.arguments !=null) entry.arguments!!.getLong("id") else 0L
            AddEditView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}