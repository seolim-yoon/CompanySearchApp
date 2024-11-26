package com.example.companysearchapp.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme
import com.example.companysearchapp.uimodel.ImageUiModel
import com.example.companysearchapp.util.IMAGE_AUTO_SWIPE
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
internal fun ImagePagerItem(
    imageList: List<ImageUiModel>
) {
    val pageCount = imageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(key1 = pagerState.currentPage) {
        while (true) {
            delay(IMAGE_AUTO_SWIPE)
            withContext(NonCancellable) {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            key = { imageList[it].id },
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->

            AsyncImageItem(
                thumbnailUrl = imageList[page].thumbnail,
                originUrl = imageList[page].origin,
                contentScale = ContentScale.Crop
            )
        }
        if (imageList.size > 1) {
            BannerIndicator(
                currentIdx = pagerState.currentPage + 1,
                totalCount = imageList.size,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.padding_16dp))
            )
        }
    }
}

@Composable
internal fun BannerIndicator(
    currentIdx: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$currentIdx / $totalCount",
        color = Color.White,
        style = CompanySearchAppTheme.typography.body14,
        modifier = modifier
            .background(
                color = CompanySearchAppTheme.colors.gray.copy(alpha = 0.6f),
                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp))
            )
            .padding
                (
                vertical = dimensionResource(R.dimen.padding_4dp),
                horizontal = dimensionResource(R.dimen.padding_8dp)
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewImagePagerItem() {
    CompanySearchAppTheme {
        ImagePagerItem(
            imageList = listOf(
                ImageUiModel(
                    id = 1,
                    isTitle = false,
                    origin = "https://static.wanted.co.kr/images/company/20662/ckazubyqigyqb515__1080_790.jpg",
                    thumbnail = "https://static.wanted.co.kr/images/company/20662/ckazubyqigyqb515__400_400.jpg"
                ),
                ImageUiModel(
                    id = 2,
                    isTitle = false,
                    origin = "https://static.wanted.co.kr/images/company/20662/mmihn75ehogygquo__1080_790.jpg",
                    thumbnail = "https://static.wanted.co.kr/images/company/20662/mmihn75ehogygquo__400_400.jpg"
                ),
                ImageUiModel(
                    id = 3,
                    isTitle = false,
                    origin = "https://static.wanted.co.kr/images/company/20662/hkodnenphw4fddjq__1080_790.jpg",
                    thumbnail = "https://static.wanted.co.kr/images/company/20662/hkodnenphw4fddjq__400_400.jpg"
                )
            ))
    }
}