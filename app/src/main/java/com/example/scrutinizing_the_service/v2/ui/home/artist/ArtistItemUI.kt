package com.example.scrutinizing_the_service.v2.ui.home.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.data.models.remote.saavn.ArtistData
import com.example.scrutinizing_the_service.v2.ui.common.ContentLoaderUI
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ArtistItemUI(
    artist: ArtistData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = {
                artist.image.last().link
            },
            modifier = Modifier
                .clip(CircleShape)
                .aspectRatio(1f),
            failure = {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_album_place_holder
                    ),
                    contentDescription = "Album place holder",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .background(Color.Gray)
                        .padding(12.dp)
                )
            },
            loading = {
                ContentLoaderUI(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        )

        Text(
            text = artist.name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            minLines = 2
        )
    }

}