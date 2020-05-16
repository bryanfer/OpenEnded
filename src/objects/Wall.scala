package objects

import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class Wall(val transX: Double,
           val transY: Double) extends Rectangle {

  var side: String = ""
  width = 60.0
  height = 60.0
  translateX = transX
  translateY = transY
  fill = Color.Gray

  def getPosition(): (Double, Double) = {
    (translateX.toDouble, translateY.toDouble)
  }

  def isCollision(player: Player): Unit = {
    if (player.distance(player.getPosition()._1 + 30.0, player.getPosition()._2 + 30.0, this.getPosition()._1 + 30.0, this.getPosition()._2 + 30.0) <= 60.0) {
      if (this.side == "left") {
        player.translateX = this.getPosition()._1 + 60.0
      } else if (this.side == "right") {
        player.translateX = this.getPosition()._1 - 60.0
      } else if (this.side == "top") {
        player.translateY = this.getPosition()._2 + 60.0
      } else if (this.side == "bottom") {
        player.translateY = this.getPosition()._2 - 60.0
      }
    }
  }
}
