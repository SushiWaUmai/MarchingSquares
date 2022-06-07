package org.example

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import kotlin.random.Random

class Processing : PApplet() {
    private var gridSize: PVector = PVector(8f, 8f)
    private var cellSize: PVector = PVector()
    private var grid: FloatArray = FloatArray(gridSize.x.toInt() * gridSize.y.toInt()) { Random.nextFloat() }

    override fun settings() {
        size(800, 800, PConstants.JAVA2D)
        cellSize = PVector(width / (gridSize.x - 1), height / (gridSize.y - 1))
    }

    override fun setup() {
    }

    override fun draw() {
        background(0)
        drawGrid()
        drawlines()
    }

    private fun drawGrid() {
        stroke(255f, 0f, 0f)

        for (x in 1 until gridSize.x.toInt() - 1) {
            line(x * cellSize.x, 0f, x * cellSize.x, height.toFloat())
        }
        for (y in 1 until gridSize.y.toInt() - 1) {
            line(0f, y * cellSize.y, width.toFloat(), y * cellSize.y)
        }
    }

    private fun drawlines() {
        stroke(255f, 255f, 0f)
        for (y in 0 until gridSize.y.toInt() - 1) {
            for (x in 0 until gridSize.x.toInt() - 1) {
                line(x * cellSize.x, y * cellSize.y, (x + 1) * cellSize.x, (y + 1) * cellSize.y)

                var p0 = if (grid[gridIndex(x + 0, y + 1)] > 0.5) 1 else 0
                var p1 = if (grid[gridIndex(x + 1, y + 1)] > 0.5) 1 else 0
                var p2 = if (grid[gridIndex(x + 1, y + 0)] > 0.5) 1 else 0
                var p3 = if (grid[gridIndex(x + 0, y + 0)] > 0.5) 1 else 0

                var idx = p0 + (p1 shl 1) + (p2 shl 2) + (p3 shl 3)

                print("${idx} ")
            }
        }
    }

    private fun gridIndex(x: Int, y: Int): Int {
        return y * gridSize.x.toInt() + x
    }
}
