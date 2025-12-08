// ONE – Morph Engine Core (47 lines – 8 Dec 2025)
// Public disclosure – @weaverofone

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OneMorphEngine() {
    var currentShape by remember { mutableStateOf("home") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (currentShape) {
            "home" -> {
                Text("Open camera → ONE becomes loyalty scanner")
                Button(onClick = { currentShape = "loyalty" }) {
                    Text("Open Camera → Morph")
                }
            }
            "loyalty" -> {
                Text("SHOPRITE LOYALTY ACTIVE", color = Color.Green)
                Text("Points: 8 421 | Coupon: R50 off")
                Button(onClick = { currentShape = "home" }) { Text("← Back") }
            }
        }
        if (currentShape != "home") {
            Button(onClick = { currentShape = "home" }) { Text("← Wrong? Teach ONE") }
        }
    }
}
