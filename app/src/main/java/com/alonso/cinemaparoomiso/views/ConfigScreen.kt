package com.alonso.cinemaparoomiso.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alonso.cinemaparoomiso.MainActivity
import com.alonso.cinemaparoomiso.TopBar
import com.alonso.cinemaparoomiso.ent.ConfiguracionEntity
import kotlinx.coroutines.launch

/**
 * Número de salas del cine
 * Número de asientos por sala
 * Precio palomitas
 * Botón guardar
 * */

@Composable
fun ConfigScreen(modifier: Modifier, navController: NavController){
    var ctx = LocalContext.current
    var nSalas by rememberSaveable { mutableStateOf("0") }
    var nAsientos by rememberSaveable { mutableStateOf("0")  }
    var precioPalomitas by rememberSaveable { mutableStateOf("0") }
    TopBar(modifier, navController)
    Column (modifier = modifier.fillMaxSize().padding(top = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Cinema Paroomiso",
            fontSize = 40.sp,
            modifier = modifier.padding(top = 20.dp)
        )
        OutlinedTextField(
            value = nSalas,
            onValueChange = { nSalas = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Restrict the input to numbers
            ),
            modifier = modifier.padding(top = 20.dp),
            label = { Text("Número de salas") }
        )
        OutlinedTextField(
            value = nAsientos,
            onValueChange = { nAsientos = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Restrict the input to numbers
            ),
            modifier = modifier.padding(top = 20.dp),
            label = { Text("Número de asientos") }
        )
        OutlinedTextField(
            value = precioPalomitas,
            onValueChange = { precioPalomitas = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Restrict the input to numbers
            ),
            modifier = modifier.padding(top = 20.dp),
            label = { Text("Precio palomitas") }
        )
        Spacer(modifier.height(10.dp).weight(1f))
        Button(onClick = {
            try{
                var nSalasInt = nSalas.toInt()
                var nAsientosInt = nAsientos.toInt()
                var precioPalomitasFloat = precioPalomitas.toFloat()
                Log.d(":::aaaaa", "$nSalasInt $nAsientosInt $precioPalomitasFloat")
                MainActivity.coroutine.launch { guardar(nSalasInt, nAsientosInt, precioPalomitasFloat) }
                navController.navigate("salas")
            }catch(e: NumberFormatException){
                Toast.makeText(ctx, "El número debe ser acorde a lo estipulado.", Toast.LENGTH_LONG).show()
            }
        }, modifier = modifier.padding(20.dp)) {
            Icon(Icons.Default.Build, contentDescription = "")
            Text("Guardar")
        }
    }
}

suspend fun guardar(nSalas: Int, nAsientos: Int, precioPalomitas: Float){
    MainActivity.coroutine.launch {
        MainActivity.db.cineDao().insertConfig(ConfiguracionEntity(nSalas, nAsientos, precioPalomitas))
    }

}
/*
@Composable
@Preview
fun ConfigScreenPreview() {
    Scaffold { innerPadding ->
        ConfigScreen(Modifier.padding(innerPadding), null)
    }
}*/