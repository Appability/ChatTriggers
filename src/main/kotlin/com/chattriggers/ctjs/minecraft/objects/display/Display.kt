package com.chattriggers.ctjs.minecraft.objects.display

import com.chattriggers.ctjs.minecraft.libs.renderer.Renderer
import com.chattriggers.ctjs.utils.kotlin.External
import org.mozilla.javascript.NativeObject
import java.util.concurrent.CopyOnWriteArrayList

@External
class Display {
    private var lines = CopyOnWriteArrayList<DisplayLine>()

    private var renderX = 0f
    private var renderY = 0f
    private var shouldRender = true
    private var order = DisplayHandler.Order.DOWN

    private var backgroundColor: Long = 0x50000000
    private var textColor: Long = 0xffffffff
    private var background = DisplayHandler.Background.NONE
    private var align = DisplayHandler.Align.LEFT

    private var minWidth = 0f
    private var width = 0f
    private var height = 0f

    constructor() {
        @Suppress("LeakingThis")
        DisplayHandler.registerDisplay(this)
    }

    constructor(config: NativeObject?) {
        shouldRender = config.getOption("shouldRender", true).toBoolean()
        renderX = config.getOption("renderX", 0).toFloat()
        renderY = config.getOption("renderY", 0).toFloat()

        setBackgroundColor(config.getOption("backgroundColor", 0x50000000).toLong())
        setBackground(config.getOption("background", DisplayHandler.Background.NONE))
        setTextColor(config.getOption("textColor", 0xffffffff).toLong())
        setAlign(config.getOption("align", DisplayHandler.Align.LEFT))
        setOrder(config.getOption("order", DisplayHandler.Order.DOWN))

        minWidth = config.getOption("minWidth", 0f).toFloat()

        @Suppress("LeakingThis")
        DisplayHandler.registerDisplay(this)
    }

    private fun NativeObject?.getOption(key: String, default: Any): String {
        return (this?.get(key) ?: default).toString()
    }

    fun getBackgroundColor(): Long = backgroundColor

    fun setBackgroundColor(backgroundColor: Long) = apply {
        this.backgroundColor = backgroundColor
    }

    fun getTextColor(): Long = textColor

    fun setTextColor(textColor: Long) = apply {
        this.textColor = textColor
    }

    fun getBackground(): DisplayHandler.Background = background

    fun setBackground(background: Any) = apply {
        this.background = when (background) {
            is String -> DisplayHandler.Background.valueOf(background.uppercase().replace(" ", "_"))
            is DisplayHandler.Background -> background
            else -> DisplayHandler.Background.NONE
        }
    }

    fun getAlign(): DisplayHandler.Align = align

    fun setAlign(align: Any) = apply {
        this.align = when (align) {
            is String -> DisplayHandler.Align.valueOf(align.uppercase())
            is DisplayHandler.Align -> align
            else -> DisplayHandler.Align.LEFT
        }
    }

    fun getOrder(): DisplayHandler.Order = order

    fun setOrder(order: Any) = apply {
        this.order = when (order) {
            is String -> DisplayHandler.Order.valueOf(order.uppercase())
            is DisplayHandler.Order -> order
            else -> DisplayHandler.Order.DOWN
        }
    }

    fun setLine(index: Int, line: Any) = apply {
        while (lines.size - 1 < index)
            lines.add(DisplayLine(""))

        lines[index] = when (line) {
            is String -> DisplayLine(line)
            is DisplayLine -> line
            else -> DisplayLine("")
        }
    }

    fun getLine(index: Int): DisplayLine = lines[index]

    fun getLines(): List<DisplayLine> = lines

    fun setLines(lines: MutableList<DisplayLine>) = apply {
        this.lines = CopyOnWriteArrayList(lines)
    }

    @JvmOverloads
    fun addLine(index: Int = -1, line: Any) {
        val toAdd = when (line) {
            is String -> DisplayLine(line)
            is DisplayLine -> line
            else -> DisplayLine("")
        }

        if (index == -1) {
            lines.add(toAdd)
        } else lines.add(index, toAdd)
    }

    fun addLines(vararg lines: Any) = apply {
        this.lines.addAll(lines.map {
            when (it) {
                is String -> DisplayLine(it)
                is DisplayLine -> it
                else -> DisplayLine("")
            }
        })
    }

    fun clearLines() = apply {
        lines.clear()
    }

    fun getRenderX(): Float = renderX

    fun setRenderX(renderX: Float) = apply {
        this.renderX = renderX
    }

    fun getRenderY(): Float = renderY

    fun setRenderY(renderY: Float) = apply {
        this.renderY = renderY
    }

    fun setRenderLoc(renderX: Float, renderY: Float) = apply {
        this.renderX = renderX
        this.renderY = renderY
    }

    fun getShouldRender(): Boolean = shouldRender

    fun setShouldRender(shouldRender: Boolean) = apply {
        this.shouldRender = shouldRender
    }

    fun getWidth(): Float = width

    fun getHeight(): Float = height

    fun getMinWidth(): Float = minWidth

    fun setMinWidth(minWidth: Float) = apply {
        this.minWidth = minWidth
    }

    fun render() {
        if (!shouldRender)
            return

        width = lines.maxOfOrNull { it.getTextWidth() }?.coerceAtLeast(minWidth) ?: minWidth

        var i = 0f
        lines.forEach {
            it.draw(renderX, renderY + i, width, background, backgroundColor, textColor, align)

            when (order) {
                DisplayHandler.Order.DOWN -> i += it.getText().getHeight()
                DisplayHandler.Order.UP -> i -= it.getText().getHeight()
            }
        }

        if (background == DisplayHandler.Background.FULL)
            Renderer.drawRect(backgroundColor, renderX - 1, renderY + height - 1, width + 1, 1f)
        else if (background == DisplayHandler.Background.PER_LINE)
            Renderer.drawRect(backgroundColor, renderX - 1, renderY + height - 1, lines.last().getTextWidth() + 1, 1f)

        height = i
    }

    override fun toString() =
        "Display{" +
                "shouldRender=$shouldRender, " +
                "renderX=$renderX, renderY=$renderY, " +
                "background=$background, backgroundColor=$backgroundColor, " +
                "textColor=$textColor, align=$align, order=$order, " +
                "minWidth=$minWidth, width=$width, height=$height, " +
                "lines=$lines" +
                "}"

}
