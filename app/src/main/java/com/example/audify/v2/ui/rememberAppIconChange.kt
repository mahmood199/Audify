package com.example.audify.v2.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audify.v2.theme.AudifyTheme


@Composable
fun rememberAppIconChange(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "AppIconChange",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 452.025f,
            viewportHeight = 452.025f
        ).apply {
            group {
                group {
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(362.612f, 34.125f)
                        horizontalLineToRelative(-55.2f)
                        lineToRelative(13.6f, -13.6f)
                        curveToRelative(4.7f, -4.7f, 4.7f, -12.3f, 0f, -17f)
                        reflectiveCurveToRelative(-12.3f, -4.7f, -17f, 0f)
                        lineToRelative(-34f, 34.1f)
                        curveToRelative(-2.3f, 2.3f, -3.5f, 5.3f, -3.5f, 8.5f)
                        reflectiveCurveToRelative(1.3f, 6.2f, 3.5f, 8.5f)
                        lineToRelative(34.1f, 34.1f)
                        curveToRelative(2.3f, 2.3f, 5.4f, 3.5f, 8.5f, 3.5f)
                        reflectiveCurveToRelative(6.1f, -1.2f, 8.5f, -3.5f)
                        curveToRelative(4.7f, -4.7f, 4.7f, -12.3f, 0f, -17f)
                        lineToRelative(-13.6f, -13.6f)
                        horizontalLineToRelative(55.2f)
                        curveToRelative(35.9f, 0f, 65f, 29.2f, 65f, 65f)
                        verticalLineToRelative(40.3f)
                        curveToRelative(0f, 6.6f, 5.4f, 12f, 12f, 12f)
                        reflectiveCurveToRelative(12f, -5.4f, 12f, -12f)
                        verticalLineToRelative(-40.3f)
                        curveTo(451.712f, 74.025f, 411.712f, 34.125f, 362.612f, 34.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(438.812f, 230.925f)
                        horizontalLineToRelative(-197.1f)
                        curveToRelative(-6.6f, 0f, -12f, 5.4f, -12f, 12f)
                        verticalLineToRelative(197.1f)
                        curveToRelative(0f, 6.6f, 5.4f, 12f, 12f, 12f)
                        horizontalLineToRelative(197.1f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        verticalLineToRelative(-197.1f)
                        curveTo(450.812f, 236.225f, 445.412f, 230.925f, 438.812f, 230.925f)
                        close()
                        moveTo(426.812f, 428.025f)
                        horizontalLineToRelative(-173.1f)
                        verticalLineToRelative(-173.1f)
                        horizontalLineToRelative(173.1f)
                        lineTo(426.812f, 428.025f)
                        lineTo(426.812f, 428.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(147.912f, 363.325f)
                        curveToRelative(-4.7f, -4.7f, -12.3f, -4.7f, -17f, 0f)
                        curveToRelative(-4.7f, 4.7f, -4.7f, 12.3f, 0f, 17f)
                        lineToRelative(13.6f, 13.6f)
                        horizontalLineToRelative(-55.2f)
                        curveToRelative(-35.9f, 0f, -65f, -29.2f, -65f, -65f)
                        verticalLineToRelative(-40.3f)
                        curveToRelative(0f, -6.6f, -5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        verticalLineToRelative(40.3f)
                        curveToRelative(0f, 49.1f, 39.9f, 89f, 89f, 89f)
                        horizontalLineToRelative(55.2f)
                        lineToRelative(-13.6f, 13.6f)
                        curveToRelative(-4.7f, 4.7f, -4.7f, 12.3f, 0f, 17f)
                        curveToRelative(2.3f, 2.3f, 5.4f, 3.5f, 8.5f, 3.5f)
                        reflectiveCurveToRelative(6.1f, -1.2f, 8.5f, -3.5f)
                        lineToRelative(34.1f, -34.1f)
                        curveToRelative(4.7f, -4.7f, 4.7f, -12.3f, 0f, -17f)
                        lineTo(147.912f, 363.325f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.212f, 24.025f)
                        curveToRelative(3.2f, 0f, 6.3f, -1.3f, 8.5f, -3.5f)
                        reflectiveCurveToRelative(3.5f, -5.3f, 3.5f, -8.5f)
                        curveToRelative(0f, -3.1f, -1.3f, -6.3f, -3.5f, -8.5f)
                        reflectiveCurveToRelative(-5.3f, -3.5f, -8.5f, -3.5f)
                        reflectiveCurveToRelative(-6.3f, 1.3f, -8.5f, 3.5f)
                        reflectiveCurveToRelative(-3.5f, 5.3f, -3.5f, 8.5f)
                        reflectiveCurveToRelative(1.3f, 6.3f, 3.5f, 8.5f)
                        curveTo(7.012f, 22.725f, 10.012f, 24.025f, 13.212f, 24.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(111.812f, 24.025f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        reflectiveCurveTo(105.112f, 24.025f, 111.812f, 24.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(62.512f, 24.025f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        reflectiveCurveTo(55.912f, 24.025f, 62.512f, 24.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(161.012f, 24.025f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        reflectiveCurveTo(154.412f, 24.025f, 161.012f, 24.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(210.312f, 0.025f)
                        curveToRelative(-3.1f, 0f, -6.3f, 1.3f, -8.5f, 3.5f)
                        reflectiveCurveToRelative(-3.5f, 5.3f, -3.5f, 8.5f)
                        reflectiveCurveToRelative(1.3f, 6.3f, 3.5f, 8.5f)
                        reflectiveCurveToRelative(5.3f, 3.5f, 8.5f, 3.5f)
                        reflectiveCurveToRelative(6.3f, -1.3f, 8.5f, -3.5f)
                        reflectiveCurveToRelative(3.5f, -5.3f, 3.5f, -8.5f)
                        reflectiveCurveToRelative(-1.3f, -6.3f, -3.5f, -8.5f)
                        reflectiveCurveTo(213.512f, 0.025f, 210.312f, 0.025f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(210.312f, 147.925f)
                        curveToRelative(-6.6f, 0f, -12f, 5.4f, -12f, 12f)
                        reflectiveCurveToRelative(5.4f, 12f, 12f, 12f)
                        reflectiveCurveToRelative(12f, -5.4f, 12f, -12f)
                        curveTo(222.312f, 153.225f, 217.012f, 147.925f, 210.312f, 147.925f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(210.312f, 98.625f)
                        curveToRelative(-6.6f, 0f, -12f, 5.4f, -12f, 12f)
                        reflectiveCurveToRelative(5.4f, 12f, 12f, 12f)
                        reflectiveCurveToRelative(12f, -5.4f, 12f, -12f)
                        curveTo(222.312f, 103.925f, 217.012f, 98.625f, 210.312f, 98.625f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(210.312f, 49.325f)
                        curveToRelative(-6.6f, 0f, -12f, 5.4f, -12f, 12f)
                        reflectiveCurveToRelative(5.4f, 12f, 12f, 12f)
                        reflectiveCurveToRelative(12f, -5.4f, 12f, -12f)
                        curveTo(222.312f, 54.725f, 217.012f, 49.325f, 210.312f, 49.325f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(210.312f, 197.125f)
                        curveToRelative(-3.2f, 0f, -6.3f, 1.3f, -8.5f, 3.5f)
                        reflectiveCurveToRelative(-3.5f, 5.3f, -3.5f, 8.5f)
                        curveToRelative(0f, 3.1f, 1.3f, 6.3f, 3.5f, 8.5f)
                        reflectiveCurveToRelative(5.3f, 3.5f, 8.5f, 3.5f)
                        reflectiveCurveToRelative(6.3f, -1.3f, 8.5f, -3.5f)
                        reflectiveCurveToRelative(3.5f, -5.3f, 3.5f, -8.5f)
                        reflectiveCurveToRelative(-1.3f, -6.3f, -3.5f, -8.5f)
                        curveTo(216.613f, 198.425f, 213.512f, 197.125f, 210.312f, 197.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(161.012f, 221.125f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        curveTo(149.012f, 215.825f, 154.412f, 221.125f, 161.012f, 221.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(111.812f, 221.125f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        curveTo(99.812f, 215.825f, 105.112f, 221.125f, 111.812f, 221.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(62.512f, 221.125f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        curveTo(50.512f, 215.825f, 55.912f, 221.125f, 62.512f, 221.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.212f, 221.125f)
                        curveToRelative(3.2f, 0f, 6.3f, -1.3f, 8.5f, -3.5f)
                        reflectiveCurveToRelative(3.5f, -5.3f, 3.5f, -8.5f)
                        reflectiveCurveToRelative(-1.3f, -6.3f, -3.5f, -8.5f)
                        reflectiveCurveToRelative(-5.3f, -3.5f, -8.5f, -3.5f)
                        reflectiveCurveToRelative(-6.3f, 1.3f, -8.5f, 3.5f)
                        reflectiveCurveToRelative(-3.5f, 5.3f, -3.5f, 8.5f)
                        curveToRelative(0f, 3.1f, 1.3f, 6.3f, 3.5f, 8.5f)
                        curveTo(7.012f, 219.825f, 10.012f, 221.125f, 13.212f, 221.125f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.212f, 171.925f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        reflectiveCurveTo(6.612f, 171.925f, 13.212f, 171.925f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.212f, 122.625f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        curveTo(1.212f, 117.225f, 6.612f, 122.625f, 13.212f, 122.625f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1.0f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1.0f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(13.212f, 73.325f)
                        curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                        reflectiveCurveToRelative(-5.4f, -12f, -12f, -12f)
                        reflectiveCurveToRelative(-12f, 5.4f, -12f, 12f)
                        reflectiveCurveTo(6.612f, 73.325f, 13.212f, 73.325f)
                        close()
                    }
                }
            }
        }.build()
    }
}


@Preview
@Composable
fun SomePreview() {
    AudifyTheme {
        rememberAppIconChange()
    }
}