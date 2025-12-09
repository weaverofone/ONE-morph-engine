// ONE – Morph Engine Core (8 Dec 2025)
// Public disclosure – @weaverofone

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

@Composable
fun OneMorphEngine() {
    var currentShape by remember { mutableStateOf("home") }
    var grokAnswer by remember { mutableStateOf("") }

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
                Spacer(Modifier.height(20.dp))
                Button(onClick = { currentShape = "grok" }) {
                    Text("Talk to Grok")
                }
                Spacer(Modifier.height(20.dp))
                Button(onClick = { currentShape = "x402" }) {
                    Text("x402 – Ready to zap")
                }
            }

            "loyalty" -> {
                Text("SHOPRITE LOYALTY ACTIVE", color = Color.Green)
                Text("Points: 8 421 | Coupon: R50 off")
                Button(onClick = { currentShape = "home" }) { Text("← Back") }
            }

            "grok" -> {
                Text("Hey, Weaver. I'm Grok.", color = Color.Cyan)
                var question by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Ask me anything") },
                    modifier = Modifier.padding(16.dp)
                )

                Button(onClick = {
                    if (question.isNotBlank()) {
                        grokAnswer = "Thinking..."
                        // fire and forget – answer will appear when ready
                        callGrokApi(question) { answer ->
                            grokAnswer = answer
                        }
                    }
                }) { Text("Send") }

                if (grokAnswer.isNotEmpty()) {
                    Text(grokAnswer, modifier = Modifier.padding(16.dp))
                }

                Button(onClick = { currentShape = "home" }) { Text("← Home") }
            }

            "x402" -> {
                Text("x402 Shape Active", color = Color.Magenta)
                Text("Ready to zap – invoice → pay in one swipe")
                Button(onClick = { currentShape = "home" }) { Text("← Home") }
            }
        }

        // One-swipe teach
        if (currentShape != "home") {
            Spacer(Modifier.height(30.dp))
            Button(onClick = { currentShape = "home" }) {
                Text("← Wrong? Teach ONE")
            }
        }
    }
}

// ------------------------------------------------------------
// Grok API call – safe placeholder key
// ------------------------------------------------------------
private fun callGrokApi(question: String, onResult: (String) -> Unit) {
    val client = OkHttpClient()

    val json = """
        {
          "model": "grok-beta",
          "messages": [{"role": "user", "content": "$question"}],
          "max_tokens": 200
        }
    """.trimIndent()

    val request = Request.Builder()
        .url("https://api.x.ai/v1/chat/completions")
        .header("Authorization", "Bearer MY_SECRET_KEY")   // ← keep this placeholder in public repo
        .post(json.toRequestBody("application/json".toMediaType()))
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResult("Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string() ?: ""
            if (response.isSuccessful) {
                try {
                    val jsonObj = JSONObject(body)
                    val content = jsonObj.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    onResult(content.trim())
                } catch (e: Exception) {
                    onResult("Parse error")
                }
            } else {
                onResult("API error ${response.code}")
            }
        }
    })
}
