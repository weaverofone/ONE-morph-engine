// main.kt - ONE Morph Engine Core (Expanded Dec 11 2025)
// Public disclosure – @weaverofone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    OneMorphEngine()
                }
            }
        }
    }
}

@Composable
fun OneMorphEngine() {
    var currentShape by remember { mutableStateOf("home") }
    val coroutineScope = rememberCoroutineScope()

    // Auto-return to home after 30 seconds inactivity
    LaunchedEffect(currentShape) {
        if (currentShape != "home") {
            delay(30000)
            currentShape = "home"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentShape) {
                "home" -> {
                    Text("Open camera → ONE becomes loyalty scanner", color = Color.White)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { currentShape = "loyalty" }) {
                        Text("Open Camera → Morph")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { currentShape = "grok" }) {
                        Text("Talk to Grok")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { currentShape = "x402" }) {
                        Text("x402 – Ready to zap")
                    }
                }

                "loyalty" -> {
                    Text("SHOPRITE LOYALTY ACTIVE", color = Color.Green)
                    Text("Points: 8 421 | Coupon: R50 off", color = Color.White)
                }

                "grok" -> {
                    Text("Hey, Weaver. I'm Grok.", color = Color.Cyan, style = MaterialTheme.typography.headlineMedium)
                    Text("Ready to build.", color = Color.White)
                }

                "x402" -> {
                    Text("x402 Shape Active", color = Color.Magenta, style = MaterialTheme.typography.headlineMedium)
                    Text("Ready to zap – invoice → pay in one swipe", color = Color.White)
                }
            }

            if (currentShape != "home") {
                Spacer(Modifier.height(32.dp))
                Button(onClick = { currentShape = "home" }) {
                    Text("← Wrong? Teach ONE")
                }
            }
        }

        // Floating Ghost Button with proof + retrain swipe
        GhostButton(
            onProof = { /* Future: cryptographic proof */ },
            onRetrain = {
                coroutineScope.launch {
                    currentShape = "home"
                    // Future: trigger local LoRA retrain here
                }
            }
        )
    }
}
