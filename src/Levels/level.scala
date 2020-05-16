package Levels

import objects.{Door, Enemy, Wall}

class level(var north: level,
            var south: level,
            var east: level,
            var west: level,
            var allWalls: List[Wall],
            var allDoors: List[Door],
            var allEnemies: List[Enemy],
            var traveled: Boolean) {

  var guiChange: Boolean = false
  var end: Boolean = false
  var boss: Boolean = false

  def goNorth(): level = {
    this.north
  }

  def goSouth(): level = {
    this.south
  }

  def goEast(): level = {
    this.east
  }

  def goWest(): level = {
    this.west
  }

}
