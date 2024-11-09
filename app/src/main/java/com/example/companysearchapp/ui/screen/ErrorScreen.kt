package com.example.companysearchapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme


@Composable
internal fun ErrorScreen(
    errorMessage: String,
    onRefresh: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = stringResource(R.string.refresh),
            color = CompanySearchAppTheme.colors.white,
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(dimensionResource(R.dimen.radius_8dp)),
                    color = CompanySearchAppTheme.colors.lightGray
                )
                .clickable {
                    onRefresh()
                }
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_12dp),
                    vertical = dimensionResource(R.dimen.padding_8dp)
                )
        )

        Text(
            text = errorMessage,
            style = CompanySearchAppTheme.typography.body16,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorScreen() {
    CompanySearchAppTheme {
        ErrorScreen(
            errorMessage = "에러 발생!",
            onRefresh = {}
        )
    }
}