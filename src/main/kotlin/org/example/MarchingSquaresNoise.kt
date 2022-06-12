package org.example

import processing.core.PVector

class MarchingSquaresNoise: MarchingSquares() {
    private var noiseScale = 5f;
    private var noiseOffset = PVector(0f, 0f);
    private var noiseSpeed = PVector(0.1f, 0.0f);

    override fun updateNoise() {
        noiseOffset = noiseOffset.add(noiseSpeed.copy().mult(deltaTime))

        for (i in 0 until grid.size) {
            var x = i % gridSize.x
            var y = i / gridSize.y

            grid[i] = noise((x.toFloat() / gridSize.x + noiseOffset.x) * noiseScale, (y.toFloat() / gridSize.y + noiseOffset.y) * noiseScale)
        }
    }
}