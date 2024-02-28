package com.example.imagesearchapp.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagesearchapp.designsystem.common.TextFieldState
import com.example.imagesearchapp.designsystem.theme.ImageSearchTheme
import com.example.imagesearchapp.designsystem.theme.Shapes

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    hintText: String,
    textState: TextFieldState = remember { TextFieldState() },
    imeAction: ImeAction = ImeAction.Search,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    textState.enableShowErrors()
                }
            },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = 12.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "",
            )
        },
        value = textState.text,
        onValueChange = {
            textState.text = it
            textState.enableShowErrors()
        },
        label = {
            Text(
                text = hintText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = textState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Text
        ),
        shape = Shapes.extraLarge,
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Preview(name = "light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchTextField() {
    ImageSearchTheme {
        SearchTextField(
            hintText = "찾으시는 이미지를 검색해보세요.",
            textState = TextFieldState(
                validator = { false },
                errorFor = { "false " }
            )
        )
    }
}