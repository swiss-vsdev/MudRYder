import scala.collection.mutable.ArrayBuffer

// Type alias: a free-draw line is a collection of lines segments

package object typesLibrary {
  type Free = ArrayBuffer[Line] // A free draw is a combination of multiple lines
}