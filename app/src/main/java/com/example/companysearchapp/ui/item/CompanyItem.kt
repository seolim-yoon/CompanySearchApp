package com.example.companysearchapp.ui.item

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme
import com.example.companysearchapp.uimodel.CompanyUiModel
import com.example.companysearchapp.uimodel.ImageUiModel

@Composable
internal fun CompanyItem(
    company: CompanyUiModel,
    onClickCompanyItem:(CompanyUiModel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_8dp),
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClickCompanyItem(company)
            }
            .padding(
                horizontal = dimensionResource(R.dimen.padding_12dp),
                vertical = dimensionResource(R.dimen.padding_16dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_12dp)),
        ) {
            AsyncImageItem(
                imgUrl = company.logoImg.thumbnail,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(60.dp)
                    .clip(
                        shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp))
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp)),
                        color = CompanySearchAppTheme.colors.lightGray
                    )
            )
            Text(
                text = company.title,
                style = CompanySearchAppTheme.typography.heading20,
                color = CompanySearchAppTheme.colors.gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = company.description,
            style = CompanySearchAppTheme.typography.body14,
            color = CompanySearchAppTheme.colors.lightGray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCompanyItem() {
    CompanySearchAppTheme {
        CompanyItem(
            company = CompanyUiModel(
                1,
                ImageUiModel(origin = "https://static.wanted.co.kr/nextweek/images/wdes/0_5.7d6ea9b0.test_nextweek.jpg", thumbnail = ""),
                "원티드랩",
                "원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩원티드랩 원티드랩 원티드랩 원티드랩 원티드랩 원티드랩",
            ),
            onClickCompanyItem = {}
        )
    }
}
