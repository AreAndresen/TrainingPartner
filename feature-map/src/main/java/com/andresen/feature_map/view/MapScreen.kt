package com.andresen.feature_map.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.andresen.feature_map.viewmodel.MapViewModel
import com.andresen.library_style.theme.TrainingPartnerTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = koinViewModel()
    /*onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
    onFriendlyInfoWindowClick: (MarkerUi) -> Unit = { },
    onCreateMarkerLongClick: (LatLng) -> Unit = { },*/
) {

    /*val mapUiState by viewModel.state.collectAsState(MapMapper.loading())

    val uiState = when (val contentUi = mapUiState.mapContent) {
        is MapContentUi.MapContent -> contentUi
        else -> null
    }*/

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = true,
                compassEnabled = true
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0), 15f
        )
    } // else rememberCameraPositionState()


    Scaffold(
        backgroundColor = TrainingPartnerTheme.colors.medium,
        contentColor = TrainingPartnerTheme.colors.contrastLight,
        scaffoldState = scaffoldState,
        /*topBar = {
            TopAppBarComposable(
                isNightVision = mapUiState.mapTopAppBar.isNightVision,
                onToggleNightVision = { viewModel.onEvent(MapEvent.ToggleNightVision) },
            )
        },*/
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = {
                    scope.launch {
                        if (false != null) {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(
                                    LatLng(0.0, 0.0),//uiState.zoomLocation,
                                    15f
                                ),
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = com.andresen.library_style.R.drawable.map),
                        contentDescription = stringResource(id = com.andresen.library_style.R.string.map),
                        tint = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = com.andresen.library_style.R.string.map),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = TrainingPartnerTheme.colors.mediumLight10,
                contentColor = TrainingPartnerTheme.colors.contrastLight,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            ), //uiState?.properties ?: MapProperties(),
            uiSettings = uiSettings,
            onMapLongClick = {
                //onCreateMarkerLongClick(it)
            }
        ) {
            /*when (val contentUi = mapUiState.mapContent) {
                is MapContentUi.MapContent -> {
                    contentUi.markers.forEach { marker ->
                        CreateMarker(
                            marker,
                            onDeleteMarkerOnInfoBoxLongClick,
                            onFriendlyInfoWindowClick
                        )
                    }
                }

                is MapContentUi.Error -> {}
                is MapContentUi.Loading -> {}
            }*/
        }
    }
}

/*
@Composable
private fun CreateMarker(
    marker: MarkerUi,
    onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
    onInfoWindowClick: (MarkerUi) -> Unit = { },
) {
    Marker(
        state = MarkerState(position = LatLng(marker.lat, marker.lng)),
        title = if (marker.friendly) {
            stringResource(id = R.string.map_marker_friendly)
        } else stringResource(
            id = R.string.map_marker_target
        ),
        snippet = "${marker.lat}, ${marker.lng}",
        onInfoWindowLongClick = {
            if (!marker.friendly) {
                onDeleteMarkerOnInfoBoxLongClick(marker)
            } else {
                onInfoWindowClick(marker)
            }
        },
        draggable = true,
        onClick = {
            it.showInfoWindow()
            true
        },
        icon = if (marker.friendly) {
            bitmapFromVector(LocalContext.current, R.drawable.unit_marker_38)
                ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        } else {
            bitmapFromVector(LocalContext.current, R.drawable.tarket_marker_38)
                ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    )
} */

private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    val bitmap: Bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas: Canvas = Canvas(bitmap)

    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}