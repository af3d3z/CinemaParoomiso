package com.alonso.cinemaparoomiso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.alonso.cinemaparoomiso.dal.CineDB
import com.alonso.cinemaparoomiso.ui.theme.CinemaParoomisoTheme
import com.alonso.cinemaparoomiso.views.ConfigScreen
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: CineDB
        lateinit var coroutine: CoroutineScope
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            db = Room.databaseBuilder(applicationContext, CineDB::class.java, "cinedb").build()
            coroutine = rememberCoroutineScope()
            CinemaParoomisoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "config"
                    ){
                        composable("config") {
                            ConfigScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CinemaParoomisoTheme {
        Greeting("Android")
    }
}

@Composable
public fun TopBar(modifier: Modifier, navController: NavController){
    Box(modifier = modifier) {
        Surface(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter), color = Color(0xBDE4A800)) {
            Row {
                TextButton(onClick = {navController.navigate("config")}, modifier = Modifier.weight(1f)) {
                    Text("Configuración", fontSize = 16.sp)
                }
                TextButton(onClick = {navController.navigate("salas")}, modifier = Modifier.weight(1f)) {
                    Text("Salas", fontSize = 16.sp)
                }
                TextButton(onClick = {navController.navigate("resumen")}, modifier = Modifier.weight(1f)) {
                    Text("Resumen", fontSize = 16.sp)
                }
            }
        }
    }
}