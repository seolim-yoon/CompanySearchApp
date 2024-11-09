package com.example.companysearchapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.companysearchapp.R
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme

@Composable
internal fun SearchBarItem(
    keyword: String,
    onValueChange: (String) -> Unit,
    onClickClearBtn: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8dp)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_12dp),
                vertical = dimensionResource(R.dimen.padding_8dp)
            )
    ) {
        TextField(
            value = keyword,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.search_place_holder)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onClickClearBtn
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewSearchBarItem() {
    CompanySearchAppTheme {
        SearchBarItem(
            keyword = "",
            onValueChange = { },
            onClickClearBtn = { }
        )
    }
}
