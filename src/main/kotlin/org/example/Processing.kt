package org.example

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector

class Processing : PApplet() {
    private var gridSize: PVector = PVector(32f, 32f)

    override fun settings() {
        size(800, 800, PConstants.JAVA2D)
    }

    override fun setup() {
    }

    override fun draw() {
        background(0)
        stroke(255f, 0f, 0f)

        for (y in 1 until gridSize.y.toInt()) {
            line(0f, y * height / gridSize.y, width.toFloat(), y * height / gridSize.y)
        }
        for (x in 1 until gridSize.x.toInt()) {
            line(x * width / gridSize.x, 0f, x * width / gridSize.x, height.toFloat())
        }
    }
}
