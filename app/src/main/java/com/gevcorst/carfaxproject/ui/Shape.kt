package com.gevcorst.carfaxproject.ui

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp),
)
val Shapes.myshape:Shapes
@Composable
get() = Shapes(  small = RoundedCornerShape(4.dp),
    medium = CutCornerShape(topEnd = 16.dp),
    large = RoundedCornerShape(0.dp))