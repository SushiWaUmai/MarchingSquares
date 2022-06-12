package org.example

import processing.core.PApplet

class Metaball(p: MarchingSquares) {
    public var x: Float = p.random(p.gridSize.x)
    public var y: Float = p.random(p.gridSize.y)
    public val radius: Float = p.random(5f, 10f)

    public var xVel: Float = p.random(-10f, 10f)
    public var yVel: Float = p.random(-10f, 10f)
}

class MarchingSquaresMetaballs : MarchingSquares() {
    private val numMetaballs: Int = 20
    private var metaballs = Array<Metaball>(numMetaballs, { Metaball(this) })

    override fun updateNoise() {
        // Update Metaballs
        for (i in 0 until numMetaballs) {
            val metaball = metaballs[i]
            metaball.x += metaball.xVel * deltaTime
            metaball.y += metaball.yVel * deltaTime

            if (metaball.x + metaball.radius > gridSize.x) {
                metaball.xVel *= -1f
                metaball.x = gridSize.x - metaball.radius
            } else if (metaball.x - metaball.radius < 0) {
                metaball.xVel *= -1f
                metaball.x = metaball.radius
            }

            if (metaball.y + metaball.radius > gridSize.y) {
                metaball.yVel *= -1f
                metaball.y = gridSize.y - metaball.radius
            } else if (metaball.y - metaball.radius < 0) {
                metaball.yVel *= -1f
                metaball.y = metaball.radius
            }
        }

        // Update Noise
        for (i in 0 until grid.size) {
            var x = i % gridSize.x
            var y = i / gridSize.y
            grid[i] = 0f

            for (metaball in metaballs) {
                val dist = PApplet.dist(x, y, metaball.x, metaball.y)
                grid[i] += PApplet.constrain(1f - (dist / metaball.radius), 0f, 1f)
            }

            grid[i] = PApplet.constrain(grid[i], 0f, 1f)
        }
    }
}
