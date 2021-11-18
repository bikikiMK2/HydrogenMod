package net.stouma915.hydrogenmod.meta.overlay

import com.mojang.blaze3d.platform.Window
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraftforge.client.event.RenderGameOverlayEvent

case class OverlayGameData(event: RenderGameOverlayEvent.Pre) {
  def getWindow: Window = event.getWindow

  def getType: RenderGameOverlayEvent.ElementType = event.getType

  def getMaxtixStack: PoseStack = event.getMatrixStack
}
