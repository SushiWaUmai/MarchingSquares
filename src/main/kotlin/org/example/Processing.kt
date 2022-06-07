package org.example

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import kotlin.random.Random

class Processing : PApplet() {
    private var noiseScale = 5;
    private var noiseOffset = PVector(0f, 0f);
    private var gridSize = PVector(64f, 64f)
    private var cellSize = PVector()
    private var pointRadius = 3f
    private var grid = FloatArray(gridSize.x.toInt() * gridSize.y.toInt()) { 
        var x = it % gridSize.x
        var y = it / gridSize.y

        noise((x.toFloat() / gridSize.x + noiseOffset.x) * noiseScale, (y.toFloat() / gridSize.y + noiseOffset.y) * noiseScale)
    }

    override fun settings() {
        size(800, 800, PConstants.JAVA2D)
        cellSize = PVector(width / (gridSize.x - 1), height / (gridSize.y - 1))
    }

    override fun setup() {
    }

    override fun draw() {
        background(0)
        drawGrid()
        drawPoints()
        drawLines()
    }

    private fun drawGrid() {
        stroke(32)

        for (x in 1 until gridSize.x.toInt() - 1) {
            line(x * cellSize.x, 0f, x * cellSize.x, height.toFloat())
        }
        for (y in 1 until gridSize.y.toInt() - 1) {
            line(0f, y * cellSize.y, width.toFloat(), y * cellSize.y)
        }
    }

    private fun drawPoints() {
        for (y in 0 until gridSize.y.toInt()) {
            for (x in 0 until gridSize.x.toInt()) {
                val value = grid[gridIndex(x, y)]
                fill(value * 255f)
                noStroke()
                circle(x * cellSize.x, y * cellSize.y, pointRadius)
            }
        }
    }

    private fun drawLines() {
        stroke(255f, 255f, 0f)
        for (y in 0 until gridSize.y.toInt() - 1) {
            for (x in 0 until gridSize.x.toInt() - 1) {
                var p0 = if (grid[gridIndex(x + 0, y + 1)] > 0.5) 1 else 0
                var p1 = if (grid[gridIndex(x + 1, y + 1)] > 0.5) 1 else 0
                var p2 = if (grid[gridIndex(x + 1, y + 0)] > 0.5) 1 else 0
                var p3 = if (grid[gridIndex(x + 0, y + 0)] > 0.5) 1 else 0

                var idx = p0 + (p1 shl 1) + (p2 shl 2) + (p3 shl 3)

                drawConfig(x, y, idx)
            }
        }
    }

    private fun drawConfig(x: Int, y: Int, idx: Int) {
        // all confiugrations for marching squares
        // https://en.wikipedia.org/wiki/Marching_squares#/media/File:Marching_squares_algorithm.svg
        when (idx) {
            0 -> {
                // Nothing
            } 
            1 -> {
                line(x * cellSize.x, (y + 0.5f) * cellSize.y, (x + 0.5f) * cellSize.x, (y + 1) * cellSize.y)
            }
            2 -> {
                line((x + 0.5f) * cellSize.x, (y + 1) * cellSize.y, (x + 1) * cellSize.x, (y + 0.5f) * cellSize.y)
            }
            3 -> {
                line(x * cellSize.x, (y + 0.5f) * cellSize.y, (x + 1) * cellSize.x, (y + 0.5f) * cellSize.y)
            }
            4 -> {
                line((x + 0.5f) * cellSize.x, y * cellSize.y, (x + 1) * cellSize.x, (y + 0.5f) * cellSize.y)
            }
            5 -> {
                drawConfig(x, y, 2);
                drawConfig(x, y, 7);
            }
            6 -> {
                line((x + 0.5f) * cellSize.x, y * cellSize.y, (x + 0.5f) * cellSize.x, (y + 1) * cellSize.y)
            }
            7 -> {
                line(x * cellSize.x, (y + 0.5f) * cellSize.y, (x + 0.5f) * cellSize.x, y * cellSize.y)
            }
            8 -> {
                drawConfig(x, y, 7);
            }
            9 -> {
                drawConfig(x, y, 6);
            }
            10 -> {
                drawConfig(x, y, 1);
                drawConfig(x, y, 4);
            }
            11 -> {
                drawConfig(x, y, 4);
            }
            12 -> {
                drawConfig(x, y, 3);
            }
            13 -> {
                drawConfig(x, y, 2);
            }
            14 -> {
                drawConfig(x, y, 1);
            }
            15 -> {
                // Nothing
            }
        }
    }

    private fun gridIndex(x: Int, y: Int): Int {
        return y * gridSize.x.toInt() + x
    }
}
