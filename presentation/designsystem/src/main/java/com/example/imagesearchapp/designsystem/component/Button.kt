package com.example.imagesearchapp.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagesearchapp.designsystem.theme.ImageSearchTheme
import com.example.imagesearchapp.designsystem.theme.gray
import com.example.imagesearchapp.designsystem.theme.white

@Composable
fun ImageSearchPrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = gray,
            contentColor = white
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun ImageSearchOutlineButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        content = content,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
    )
}

@Preview(name = "light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ImageSearchButtonPreview() {
    ImageSearchTheme {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImageSearchPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                enabled = true
            ) {
                Text(
                    text = "검색",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ImageSearchPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                enabled = false
            ) {
                Text(
                    text = "검색",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ImageSearchOutlineButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(
                    text = "검색",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}