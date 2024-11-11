package com.example.companysearchapp.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import com.example.companysearchapp.ui.theme.CompanySearchAppTheme
import com.example.companysearchapp.util.LINK_URL


@Composable
internal fun LinkItem(
    strRes: Int,
    link: String,
    onClickLinkItem: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(strRes))
        append(link)
        addStyle(
            style = CompanySearchAppTheme.typography.link16.copy(
                color = CompanySearchAppTheme.colors.blue
            ),
            start = stringResource(strRes).length,
            end = this.length
        )
        addStringAnnotation(
            tag = LINK_URL,
            annotation = link,
            start = stringResource(strRes).length,
            end = this.length
        )
    }

    Text(
        text = annotatedString,
        style = CompanySearchAppTheme.typography.body16,
        color = CompanySearchAppTheme.colors.gray,
        modifier = Modifier.clickable {
            annotatedString.getStringAnnotations(
                tag = LINK_URL,
                start = 0,
                end = annotatedString.length
            )
                .firstOrNull()?.let { annotation ->
                    onClickLinkItem(annotation.item)
                }
        }
    )
}