package objects

import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class Player(val Width: Double,
             val Height: Double,
             val positionX: Double,
             val positionY: Double) extends Rectangle {
  width = Width
  height = Height
  translateX = positionX
  translateY = positionY
  fill = Color.Red
  var health: Int = 100

  def getPosition(): (Double, Double) = {
    (translateX.toDouble, translateY.toDouble)
  }

  def shoot(direction: String): Unit = {
    var end: Double = 0
    val shot = new Rectangle {
      width = 5.0
      height = 5.0
      translateX = Player.this.translateX.toDouble
      translateY = Player.this.translateY.toDouble
      fill = Color.Blue
    }
    GUI.GUI.sceneGraphics.children.add(shot)
    if (direction == "up") {
      end = 70
    } else if (direction == "down") {
      end = 1010
    } else if (direction == "left") {
      end = 70
    } else if (direction == "right") {
      end = 710
    }
    while (shot.translateX.value != end || shot.translateY.value != end) {
      if (direction == "up") {
        shot.translateY.value -= 5
      } else if (direction == "down") {
        shot.translateY.value += 5
      } else if (direction == "left") {
        shot.translateX.value -= 5
      } else if (direction == "right") {
        shot.translateX.value += 5
      }
    }
  }

  def distance(x1: Double, y1: Double, x2: Double, y2: Double): Double = {
    val ans: Double = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
    ans
  }
}
