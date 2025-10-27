package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.MiembroEquipo
import com.example.bargaming_grupo4.R

@Composable
fun NosotrosScreen(
) {
    val bg = MaterialTheme.colorScheme.surfaceVariant

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo(
            Modifier
                .size(80.dp)
                .padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Nosotros",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(24.dp))

        Card(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, bg),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Bienvenido a BarGaming",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Una empresa de gamers para gamers. Porque sabemos lo que necesitas y para qué lo necesitas. Cada producto de nuestra tienda se convierte en el deseo de muchos jugadores debido a su gran calidad y, tanto vendedor como comprador, se sienten cómodos con la ayuda que ofrecemos como comunidad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Text(
                    text = "Nuestra historia",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Desde sus inicios, nuestra empresa se ha dedicado a ofrecer productos de calidad. A lo largo de los años, hemos crecido gracias al compromiso con nuestros clientes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Text(
                    text = "Nuestra Misión",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Proveer soluciones tecnológicas innovadoras que mejoren la vida de las personas y promuevan el desarrollo sostenible.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            MiembroEquipo(
                nombre = "Anthony Adasme",
                puesto = "CEO",
                imagen = R.drawable.profile1
            )
            MiembroEquipo(
                nombre = "Brandon Yáñez",
                puesto = "CTO",
                imagen = R.drawable.profile2
            )
        }
    }
}