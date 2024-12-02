package com.alonso.cinemaparoomiso.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alonso.cinemaparoomiso.MainActivity
import com.alonso.cinemaparoomiso.TopBar
import com.alonso.cinemaparoomiso.ent.ClienteEntity
import com.alonso.cinemaparoomiso.ent.ConfiguracionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Sala(modifier: Modifier, navController: NavController) {
    var conf by remember { mutableStateOf(ConfiguracionEntity(numSalas = 0, numAsientos = 0, precioPalomitas = 0f)) }
    var nClientes by remember { mutableStateOf(0) }

    // Fetch config asynchronously
    LaunchedEffect(Unit) {
        conf = getLatestConfig()
        Log.d(":::aaaaa", "${conf.numSalas}")
    }

    // Update the number of clients and trigger color update
    LaunchedEffect(nClientes) {
        conf.let {
            MainActivity.db.cineDao().deleteAllClients()
            while (nClientes < 100 || nClientes >= (it.numAsientos * it.numSalas)) {
                delay(1000)
                val newClients = agregarClientes(it.numSalas)
                nClientes += newClients
            }
        }
    }

    TopBar(modifier, navController)
    Column(modifier = modifier.fillMaxSize().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Salas disponibles", textAlign = TextAlign.Center, fontSize = 40.sp)
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(conf.numSalas) { index ->
                CartaSala(index, conf)
            }
        }
    }
}

suspend fun getLatestConfig(): ConfiguracionEntity {
    return withContext(Dispatchers.IO) {
        MainActivity.db.cineDao().getLatestConfig()
    }
}

@Composable
fun CartaSala(index: Int, config: ConfiguracionEntity) {
    var color by remember { mutableStateOf(Color.Gray) }

    // Update the color based on the number of clients
    LaunchedEffect(index, config) {
        color = getColorAforo(index, config)
    }

    Box(
        Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RectangleShape)
            .background(color = color)
            .padding(10.dp), contentAlignment = Alignment.Center
    ) {
        Text("Sala ${index + 1}", fontSize = 25.sp, color = Color.White)
    }
}

suspend fun getColorAforo(index: Int, config: ConfiguracionEntity): Color {
    val clientes = withContext(Dispatchers.IO) {
        MainActivity.db.cineDao().getAsientosOcupadosSala(index)
    }
    val porcentaje = (clientes * 100) / config.numAsientos
    return when {
        porcentaje < 66 -> Color.Green
        porcentaje < 90 -> Color.Yellow
        else -> Color.Red
    }
}

suspend fun agregarClientes(nSalas: Int): Int {
    val nClientes = (1..3).random()
    withContext(Dispatchers.IO) {
        for (i in 1..nClientes) {
            MainActivity.db.cineDao().insertClient(
                ClienteEntity(
                    salaElegida = ((1..nSalas).random()),
                    palomitas = (0..5).random()
                )
            )
        }
    }
    return nClientes
}
