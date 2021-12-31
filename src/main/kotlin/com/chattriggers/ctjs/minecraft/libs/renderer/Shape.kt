package com.chattriggers.ctjs.minecraft.libs.renderer

import com.chattriggers.ctjs.minecraft.wrappers.Client
import com.chattriggers.ctjs.utils.kotlin.External
import com.chattriggers.ctjs.utils.kotlin.MCTessellator
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.math.Vec2f
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@External
class Shape(private var color: Int) {
    private var vertexes = mutableListOf<Vec2f>()
    private var drawMode = 9

    fun copy(): Shape = clone()

    fun clone(): Shape {
        val clone = Shape(color)
        clone.vertexes.addAll(vertexes)
        clone.setDrawMode(drawMode)
        return clone
    }

    fun getColor(): Int = color

    fun setColor(color: Int) = apply { this.color = color }

    fun getDrawMode(): Int = drawMode

    /**
     * Sets the GL draw mode of the shape. Possible draw modes are:
     * 0 = points
     * 1 = lines
     * 2 = line loop
     * 3 = line strip
     * 5 = triangles
     * 5 = triangle strip
     * 6 = triangle fan
     * 7 = quads
     * 8 = quad strip
     * 9 = polygon
     */
    fun setDrawMode(drawMode: Int) = apply { this.drawMode = drawMode }

    fun getVertexes(): List<Vec2f> = vertexes

    fun addVertex(x: Float, y: Float) = apply { vertexes.add(Vec2f(x, y)) }

    fun insertVertex(index: Int, x: Float, y: Float) = apply { vertexes.add(index, Vec2f(x, y)) }

    fun removeVertex(index: Int) = apply { vertexes.removeAt(index) }

    /**
     * Sets the shape as a line pointing from [x1, y1] to [x2, y2] with a thickness
     */
    fun setLine(x1: Float, y1: Float, x2: Float, y2: Float, thickness: Float) = apply {
        vertexes.clear()

        val theta = -atan2(y2 - y1, x2 - x1)
        val i = sin(theta) * (thickness / 2)
        val j = cos(theta) * (thickness / 2)

        vertexes.add(Vec2f(x1 + i, y1 + j))
        vertexes.add(Vec2f(x2 + i, y2 + j))
        vertexes.add(Vec2f(x2 - i, y2 - j))
        vertexes.add(Vec2f(x1 - i, y1 - j))

        drawMode = 9
    }

    /**
     * Sets the shape as a circle with a center at [x, y]
     * with radius and number of steps around the circle
     */
    fun setCircle(x: Float, y: Float, radius: Float, steps: Int) = apply {
        vertexes.clear()

        val theta = 2 * PI / steps
        val cos = cos(theta).toFloat()
        val sin = sin(theta).toFloat()

        var xHolder: Float
        var circleX = 1f
        var circleY = 0f

        for (i in 0..steps) {
            vertexes.add(Vec2f(x, y))
            vertexes.add(Vec2f(circleX * radius + x, circleY * radius + y))
            xHolder = circleX
            circleX = cos * circleX - sin * circleY
            circleY = sin * xHolder + cos * circleY
            vertexes.add(Vec2f(circleX * radius + x, circleY * radius + y))
        }

        drawMode = 5
    }

    fun draw() = apply {
        val a = (color shr 24 and 255).toFloat() / 255.0f
        val r = (color shr 16 and 255).toFloat() / 255.0f
        val g = (color shr 8 and 255).toFloat() / 255.0f
        val b = (color and 255).toFloat() / 255.0f

        val tessellator = MCTessellator.getInstance().buffer
        val worldRenderer = Client.getMinecraft().worldRenderer

        RenderSystem.enableBlend()
        RenderSystem.disableTexture()
        RenderSystem.blendFuncSeparate(770, 771, 1, 0)
        if (Renderer.colorized == null)
            RenderSystem.setShaderColor(r, g, b, a)


        tessellator.begin(VertexFormat.DrawMode.values()[drawMode], VertexFormats.POSITION)

        for (vertex in vertexes)
            tessellator.vertex(vertex.x.toDouble(), vertex.y.toDouble(), 0.0).next()

        MCTessellator.getInstance().draw()
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()

        Renderer.finishDraw()
    }
}
