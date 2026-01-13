package com.seenu.dev.android.december25.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Delete
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.seenu.dev.android.december25.components.RichTextEditorState
import com.seenu.dev.android.december25.state.Font
import com.seenu.dev.android.december25.state.FontSize
import com.seenu.dev.android.december25.state.AppliedTextFormats
import com.seenu.dev.android.december25.theme.Montserrat
import com.seenu.dev.android.december25.theme.PTSans
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WinterGreetingEditorViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateEditorState(newState: RichTextEditorState) {
        _uiState.value = _uiState.value.copy(
            state = newState
        )
    }

    fun onIntent(intent: WinterGreetingEditorIntent) {
        when (intent) {
            WinterGreetingEditorIntent.Bold -> {
                val selection = _uiState.value.state.selection
            }
            WinterGreetingEditorIntent.Italic -> TODO()
            WinterGreetingEditorIntent.Underline -> TODO()
        }
    }

}


sealed interface WinterGreetingEditorIntent {
    data object Bold : WinterGreetingEditorIntent
    data object Italic : WinterGreetingEditorIntent
    data object Underline : WinterGreetingEditorIntent
}

data class UiState constructor(
    val state: RichTextEditorState = RichTextEditorState(
        text = buildAnnotatedString { },
    ),
    val isBoldEnabled: Boolean = false,
    val isItalicEnabled: Boolean = false,
    val isUnderlineEnabled: Boolean = false,
)

class RichTextFormatData {

    private var text = ""

    private val formats: MutableList<AppliedTextFormats> = mutableListOf()

    fun onEditorAction(text: String) {
        val oldText = this.text

        val prefix = commonPrefixLength(oldText, text)
        val suffix = commonSuffixLength(oldText, text, prefix)

        val oldMiddle = oldText.substring(prefix, oldText.length - suffix)
        val newMiddle = text.substring(prefix, text.length - suffix)

        if (oldMiddle.isEmpty() && newMiddle.isNotEmpty()) {
//            Insert(
//                index = prefix,
//                text = newMiddle
//            )
        }
        if (oldMiddle.isNotEmpty() && newMiddle.isEmpty()) {
//            Delete(
//                index = prefix,
//                length = oldMiddle.length
//            )
        }
        if (oldMiddle.isNotEmpty() && newMiddle.isNotEmpty()) {
//            Replace(
//                index = prefix,
//                old = oldMiddle,
//                new = newMiddle
//            )
        }
    }

    private fun commonPrefixLength(a: String, b: String): Int {
        val min = minOf(a.length, b.length)
        for (i in 0 until min) {
            if (a[i] != b[i]) return i
        }
        return min
    }

    private fun commonSuffixLength(
        a: String,
        b: String,
        prefix: Int
    ): Int {
        var i = a.length - 1
        var j = b.length - 1
        var count = 0

        while (i >= prefix && j >= prefix && a[i] == b[j]) {
            count++
            i--
            j--
        }
        return count
    }


    fun build(data: RichTextFormatData): AnnotatedString {
        return buildAnnotatedString {
            this.append(data.text)

//            for ((range, formats) in data.getFormats()) {
//                for (format in formats) {
//                    val spanStyle = when (format) {
//                        RichTextFormatType.Underline -> SpanStyle(
//                            textDecoration = Underline
//                        )
//
//                        RichTextFormatType.Bold -> SpanStyle(
//                            fontWeight = FontWeight.Bold
//                        )
//
//                        RichTextFormatType.Italic -> SpanStyle(
//                            fontStyle = FontStyle.Italic
//                        )
//
//                        is RichTextFormatType.TextColor -> {
//                            val color = format.color
//                            SpanStyle(
//                                color = color
//                            )
//                        }
//
//                        is RichTextFormatType.FontSize -> {
//                            val fontSize = when (format.size) {
//                                FontSize.SMALL -> 12.sp
//                                FontSize.MEDIUM -> 18.sp
//                                FontSize.LARGE -> 34.sp
//                            }
//                            SpanStyle(
//                                fontSize = fontSize
//                            )
//                        }
//
//                        is RichTextFormatType.Font -> {
//                            val fontFamily = when (format.family) {
//                                Font.MONTSERRAT -> Montserrat
//                                Font.PT_SANS -> PTSans
//                            }
//                            SpanStyle(
//                                fontFamily = fontFamily
//                            )
//                        }
//                    }
//
//                    addStyle(
//                        style = spanStyle,
//                        start = range.first,
//                        end = range.second
//                    )
//                }
//            }
        }
    }
}

sealed interface RichTextFormatType {
    data object Bold : RichTextFormatType
    data object Italic : RichTextFormatType
    data object Underline : RichTextFormatType
    data class TextColor(val color: Color) : RichTextFormatType
    data class Font(val family: com.seenu.dev.android.december25.state.Font) : RichTextFormatType
    data class FontSize(val size: com.seenu.dev.android.december25.state.FontSize) :
        RichTextFormatType
}