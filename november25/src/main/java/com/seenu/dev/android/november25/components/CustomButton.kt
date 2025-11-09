package com.seenu.dev.android.november25.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    drawBorder: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .let {
                if (drawBorder) {
                    it.border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
                } else {
                    it
                }
            }
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.Button
            )
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}