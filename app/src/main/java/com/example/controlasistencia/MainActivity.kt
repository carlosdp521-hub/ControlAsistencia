package com.example.controlasistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.controlasistencia.ui.theme.ControlAsistenciaTheme

import kotlinx.coroutines.delay

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// ======================================================
// MODELO DE REGISTRO
// ======================================================

data class RegistroAsistencia(
    val fecha: String,
    val entrada: String,
    val salida: String
)


// ======================================================
// ACTIVIDAD PRINCIPAL
// ======================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ControlAsistenciaTheme {

                ControlAsistenciaApp()
            }
        }
    }
}


// ======================================================
// APLICACIÓN PRINCIPAL
// ======================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlAsistenciaApp() {

    // --------------------------------------------------
    // VARIABLES
    // --------------------------------------------------

    var horaActual by remember {

        mutableStateOf(
            SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date())
        )
    }

    var horaEntrada by remember {
        mutableStateOf("")
    }

    var horaSalida by remember {
        mutableStateOf("")
    }

    var estado by remember {
        mutableStateOf("Sin registro")
    }

    var registros by remember {
        mutableStateOf(
            listOf<RegistroAsistencia>()
        )
    }

    var pantallaSeleccionada by remember {
        mutableStateOf(0)
    }

    val fechaActual = SimpleDateFormat(
        "dd-MM-yyyy",
        Locale.getDefault()
    ).format(Date())


    // --------------------------------------------------
    // RELOJ EN TIEMPO REAL
    // --------------------------------------------------

    LaunchedEffect(Unit) {

        while (true) {

            horaActual = SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date())

            delay(1000)
        }
    }


    // ==================================================
    // SCAFFOLD
    // ==================================================

    Scaffold(

        containerColor = Color(0xFF07101F),


        // ==================================================
        // BARRA SUPERIOR
        // ==================================================

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Control de Asistencia",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Registro de jornada laboral",
                            fontSize = 13.sp,
                            color = Color(0xFFB8C4D6)
                        )
                    }
                },


                navigationIcon = {

                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp)
                    )
                },


                actions = {

                    Box(

                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0xFF4F6FE8)
                            ),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "CD",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },


                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = Color(0xFF152447),

                    titleContentColor = Color.White,

                    navigationIconContentColor = Color.White,

                    actionIconContentColor = Color.White
                )
            )
        },


        // ==================================================
        // BARRA INFERIOR
        // ==================================================

        bottomBar = {

            NavigationBar(

                containerColor = Color(0xFF152238),

                modifier = Modifier
                    .navigationBarsPadding()
            ) {

                NavigationBarItem(

                    selected =
                        pantallaSeleccionada == 0,

                    onClick = {

                        pantallaSeleccionada = 0
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.Home,

                            contentDescription =
                                "Inicio"
                        )
                    },

                    label = {

                        Text(
                            text = "Inicio"
                        )
                    }
                )


                NavigationBarItem(

                    selected =
                        pantallaSeleccionada == 1,

                    onClick = {

                        pantallaSeleccionada = 1
                    },

                    icon = {

                        Icon(
                            imageVector =
                                Icons.Default.BarChart,

                            contentDescription =
                                "Resumen"
                        )
                    },

                    label = {

                        Text(
                            text = "Resumen"
                        )
                    }
                )
            }
        }

    ) { innerPadding ->


        // ==================================================
        // CONTENIDO PRINCIPAL
        // ==================================================

        if (pantallaSeleccionada == 0) {

            PantallaInicio(

                modifier =
                    Modifier.padding(innerPadding),

                fechaActual =
                    fechaActual,

                horaActual =
                    horaActual,

                estado =
                    estado,

                horaEntrada =
                    horaEntrada,

                horaSalida =
                    horaSalida,

                registros =
                    registros,


                // ------------------------------------------
                // REGISTRAR ENTRADA
                // ------------------------------------------

                onRegistrarEntrada = {

                    horaEntrada =
                        SimpleDateFormat(
                            "HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date())

                    estado =
                        "Entrada registrada"
                },


                // ------------------------------------------
                // REGISTRAR SALIDA
                // ------------------------------------------

                onRegistrarSalida = {

                    horaSalida =
                        SimpleDateFormat(
                            "HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date())

                    estado =
                        "Jornada finalizada"

                    registros =
                        registros +
                                RegistroAsistencia(

                                    fecha =
                                        fechaActual,

                                    entrada =
                                        horaEntrada,

                                    salida =
                                        horaSalida
                                )
                },


                // ------------------------------------------
                // NUEVO REGISTRO
                // ------------------------------------------

                onNuevoRegistro = {

                    horaEntrada = ""

                    horaSalida = ""

                    estado =
                        "Sin registro"
                }
            )

        } else {

            PantallaResumen(

                modifier =
                    Modifier.padding(innerPadding),

                registros =
                    registros
            )
        }
    }
}


// ======================================================
// PANTALLA DE INICIO
// ======================================================

@Composable
fun PantallaInicio(

    modifier: Modifier,

    fechaActual: String,

    horaActual: String,

    estado: String,

    horaEntrada: String,

    horaSalida: String,

    registros: List<RegistroAsistencia>,

    onRegistrarEntrada: () -> Unit,

    onRegistrarSalida: () -> Unit,

    onNuevoRegistro: () -> Unit

) {

    Column(

        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(18.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // ==================================================
        // TARJETA DEL USUARIO
        // ==================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFF17243A)
                )
        ) {

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(22.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                // ------------------------------------------
                // AVATAR
                // ------------------------------------------

                Box(

                    modifier =
                        Modifier
                            .size(95.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0xFF4F6FE8)
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Person,

                        contentDescription =
                            "Usuario",

                        tint =
                            Color.White,

                        modifier =
                            Modifier.size(55.dp)
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(18.dp)
                )


                // ------------------------------------------
                // INFORMACIÓN
                // ------------------------------------------

                Column {

                    Text(

                        text =
                            "Carlos Di Piazza",

                        fontSize =
                            21.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.CalendarMonth,

                            contentDescription =
                                "Fecha",

                            tint =
                                Color(0xFF7EA1FF),

                            modifier =
                                Modifier.size(20.dp)
                        )


                        Spacer(
                            modifier =
                                Modifier.width(7.dp)
                        )


                        Text(

                            text =
                                "Fecha: $fechaActual",

                            color =
                                Color(0xFFD0D7E5),

                            fontSize =
                                14.sp
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(7.dp)
                    )


                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.AccessTime,

                            contentDescription =
                                "Hora",

                            tint =
                                Color(0xFF7EA1FF),

                            modifier =
                                Modifier.size(20.dp)
                        )


                        Spacer(
                            modifier =
                                Modifier.width(7.dp)
                        )


                        Text(

                            text =
                                "Hora actual: $horaActual",

                            color =
                                Color(0xFFD0D7E5),

                            fontSize =
                                14.sp
                        )
                    }
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        // ==================================================
        // TARJETA DE ESTADO
        // ==================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFF17243A)
                )
        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(22.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {


                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Box(

                        modifier =
                            Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xFF32B86D)
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                "Estado",

                            tint =
                                Color.White,

                            modifier =
                                Modifier.size(29.dp)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )


                    Text(

                        text =
                            "Estado actual",

                        fontSize =
                            20.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(25.dp)
                )


                // ------------------------------------------
                // ICONO CENTRAL
                // ------------------------------------------

                Box(

                    modifier =
                        Modifier
                            .size(125.dp)
                            .clip(
                                RoundedCornerShape(30.dp)
                            )
                            .background(
                                Color(0xFF26385C)
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Assignment,

                        contentDescription =
                            "Asistencia",

                        tint =
                            Color(0xFF6E8DF5),

                        modifier =
                            Modifier.size(72.dp)
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )


                Text(

                    text =
                        estado,

                    fontSize =
                        22.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color.White
                )


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                Text(

                    text = when (estado) {

                        "Sin registro" ->
                            "Aún no has registrado tu entrada."

                        "Entrada registrada" ->
                            "Tu jornada laboral está activa."

                        else ->
                            "Tu jornada laboral ha finalizado."
                    },

                    color =
                        Color(0xFFAAB5C8),

                    fontSize =
                        14.sp,

                    textAlign =
                        TextAlign.Center
                )


                if (horaEntrada.isNotEmpty()) {

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    Text(

                        text =
                            "Entrada: $horaEntrada",

                        color =
                            Color(0xFF6EE7A8),

                        fontWeight =
                            FontWeight.Bold
                    )
                }


                if (horaSalida.isNotEmpty()) {

                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )

                    Text(

                        text =
                            "Salida: $horaSalida",

                        color =
                            Color(0xFF7EA1FF),

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        // ==================================================
        // BOTÓN ENTRADA
        // ==================================================

        Button(

            onClick =
                onRegistrarEntrada,

            enabled =
                horaEntrada.isEmpty(),

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(65.dp),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Color(0xFF32B86D),

                    contentColor =
                        Color.White,

                    disabledContainerColor =
                        Color(0xFF243129),

                    disabledContentColor =
                        Color(0xFF66756C)
                )
        ) {

            Icon(

                imageVector =
                    Icons.Default.Login,

                contentDescription =
                    "Entrada",

                modifier =
                    Modifier.size(29.dp)
            )


            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )


            Text(

                text =
                    "Registrar Entrada",

                fontSize =
                    17.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.weight(1f)
            )


            Icon(

                imageVector =
                    Icons.Default.ChevronRight,

                contentDescription =
                    null
            )
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // ==================================================
        // BOTÓN SALIDA
        // ==================================================

        Button(

            onClick =
                onRegistrarSalida,

            enabled =
                horaEntrada.isNotEmpty() &&
                        horaSalida.isEmpty(),

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(65.dp),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Color(0xFF2867D9),

                    contentColor =
                        Color.White,

                    disabledContainerColor =
                        Color(0xFF202B3C),

                    disabledContentColor =
                        Color(0xFF68758A)
                )
        ) {

            Icon(

                imageVector =
                    Icons.Default.Logout,

                contentDescription =
                    "Salida",

                modifier =
                    Modifier.size(29.dp)
            )


            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )


            Text(

                text =
                    "Registrar Salida",

                fontSize =
                    17.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.weight(1f)
            )


            Icon(

                imageVector =
                    Icons.Default.ChevronRight,

                contentDescription =
                    null
            )
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // ==================================================
        // BOTÓN NUEVO REGISTRO
        // ==================================================

        Button(

            onClick =
                onNuevoRegistro,

            enabled =
                horaEntrada.isNotEmpty(),

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(65.dp),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Color(0xFF18263C),

                    contentColor =
                        Color.White,

                    disabledContainerColor =
                        Color(0xFF101A29),

                    disabledContentColor =
                        Color(0xFF596579)
                )
        ) {

            Icon(

                imageVector =
                    Icons.Default.Refresh,

                contentDescription =
                    "Nuevo registro",

                modifier =
                    Modifier.size(29.dp)
            )


            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )


            Column(

                modifier =
                    Modifier.weight(1f),

                horizontalAlignment =
                    Alignment.Start
            ) {

                Text(

                    text =
                        "Nuevo registro",

                    fontSize =
                        17.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Text(

                    text =
                        "Limpiar registro actual",

                    fontSize =
                        12.sp,

                    color =
                        Color(0xFFAAB5C8)
                )
            }


            Icon(

                imageVector =
                    Icons.Default.ChevronRight,

                contentDescription =
                    null
            )
        }


        Spacer(
            modifier =
                Modifier.height(25.dp)
        )


        // ==================================================
        // HISTORIAL
        // ==================================================

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(

                imageVector =
                    Icons.Default.Assignment,

                contentDescription =
                    "Historial",

                tint =
                    Color(0xFF8E7CF6),

                modifier =
                    Modifier.size(30.dp)
            )


            Spacer(
                modifier =
                    Modifier.width(9.dp)
            )


            Text(

                text =
                    "Historial de asistencia",

                fontSize =
                    20.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color.White
            )
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        if (registros.isEmpty()) {

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(20.dp),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            Color(0xFF17243A)
                    )
            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(28.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Assignment,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF66748A),

                        modifier =
                            Modifier.size(52.dp)
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    Text(

                        text =
                            "No existen registros todavía.",

                        color =
                            Color.White,

                        fontSize =
                            16.sp,

                        fontWeight =
                            FontWeight.Medium,

                        textAlign =
                            TextAlign.Center
                    )


                    Spacer(
                        modifier =
                            Modifier.height(5.dp)
                    )


                    Text(

                        text =
                            "Tus registros aparecerán aquí.",

                        color =
                            Color(0xFF8996AA),

                        fontSize =
                            13.sp
                    )
                }
            }

        } else {

            registros
                .reversed()
                .forEach { registro ->

                    RegistroItem(
                        registro =
                            registro
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )
                }
        }


        Spacer(
            modifier =
                Modifier.height(25.dp)
        )
    }
}


