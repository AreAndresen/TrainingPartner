package com.andresen.feature_map.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.andresen.feature_map.viewmodel.MapViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.koin.androidx.compose.koinViewModel


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    // viewModel: MapViewModel = koinViewModel()
    /*onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
    onFriendlyInfoWindowClick: (MarkerUi) -> Unit = { },
    onCreateMarkerLongClick: (LatLng) -> Unit = { },*/
) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Chat Screen: ", textAlign = TextAlign.Center)
        }
    }

    /*val mapUiState by viewModel.state.collectAsState(MapMapper.loading())

    val uiState = when (val contentUi = mapUiState.mapContent) {
        is MapContentUi.MapContent -> contentUi
        else -> null
    }

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

    val cameraPositionState = if (uiState != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                uiState.zoomLocation, 15f
            )
        }
    } else rememberCameraPositionState()


    Scaffold(
        backgroundColor = OverwatchTheme.colors.medium,
        contentColor = OverwatchTheme.colors.contrastLight,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBarComposable(
                isNightVision = mapUiState.mapTopAppBar.isNightVision,
                onToggleNightVision = { viewModel.onEvent(MapEvent.ToggleNightVision) },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = {
                    scope.launch {
                        if (uiState != null) {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(
                                    uiState.zoomLocation,
                                    15f
                                ),
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unit),
                        contentDescription = stringResource(id = R.string.map_locate_target),
                        tint = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.map_locate_target),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = OverwatchTheme.colors.mediumLight10,
                contentColor = OverwatchTheme.colors.contrastLight,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            properties = uiState?.properties ?: MapProperties(),
            uiSettings = uiSettings,
            onMapLongClick = {
                onCreateMarkerLongClick(it)
            }
        ) {
            when (val contentUi = mapUiState.mapContent) {
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
            }
        }
    }
}

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
    ) */
}

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