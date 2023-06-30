package com.andresen.library_style.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun TrainingPartnerComposableTheme(
    trainingPartnerColors: TrainingPartnerColors = createBaseBlueColors(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides trainingPartnerColors,
        LocalTrainingPartnerTypography provides createTrainingPartnerSansTypography(),
        LocalTrainingPartnerShapes provides createTrainingPartnerShapes(),

        LocalIndication provides rememberRipple(),

        content = { content() }
    )
}

object TrainingPartnerTheme {
    val colors: TrainingPartnerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: TrainingPartnerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTrainingPartnerTypography.current

    val shapes: TrainingPartnerShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalTrainingPartnerShapes.current
}