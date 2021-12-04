package com.chattriggers.ctjs.triggers

import com.chattriggers.ctjs.engine.module.ModuleManager

enum class TriggerType {
    // client
    Chat,
    ActionBar,
    Tick,
    Step,
    GameUnload,
    GameLoad,
    Clicked,
    Scrolled,
    Dragged,
    GuiOpened,
    ScreenshotTaken,
    PickupItem,
    DropItem,
    MessageSent,
    Tooltip,
    PlayerInteract,
    AttackEntity,
    HitBlock,
    GuiRender,
    GuiKey,
    GuiMouseClick,
    GuiMouseRelease,
    GuiMouseDrag,
    ChatComponentClicked,
    ChatComponentHovered,
    PacketSent,
    PacketReceived,

    // rendering
    RenderWorld,
    BlockHighlight,
    RenderOverlay,
    RenderPlayerList,
    RenderBossHealth,
    RenderDebug,
    RenderCrosshair,
    RenderHotbar,
    RenderExperience,
    RenderArmor,
    RenderHealth,
    RenderFood,
    RenderMountHealth,
    RenderAir,
    RenderEntity,
    PostGuiRender,
    PreItemRender,
    RenderSlotHighlight,

    // world
    PlayerJoin,
    PlayerLeave,
    SoundPlay,
    NoteBlockPlay,
    NoteBlockChange,
    WorldLoad,
    WorldUnload,
    BlockBreak,
    SpawnParticle,
    EntityDeath,
    EntityDamage,

    // misc
    Command,
    Other;

    fun triggerAll(vararg args: Any?) {
        ModuleManager.trigger(this, args)
    }

    fun triggerAllWithContextSwitch(vararg args: Any?) {
        ModuleManager.triggerWithContextSwitch(this, args)
    }
}
