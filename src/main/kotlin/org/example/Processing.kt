package org.example

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import kotlin.random.Random
import kotlin.math.roundToInt

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
        updateNoise()
        drawGrid()
        drawPoints()
        drawLines()
    }

    private fun updateNoise() {
        noiseOffset.x += 0.001f

        for (i in 0 until grid.size) {
            var x = i % gridSize.x
            var y = i / gridSize.y

            grid[i] = noise((x.toFloat() / gridSize.x + noiseOffset.x) * noiseScale, (y.toFloat() / gridSize.y + noiseOffset.y) * noiseScale)
        }
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
                var p0 = grid[gridIndex(x + 0, y + 1)]
                var p1 = grid[gridIndex(x + 1, y + 1)]
                var p2 = grid[gridIndex(x + 1, y + 0)]
                var p3 = grid[gridIndex(x + 0, y + 0)]

                var idx = pointsToConfig(p0, p1, p2, p3)

                // drawConfig(x, y, idx)
                drawConfigSmooth(x, y, idx, p0, p1, p2, p3)
            }
        }
    }

    private fun pointsToConfig(p0: Float, p1: Float, p2: Float, p3: Float): Int {
        var idx = p0.roundToInt() + (p1.roundToInt() shl 1) + (p2.roundToInt() shl 2) + (p3.roundToInt() shl 3)
        return idx
    }

    // p3 -- p2
    // |     |
    // p0 -- p1
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

    // p3 - s2 - p2
    // |         |
    // s3        s1 
    // |         |
    // p0 - s0 - p1
    private fun drawConfigSmooth(x: Int, y: Int, idx: Int, p0: Float, p1: Float, p2: Float, p3: Float) {
        // all confiugrations for marching squares
        // https://en.wikipedia.org/wiki/Marching_squares#/media/File:Marching_squares_algorithm.svg
        var s0 = (0.5f - p0) / (p1 - p0)
        var s1 = (0.5f - p2) / (p1 - p2)
        var s2 = (0.5f - p3) / (p2 - p3)
        var s3 = (0.5f - p3) / (p0 - p3)
        when (idx) {
            0 -> {
                // Nothing
            } 
            1 -> {
                line(x * cellSize.x, (y + s3) * cellSize.y, (x + s0) * cellSize.x, (y + 1) * cellSize.y)
            }
            2 -> {
                line((x + s0) * cellSize.x, (y + 1) * cellSize.y, (x + 1) * cellSize.x, (y + s1) * cellSize.y)
            }
            3 -> {
                line(x * cellSize.x, (y + s3) * cellSize.y, (x + 1) * cellSize.x, (y + s1) * cellSize.y)
            }
            4 -> {
                line((x + s2) * cellSize.x, y * cellSize.y, (x + 1) * cellSize.x, (y + s1) * cellSize.y)
            }
            5 -> {
                drawConfigSmooth(x, y, 2, p0, p1, p2, p3);
                drawConfigSmooth(x, y, 7, p0, p1, p2, p3);
            }
            6 -> {
                line((x + s2) * cellSize.x, y * cellSize.y, (x + s0) * cellSize.x, (y + 1) * cellSize.y)
            }
            7 -> {
                line(x * cellSize.x, (y + s3) * cellSize.y, (x + s2) * cellSize.x, y * cellSize.y)
            }
            8 -> {
                drawConfigSmooth(x, y, 7, p0, p1, p2, p3);
            }
            9 -> {
                drawConfigSmooth(x, y, 6, p0, p1, p2, p3);
            }
            10 -> {
                drawConfigSmooth(x, y, 1, p0, p1, p2, p3);
                drawConfigSmooth(x, y, 4, p0, p1, p2, p3);
            }
            11 -> {
                drawConfigSmooth(x, y, 4, p0, p1, p2, p3);
            }
            12 -> {
                drawConfigSmooth(x, y, 3, p0, p1, p2, p3);
            }
            13 -> {
                drawConfigSmooth(x, y, 2, p0, p1, p2, p3);
            }
            14 -> {
                drawConfigSmooth(x, y, 1, p0, p1, p2, p3);
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
