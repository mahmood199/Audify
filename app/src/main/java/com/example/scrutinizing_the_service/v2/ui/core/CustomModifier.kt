package com.example.scrutinizing_the_service.v2.ui.core

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode

private class CircleNode(var color: Color) : DrawModifierNode, Modifier.Node() {

    override fun ContentDrawScope.draw() {
        drawCircle(color)
    }

    override fun onMeasureResultChanged() {
        super.onMeasureResultChanged()
    }

}

