package com.chattriggers.ctjs.launch.plugin

import com.chattriggers.ctjs.minecraft.listeners.CancellableEvent
import com.chattriggers.ctjs.minecraft.wrappers.Player
import com.chattriggers.ctjs.triggers.TriggerType
import dev.falsehonesty.asmhelper.dsl.At
import dev.falsehonesty.asmhelper.dsl.InjectionPoint
import dev.falsehonesty.asmhelper.dsl.code.CodeBlock.Companion.methodReturn
import dev.falsehonesty.asmhelper.dsl.inject
import net.minecraft.client.renderer.GlStateManager

fun injectGuiIngame() {
    injectRenderScoreboard()
    injectRenderHotbarItem()
}

fun injectRenderScoreboard() = inject {
    className = "net/minecraft/client/gui/GuiIngame"
    methodName = "renderScoreboard"
    methodDesc = "(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"
    at = At(InjectionPoint.HEAD)

    methodMaps = mapOf("func_180475_a" to "renderScoreboard")

    codeBlock {
        code {
            val event = CancellableEvent()
            TriggerType.RenderScoreboard.triggerAll(event)

            if (event.isCanceled())
                methodReturn()
        }
    }
}

fun injectRenderHotbarItem() = inject {
    className = "net/minecraft/client/gui/GuiIngame"
    methodName = "renderHotbarItem"
    methodDesc = "(IIIFLnet/minecraft/entity/player/EntityPlayer;)V"
    at = At(InjectionPoint.HEAD)

    methodMaps = mapOf("func_175184_a" to "renderHotbarItem")

    codeBlock {
        val local1 = shadowLocal<Int>()
        val local2 = shadowLocal<Int>()
        val local3 = shadowLocal<Int>()

        code {
            val event = CancellableEvent()
            val item = Player.getInventory()?.getStackInSlot(local1)

            GlStateManager.pushMatrix()
            TriggerType.RenderHotbarItem.triggerAll(item, local2, local3, local1, event)
            GlStateManager.popMatrix()

            if (event.isCanceled())
                methodReturn()
        }
    }
}

