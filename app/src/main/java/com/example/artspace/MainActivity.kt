package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    // guardo en que obra estoy, empieza en la 1
    var obraActual by remember { mutableStateOf(1) }

    // segun la obra cojo la imagen y los textos que toquen
    val imagen = when (obraActual) {
        1 -> R.drawable.atardecer_mar
        2 -> R.drawable.composicion_geometrica
        3 -> R.drawable.noche_espirales
        4 -> R.drawable.campo_amapolas
        else -> R.drawable.circulos_armonia
    }
    val titulo = when (obraActual) {
        1 -> R.string.title_1
        2 -> R.string.title_2
        3 -> R.string.title_3
        4 -> R.string.title_4
        else -> R.string.title_5
    }
    val artista = when (obraActual) {
        1 -> R.string.artist_1
        2 -> R.string.artist_2
        3 -> R.string.artist_3
        4 -> R.string.artist_4
        else -> R.string.artist_5
    }
    val anio = when (obraActual) {
        1 -> R.string.year_1
        2 -> R.string.year_2
        3 -> R.string.year_3
        4 -> R.string.year_4
        else -> R.string.year_5
    }
    val descripcion = when (obraActual) {
        1 -> R.string.description_1
        2 -> R.string.description_2
        3 -> R.string.description_3
        4 -> R.string.description_4
        else -> R.string.description_5
    }

    // pongo scroll para que en horizontal o en tablet no se corte nada
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(imagen = imagen, descripcion = descripcion)

        Spacer(modifier = Modifier.height(32.dp))

        ArtworkDescriptor(titulo = titulo, artista = artista, anio = anio)

        Spacer(modifier = Modifier.height(24.dp))

        DisplayController(
            onAnterior = {
                // si estoy en la primera y le doy a anterior me voy a la ultima
                if (obraActual == 1) {
                    obraActual = 5
                } else {
                    obraActual = obraActual - 1
                }
            },
            onSiguiente = {
                // si estoy en la ultima vuelvo a empezar por la primera
                if (obraActual == 5) {
                    obraActual = 1
                } else {
                    obraActual = obraActual + 1
                }
            }
        )
    }
}

// la "pared" con el cuadro, le pongo un Surface con sombra para que parezca un marco
@Composable
fun ArtworkWall(imagen: Int, descripcion: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shadowElevation = 12.dp
    ) {
        Image(
            painter = painterResource(imagen),
            // la descripcion es para el TalkBack
            contentDescription = stringResource(descripcion),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(32.dp)
                .height(380.dp)
        )
    }
}

// el recuadro con el titulo y debajo el artista y el año
@Composable
fun ArtworkDescriptor(titulo: Int, artista: Int, anio: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(titulo),
                fontSize = 26.sp,
                fontWeight = FontWeight.Light
            )
            Row {
                Text(
                    text = stringResource(artista),
                    fontWeight = FontWeight.Bold
                )
                Text(text = " (" + stringResource(anio) + ")")
            }
        }
    }
}

// los dos botones, uno a cada lado
@Composable
fun DisplayController(onAnterior: () -> Unit, onSiguiente: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onAnterior, modifier = Modifier.width(140.dp)) {
            Text(text = stringResource(R.string.previous))
        }
        Button(onClick = onSiguiente, modifier = Modifier.width(140.dp)) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}
