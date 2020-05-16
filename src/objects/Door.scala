package objects

import Levels.level
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class Door(val transX: Double,
           val transY: Double,
           var side: String,
           var traveled: Boolean) extends Rectangle {
  width = 60.0
  height = 60.0
  translateX = transX
  translateY = transY
  fill = Color.White

  def getPosition(): (Double, Double) = {
    (translateX.toDouble, translateY.toDouble)
  }

  def isCollision(player: Player, room: level): Unit = {
    if (player.distance(player.getPosition()._1 + 30.0, player.getPosition()._2 + 30.0, this.getPosition()._1 + 30.0, this.getPosition()._2 + 30.0) <= 10.0 && !room.guiChange) {
      room.guiChange = true
      GUI.GUI.nextRoom(room)
    }
  }
}
