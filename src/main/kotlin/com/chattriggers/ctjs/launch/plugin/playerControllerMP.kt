package com.chattriggers.ctjs.launch.plugin

import com.chattriggers.ctjs.minecraft.listeners.CancellableEvent
import com.chattriggers.ctjs.minecraft.listeners.ClientListener
import com.chattriggers.ctjs.minecraft.wrappers.World
import com.chattriggers.ctjs.minecraft.wrappers.objects.block.BlockFace
import com.chattriggers.ctjs.minecraft.wrappers.objects.entity.Entity
import com.chattriggers.ctjs.triggers.TriggerType
import com.chattriggers.ctjs.utils.kotlin.MCBlockPos
import com.chattriggers.ctjs.utils.kotlin.MCEntity
import dev.falsehonesty.asmhelper.dsl.At
import dev.falsehonesty.asmhelper.dsl.InjectionPoint
import dev.falsehonesty.asmhelper.dsl.code.CodeBlock.Companion.iReturn
import dev.falsehonesty.asmhelper.dsl.code.CodeBlock.Companion.methodReturn
import dev.falsehonesty.asmhelper.dsl.inject
import net.minecraft.util.EnumFacing

fun injectPlayerControllerMP() {
    injectAttackEntity()
    injectHitBlock()
    injectBreakBlock()
}

fun injectAttackEntity() = inject {
    className = "net/minecraft/client/multiplayer/PlayerControllerMP"
    methodName = "attackEntity"
    methodDesc = "(L$ENTITY_PLAYER;L$ENTITY;)V"
    at = At(InjectionPoint.HEAD)

    methodMaps = mapOf("func_78764_a" to "attackEntity")

    codeBlock {
        val local2 = shadowLocal<MCEntity>()

        code {
            val event = CancellableEvent()
            TriggerType.AttackEntity.triggerAll(Entity(local2), event)
            if (event.isCancelled())
                methodReturn()
        }
    }
}

fun injectHitBlock() = inject {
    className = "net/minecraft/client/multiplayer/PlayerControllerMP"
    methodName = "clickBlock"
    methodDesc = "(L$BLOCK_POS;L$ENUM_FACING;)Z"
    at = At(InjectionPoint.HEAD)

    methodMaps = mapOf("func_180511_b" to "clickBlock")

    codeBlock {
        val local1 = shadowLocal<MCBlockPos>()
        val local2 = shadowLocal<EnumFacing>()

        code {
            if (ClientListener.onHitBlock(local1, local2))
                iReturn(0)
        }
    }
}

fun injectBreakBlock() = inject {
    className = "net/minecraft/client/multiplayer/PlayerControllerMP"
    methodName = "onPlayerDestroyBlock"
    methodDesc = "(L$BLOCK_POS;L$ENUM_FACING;)Z"
    at = At(InjectionPoint.HEAD)

    methodMaps = mapOf("func_178888_a" to "onPlayerDestroyBlock")

    codeBlock {
        val local1 = shadowLocal<MCBlockPos>()
        val local2 = shadowLocal<EnumFacing>()

        code {
            TriggerType.BlockBreak.triggerAll(
                World.getBlockAt(local1.x, local1.y, local1.z)
                    .withFace(BlockFace.fromMCEnumFacing(local2)),
            )
        }
    }
}
