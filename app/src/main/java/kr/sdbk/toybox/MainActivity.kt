package kr.sdbk.toybox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import kr.sdbk.common.ui.theme.ToyboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToyboxTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToyboxApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}