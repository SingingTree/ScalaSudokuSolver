package sudoku

abstract class Square
case object EmptySquare extends Square
{
	override def toString() = "-"
}
case class CompletedSquare(val value : Int) extends Square
{
	override def toString = value.toString
}
