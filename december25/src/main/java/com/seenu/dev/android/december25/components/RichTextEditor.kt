package com.seenu.dev.android.december25.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RichTextEditor(
    state: RichTextEditorState,
    onStateChange: (RichTextEditorState) -> Unit,
    placeholder: @Composable () -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = TextFieldValue(
            annotatedString = state.text,
            selection = state.selection
        ),
        onValueChange = {
            onStateChange(
                RichTextEditorState(
                    text = it.annotatedString,
                    selection = it.selection
                )
            )
        },
        modifier = modifier,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            if (state.text.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    placeholder()
                }
            }

            innerTextField()
        }

    )
}

data class RichTextEditorState(
    val text: AnnotatedString,
    val selection: TextRange = TextRange.Zero
)