package com.andresen.library_style.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalTrainingPartnerShapes = staticCompositionLocalOf<TrainingPartnerShapes> {
    error("LocalShapes not initialized!")
}

data class TrainingPartnerShapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape
)

internal fun createTrainingPartnerShapes() = TrainingPartnerShapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(6.dp)
)