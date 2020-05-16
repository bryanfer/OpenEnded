package objects

import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class Enemy(val Width: Double,
            val Height: Double,
            val positionX: Double,
            val positionY: Double) extends Rectangle {
  width = Width
  height = Height
  translateX = positionX
  translateY = positionY
  fill = Color.Black
  var health: Int = 100

  def getPosition(): (Double, Double) = {
    (translateX.toDouble, translateY.toDouble)
  }

  def shoot(direction: String): Unit = {

  }
}
