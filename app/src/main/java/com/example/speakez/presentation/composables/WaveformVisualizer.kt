package com.example.speakez.presentation.composables

import android.R.attr.spacing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun WaveformVisualizer(amplitudes: List<Float>) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) { 
        val lineCount = 100
        val spacing = size.width / lineCount
        val maxLineHeight = size.height

        amplitudes.takeLast(lineCount).forEachIndexed { index, amplitude ->
            val x = index * spacing
            val lineHeight = (amplitude * maxLineHeight).coerceIn(0f, maxLineHeight)
            val y = (size.height - lineHeight) / 2

            drawLine(
                color = primaryColor,
                start = Offset(x, y),
                end = Offset(x, y + lineHeight),
                strokeWidth = spacing / 2,
                cap = StrokeCap.Round
            )
        }
    }
}
