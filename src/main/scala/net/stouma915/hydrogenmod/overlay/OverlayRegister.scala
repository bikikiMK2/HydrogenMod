package net.stouma915.hydrogenmod.overlay

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.{EventPriority, SubscribeEvent}
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.meta.overlay.OverlayGameData

@Mod.EventBusSubscriber(
  Array(Dist.CLIENT),
  modid = HydrogenMod.ModId
)
object OverlayRegister {
  private val overlaysToRegister = List(
    TestOverlay()
  )

  @SubscribeEvent(priority = EventPriority.NORMAL)
  def onRenderGameOverlay(event: RenderGameOverlayEvent.Pre): Unit =
    overlaysToRegister.foreach { overlay =>
      val overlayGameData = OverlayGameData(event)

      overlay.render(overlayGameData)
    }
}
