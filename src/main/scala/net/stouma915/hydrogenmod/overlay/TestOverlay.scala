package net.stouma915.hydrogenmod.overlay

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.stouma915.hydrogenmod.meta.overlay.{Overlay, OverlayGameData}

object TestOverlay {
  private val instance: Overlay = new TestOverlay

  def apply(): Overlay = instance
}

sealed class TestOverlay private () extends Overlay {
  override def render(overlayGameData: OverlayGameData): Unit =
    if (overlayGameData.getType == RenderGameOverlayEvent.ElementType.ALL) {
      /*
      val width = overlayGameData.getWindow.getGuiScaledWidth
      val height = overlayGameData.getWindow.getGuiScaledHeight
      val posX = width / 2
      val posY = height / 2

      Minecraft
        .getInstance()
        .font
        .draw(
          overlayGameData.getMaxtixStack,
          "OVERLAY TEST",
          posX.toFloat - 26,
          posY.toFloat + 67,
          -12829636
        )
       */
    }
}
