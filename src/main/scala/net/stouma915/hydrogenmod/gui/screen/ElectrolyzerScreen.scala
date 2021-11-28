package net.stouma915.hydrogenmod.gui.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu

import scala.collection.immutable.HashMap

object ElectrolyzerScreen {

  final val guiState: HashMap[String, Object] = HashMap()
  private val texture = new ResourceLocation(
    "hydrogenmod:textures/gui/electrolyzer_menu.png"
  )

  private[gui] def newInstance(
      container: ElectrolyzerMenu,
      inventory: Inventory,
      text: Component
  ): ElectrolyzerScreen = new ElectrolyzerScreen(container, inventory, text)

}

sealed class ElectrolyzerScreen private (
    container: ElectrolyzerMenu,
    inventory: Inventory,
    text: Component
) extends AbstractContainerScreen[ElectrolyzerMenu](
      container,
      inventory,
      text
    ) {

  override def render(
      poseStack: PoseStack,
      mouseX: Int,
      mouseY: Int,
      partialTicks: Float
  ): Unit = {
    this.renderBackground(poseStack)
    super.render(poseStack, mouseX, mouseY, partialTicks)
    this.renderTooltip(poseStack, mouseX, mouseY)
  }

  override protected def renderBg(
      poseStack: PoseStack,
      partialTicks: Float,
      gx: Int,
      gy: Int
  ): Unit = {
    RenderSystem.setShaderColor(1, 1, 1, 1)
    RenderSystem.enableBlend()
    RenderSystem.defaultBlendFunc()
    RenderSystem.setShaderTexture(0, ElectrolyzerScreen.texture)
    this.blit(
      poseStack,
      (this.width - this.imageWidth) / 2,
      (this.height - this.imageHeight) / 2,
      0,
      0,
      this.imageWidth,
      this.imageHeight
    )
    RenderSystem.disableBlend()
  }

  override def keyPressed(key: Int, b: Int, c: Int): Boolean = {
    if (key == 256) {
      this.minecraft.player.closeContainer()
      return true
    }
    super.keyPressed(key, b, c)
  }

  override def containerTick(): Unit =
    super.containerTick()

  override def onClose(): Unit = {
    super.onClose()
    Minecraft.getInstance.keyboardHandler.setSendRepeatsToGui(false)
  }

  override def init(): Unit = {
    super.init()
    this.minecraft.keyboardHandler.setSendRepeatsToGui(true)
  }

}
