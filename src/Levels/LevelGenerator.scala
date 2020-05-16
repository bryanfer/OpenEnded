package Levels

class LevelGenerator(val levelNumber: Int) {
  val rand = new scala.util.Random
  var numRooms: Int = 0

  def generateLevel(room: level, numRooms: Int): level = {
    this.numRooms = numRooms
    while (numRooms != 0 && !room.end) {
      val chooseRoom: Int = rand.nextInt(3) + 1
      if (chooseRoom == 1 && room.north == null) {
        room.north = new level(null, room,null, null, null, null, null, false)
        room.end = true
        generateLevel(room.north, numRooms - 1)
      } else if (chooseRoom == 2 && room.south == null) {
        room.south = new level(room, null,null, null, null, null, null,  false)
        room.end = true
        generateLevel(room.south, numRooms - 1)
      } else if (chooseRoom == 3 && room.east == null) {
        room.east = new level(null, null,null, room, null, null, null, false)
        room.end = true
        generateLevel(room.east, numRooms - 1)
      } else if (chooseRoom == 4 && room.west == null) {
        room.west = new level(null, null, room, null, null, null, null, false)
        room.end = true
        generateLevel(room.west, numRooms - 1)
      } else {
        generateLevel(room, numRooms)
      }
    }
    if (numRooms == 0) {
      room.boss = true
    }
    room
  }
}
