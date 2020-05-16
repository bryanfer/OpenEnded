package Events

import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}
import objects.Player

class KeyEventHandler(player: Player) extends EventHandler[KeyEvent] {

  val playerSpeed: Int = 10

  override def handle(event: KeyEvent): Unit = {
    keyPressed(event.getCode)
  }

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => player.translateY.value -= playerSpeed
      case "A" => player.translateX.value -= playerSpeed
      case "S" => player.translateY.value += playerSpeed
      case "D" => player.translateX.value += playerSpeed
      case "W" | "A" =>
        player.translateY.value -= playerSpeed
        player.translateX.value -= playerSpeed
      case "S" | "D" =>
        player.translateY.value += playerSpeed
        player.translateX.value += playerSpeed

      case "Up" => player.shoot("up")
      case "Down" => player.shoot("down")
      case "Left" => player.shoot("left")
      case "Right" => player.shoot("right")
      case _ =>
    }
  }
}
