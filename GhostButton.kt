// GhostButton.kt - Reusable proof + retrain button
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GhostButton(onProof: () -> Unit, onRetrain: () -> Unit = {}) {
    var isProven by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                isProven = true
                onProof()
                CoroutineScope(Dispatchers.Main).launch {
                    delay(20000)
                    isProven = false
                }
            },
            containerColor = if (isProven) Color.Green else Color.Gray,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset { IntOffset(offsetX.toInt(), 0) }
                .size(64.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount < -100) { // left swipe detected
                            onRetrain()
                            offsetX = 0f
                        } else {
                            offsetX += dragAmount
                        }
                    }
                }
        ) {
            Icon(Icons.Default.Check, contentDescription = "Prove I'm real")
        }
    }
}
