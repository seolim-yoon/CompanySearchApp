package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.companysearchapp.DetailUiState
import com.example.companysearchapp.LoadState
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.item.AsyncImageItem
import com.example.companysearchapp.ui.item.ImagePagerItem
import com.example.companysearchapp.ui.item.LinkItem
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme

@Composable
internal fun DetailScreen(
    uiState: DetailUiState,
    onRefresh: () -> Unit,
    onClickLinkItem: (String) -> Unit
) {
    when (uiState.detailLoadState) {
        is LoadState.Loading -> LinearProgressIndicator()
        is LoadState.Success -> {
            val company = uiState.detailLoadState.data

            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_12dp)),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Box {
                    ImagePagerItem(
                        imageList = company.companyImageUrl
                    )
                    AsyncImageItem(
                        imgUrl = company.logoUrl.thumbnail,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .offset(y = 40.dp)
                            .align(Alignment.BottomStart)
                            .padding(start = dimensionResource(R.dimen.padding_24dp))
                            .size(80.dp)
                            .clip(
                                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp))
                            )
                            .border(
                                width = dimensionResource(R.dimen.width_1dp),
                                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp)),
                                color = CompanySearchAppTheme.colors.lightGray
                            )

                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_12dp)),
                    modifier = Modifier.padding(
                        vertical = 50.dp + dimensionResource(R.dimen.padding_24dp),
                        horizontal = dimensionResource(R.dimen.padding_24dp)
                    )
                ) {

                    Text(
                        text = company.title,
                        style = CompanySearchAppTheme.typography.heading20,
                        color = CompanySearchAppTheme.colors.gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = company.description,
                        style = CompanySearchAppTheme.typography.body16,
                        color = CompanySearchAppTheme.colors.lightGray,
                    )

                    if (company.link.isNotEmpty()) {
                        LinkItem(
                            strRes = R.string.str_link,
                            link = company.link,
                            onClickLinkItem = onClickLinkItem
                        )
                    }

                    if (company.url.isNotEmpty()) {
                        LinkItem(
                            strRes = R.string.str_url,
                            link = company.url,
                            onClickLinkItem = onClickLinkItem
                        )
                    }
                }
            }
        }

        is LoadState.Error -> {
            ErrorScreen(
                errorMessage = uiState.detailLoadState.error.message.toString(),
                onRefresh = onRefresh
            )
        }
    }
}
