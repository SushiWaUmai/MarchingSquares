package org.example

import processing.core.PApplet

open class BetterPApplet : PApplet() {
    private var lastTime: Float = 0.0f
    public var deltaTime: Float = 0.0f
    public var recording: Boolean = false
    public var currentFrame: Int = 0

    override fun draw() {
        deltaTime = millis() / 1000f - lastTime
        lastTime = millis() / 1000f

        if (recording) {
            currentFrame += 1
            saveFrame("img/frame$currentFrame.png")
        }
    }
}
