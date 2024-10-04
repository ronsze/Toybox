package kr.sdbk.toybox

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainView(
    mainNavController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(Features.entries) {
            FeatureItem(
                feature = it,
                modifier = Modifier.clickable {
                    mainNavController.navigate(it.name)
                }
            )
        }
    }
}

@Composable
private fun FeatureItem(
    feature: Features,
    modifier: Modifier
) {
    Image(
        painter = painterResource(id = feature.icon),
        contentDescription = "",
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(10.dp)
    )
}
