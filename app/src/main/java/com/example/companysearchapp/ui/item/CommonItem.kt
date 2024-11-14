package com.example.companysearchapp.ui.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.companysearchapp.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun AsyncImageItem(
    thumbnailUrl: String,
    originUrl: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        GlideImage(
            model = originUrl,
            contentDescription = null,
            contentScale = contentScale,
            failure = placeholder(R.drawable.ic_launcher_foreground),
            modifier = modifier
        ) {
            it.thumbnail(
                Glide.with(context)
                    .load(thumbnailUrl)
            )
        }
    }
}