// ======================================================
// ITEM DEL HISTORIAL
// ======================================================

@Composable
fun RegistroItem(
    registro: RegistroAsistencia
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    Color(0xFF17243A)
            )
    ) {

        Column(

            modifier =
                Modifier.padding(18.dp)
        ) {

            Text(

                text =
                    registro.fecha,

                color =
                    Color.White,

                fontSize =
                    17.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(

                    text =
                        "Entrada: ${registro.entrada}",

                    color =
                        Color(0xFF6EE7A8),

                    fontSize =
                        14.sp
                )


                Text(

                    text =
                        "Salida: ${registro.salida}",

                    color =
                        Color(0xFF7EA1FF),

                    fontSize =
                        14.sp
                )
            }
        }
    }
}


// ======================================================
// PANTALLA RESUMEN
// ======================================================

@Composable
fun PantallaResumen(

    modifier: Modifier,

    registros: List<RegistroAsistencia>

) {

    val totalRegistros =
        registros.size


    Column(

        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
    ) {

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )


        Text(

            text =
                "Resumen",

            color =
                Color.White,

            fontSize =
                30.sp,

            fontWeight =
                FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Text(

            text =
                "Resumen de tus jornadas laborales",

            color =
                Color(0xFFAAB5C8),

            fontSize =
                16.sp
        )


        Spacer(
            modifier =
                Modifier.height(25.dp)
        )


        // ==================================================
        // TOTAL
        // ==================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFF17243A)
                )
        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(25.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(

                    text =
                        "Jornadas registradas",

                    color =
                        Color(0xFFAAB5C8),

                    fontSize =
                        15.sp
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(

                    text =
                        totalRegistros.toString(),

                    color =
                        Color.White,

                    fontSize =
                        45.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(15.dp)
        )


        // ==================================================
        // ESTADO
        // ==================================================

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                CardDefaults.cardColors(

                    containerColor =
                        Color(0xFF17243A)
                )
        ) {

            Column(

                modifier =
                    Modifier.padding(25.dp)
            ) {

                Text(

                    text =
                        "Estado",

                    color =
                        Color(0xFFAAB5C8),

                    fontSize =
                        15.sp
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(

                    text =
                        if (totalRegistros > 0)
                            "Actividad registrada"
                        else
                            "Sin actividad registrada",

                    color =
                        Color.White,

                    fontSize =
                        20.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(15.dp)
        )


        // ==================================================
        // REGISTROS
        // ==================================================

        registros
            .reversed()
            .forEach { registro ->

                RegistroItem(
                    registro =
                        registro
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )
            }
    }
}