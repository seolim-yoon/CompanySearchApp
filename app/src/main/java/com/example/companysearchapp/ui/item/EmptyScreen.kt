package com.example.companysearchapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
internal fun EmptyScreen(
    keyword: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = if (keyword.isNotEmpty()) {
                stringResource(R.string.empty_view_message, keyword)
            } else {
                stringResource(R.string.search_place_holder)
            },
            style = CompanySearchAppTheme.typography.body16,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyScreen() {
    CompanySearchAppTheme {
        EmptyScreen(
            keyword = "키워드"
        )
    }
}