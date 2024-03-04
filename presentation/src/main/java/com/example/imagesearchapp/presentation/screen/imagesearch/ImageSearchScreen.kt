package com.example.imagesearchapp.presentation.screen.imagesearch

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imagesearchapp.designsystem.common.TextFieldState
import com.example.imagesearchapp.designsystem.component.ImageSearchPrimaryButton
import com.example.imagesearchapp.designsystem.component.SearchTextField
import com.example.imagesearchapp.designsystem.theme.ImageSearchTheme
import com.example.imagesearchapp.presentation.R

@Composable
internal fun ImageSearchRoute(
    viewModel: ImageSearchViewModel = hiltViewModel(),
    onSearchClick: (String) -> Unit
) {
    val searchQuery by viewModel.keyword.collectAsStateWithLifecycle()
    val onSearchQueryChanged = remember<(String) -> Unit> { viewModel::setKeyword }

    ImageSearchScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 36.dp),
        searchQueryState = remember { SearchState() },
        onSearchQueryChanged = onSearchQueryChanged,
        onSearchClick = { onSearchClick(searchQuery ?: "") }
    )
}

@Composable
fun ImageSearchScreen(
    modifier: Modifier = Modifier,
    searchQueryState: TextFieldState = remember { SearchState() },
    onSearchQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(modifier) {
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = ImageVector.run { vectorResource(id = R.drawable.ic_unsplash) },
            contentDescription = "unsplash logo",
            tint = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchTextField(
            textState = searchQueryState,
            hintText = stringResource(R.string.search_hint),
            onSearchQueryChanged = onSearchQueryChanged
        )

        Spacer(modifier = Modifier.height(30.dp))

        ImageSearchPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = searchQueryState.isValid,
            onClick = onSearchClick
        ) {
            Text(
                text = stringResource(id = R.string.search_start),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(name = "light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageSearchScreenPreview() {
    ImageSearchTheme {
        ImageSearchScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 36.dp),
            searchQueryState = remember { SearchState() },
            onSearchQueryChanged = {}
        ) {

        }
    }
}