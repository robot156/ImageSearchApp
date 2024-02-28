package com.example.imagesearchapp.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.imagesearchapp.designsystem.theme.ImageSearchTheme

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
            contentColor = Color.White
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
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
    )
}

@Preview(name = "light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ImageSearchOutlineButtonPreview() {
    ImageSearchTheme {
        Column {
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