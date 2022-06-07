package org.example

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import kotlin.random.Random

class Processing : PApplet() {
    private var gridSize: PVector = PVector(8f, 8f)
    private var grid: FloatArray = FloatArray(gridSize.x.toInt() * gridSize.y.toInt()) { Random.nextFloat() }

    override fun settings() {
        size(800, 800, PConstants.JAVA2D)
    }

    override fun setup() {
        println(grid.contentToString())
    }

    override fun draw() {
        background(0)
        stroke(255f, 0f, 0f)

        for (y in 1 until gridSize.y.toInt() - 1) {
            line(0f, y * height / (gridSize.y - 1), width.toFloat(), y * height / (gridSize.y - 1))
        }
        for (x in 1 until gridSize.x.toInt() - 1) {
            line(x * width / (gridSize.x - 1), 0f, x * width / (gridSize.x - 1), height.toFloat())
        }
    }
}
