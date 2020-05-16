package GUI

import Events.KeyEventHandler
import Levels._
import javafx.scene.input.KeyEvent
import objects.{Door, Enemy, Player, Wall}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}

object GUI extends JFXApp {

  val rand = new scala.util.Random
  var sceneGraphics: Group = new Group {}

  val numberOfRooms: Int = rand.nextInt(1) + 5
  val windowWidth: Double = 1160
  val windowHeight: Double = 800

  val startRoom: level = new level(null, null, null, null, null, null, null, false)
  var generator: LevelGenerator = new LevelGenerator(0)
  var firstFloor: level = generator.generateLevel(startRoom, numberOfRooms)
  var currentRoom: level = firstFloor

  var playerWidth: Double = 60.0
  var playerHeight: Double = 60.0
  var playerX: Double = (windowWidth / 2) - 30.0
  var playerY: Double = (windowHeight / 2) - 30.0
  val player = new Player(playerWidth, playerHeight, playerX, playerY)

  var allWalls: List[Wall] = List()
  var allDoors: List[Door] = List()
  var allEnemies: List[Enemy] = List()

  def generateBorderX(trans: Double, room: level): Unit = {
    for (number <- 0 to 18) {
      val wall = new Wall(10.0 + number * 60.0, trans)
      val door = new Door(10.0 + number * 60.0, trans, "", false)
      if (number != 9) {
        if (trans == 10) {
          wall.side = "top"
        } else {
          wall.side = "bottom"
        }
        allWalls ::= wall
      } else {
        if (checkForDoor(room)._1 == 1 && trans == 10) {
          door.side = "north"
          allDoors ::= door
        } else if (checkForDoor(room)._1 == 2 && trans != 10) {
          door.side = "south"
          allDoors ::= door
        } else {
          allWalls ::= wall
        }
      }
    }
  }

  def generateBorderY(trans: Double, room: level): Unit = {
    for (number <- 0 to 10) {
      val wall = new Wall(trans, 70.0 + number * 60.0)
      val door = new Door(trans, 70.0 + number * 60.0, "", false)
      if (number != 5) {
        if (trans == 10) {
          wall.side = "left"
        } else {
          wall.side = "right"
        }
        allWalls ::= wall
      } else {
        if (checkForDoor(room)._1 == 4 && trans == 10) {
          door.side = "west"
          allDoors ::= door
        } else if (checkForDoor(room)._1 == 3 && trans != 10) {
          door.side = "east"
          allDoors ::= door
        } else {
          allWalls ::= wall
        }
      }
    }
  }

  def checkForDoor(room: level): (Int, level) = {
    var door: (Int, level) = null
    if (room.allDoors == null) {
      if (room.north != null && !room.north.traveled) {
        door = (1, room.north)
      } else if (room.south != null && !room.south.traveled) {
        door = (2, room.south)
      } else if (room.east != null && !room.east.traveled) {
        door = (3, room.east)
      } else if (room.west != null && !room.west.traveled) {
        door = (4, room.west)
      } else {
        door = (0, null)
      }
    } else {
      if (room.allDoors.head.side == "north") {
        door = (1, room.north)
      } else if (room.allDoors.head.side == "south") {
        door = (2, room.south)
      } else if (room.allDoors.head.side == "east") {
        door = (3, room.east)
      } else if (room.allDoors.head.side == "west") {
        door = (4, room.west)
      } else {
        door = (0, null)
      }
    }
    room.traveled = true
    door
  }

  def nextRoom(room: level): Unit = {
    val sceneGraphic: Group = new Group {}
    checkForDoor(room)._2.allWalls.foreach(sceneGraphic.children.add(_))
    checkForDoor(room)._2.allDoors.foreach(sceneGraphic.children.add(_))
    checkForDoor(room)._2.allEnemies.foreach(sceneGraphic.children.add(_))
    if (checkForDoor(room)._1 == 1) {
      player.translateY = player.translateY.value + 710.0
    } else if (checkForDoor(room)._1 == 2) {
      player.translateY = player.translateY.value - 710.0
    } else if (checkForDoor(room)._1 == 3) {
      player.translateX = player.translateX.value + 1010.0
    } else if (checkForDoor(room)._1 == 4) {
      player.translateX = player.translateX.value - 1010.0
    }
    sceneGraphics = sceneGraphic
    currentRoom = checkForDoor(room)._2
    sceneGraphics.children.add(player)
    stage.scene.apply.setRoot(sceneGraphics)
  }

  def spawnEnemies(room: level): Unit = {
    if (room != null) {
      val enemies: Int = rand.nextInt(3) + 1
      for (_ <- 1 to enemies) {
        val randX: Int = rand.nextInt(1020)
        val randY: Int = rand.nextInt(660)
        val enemy: Enemy = new Enemy(60.0, 60.0, randX, randY)
        allEnemies ::= enemy
      }
    }
  }

  var room: level = firstFloor
  while (checkForDoor(room)._1 != 0) {
    generateBorderY(10.0, room)
    generateBorderY(windowWidth - 70.0, room)
    generateBorderX(10.0, room)
    generateBorderX(windowHeight - 70.0, room)
    spawnEnemies(room)
    room.allWalls = allWalls
    room.allDoors = allDoors
    room.allEnemies = allEnemies
    allWalls = List()
    allDoors = List()
    allEnemies = List()
    room = checkForDoor(room)._2
  }

  //Boss room
  generateBorderY(10.0, room)
  generateBorderY(windowWidth - 70.0, room)
  generateBorderX(10.0, room)
  generateBorderX(windowHeight - 70.0, room)
  spawnEnemies(room)
  room.allWalls = allWalls
  room.allDoors = allDoors
  room.allEnemies = allEnemies

  currentRoom.allWalls.foreach(sceneGraphics.children.add(_))
  currentRoom.allDoors.foreach(sceneGraphics.children.add(_))
  currentRoom.allEnemies.foreach(sceneGraphics.children.add(_))

  this.stage = new PrimaryStage {
    title = "Game"
    scene = new Scene(windowWidth, windowHeight) {
      content = List(sceneGraphics, player)
      addEventHandler(KeyEvent.KEY_PRESSED, new KeyEventHandler(player))
    }
  }

  val checkWallCollision: Long => Unit = (time: Long) => {
    for (wall <- currentRoom.allWalls) {
      wall.isCollision(player)
    }
  }

  val checkDoorCollision: Long => Unit = (time: Long) => {
    for (door <- currentRoom.allDoors) {
      door.isCollision(player, checkForDoor(currentRoom)._2)
    }
  }

  AnimationTimer(checkWallCollision).start()
  AnimationTimer(checkDoorCollision).start()
}
