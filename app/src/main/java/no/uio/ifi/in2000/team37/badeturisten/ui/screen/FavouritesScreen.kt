package no.uio.ifi.in2000.team37.badeturisten.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouritesScreen(navController: NavController) {
    Scaffold (
        bottomBar = { BottomBar(navController = navController) }
    ) { padding  ->
        Text(
            text = "dette er favoritt screen",
        modifier = Modifier.padding(padding))
    }
}