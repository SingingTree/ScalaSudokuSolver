package sudoku

import scala.swing._

/*
object SudokuGUI extends SimpleSwingApplication 
{
	def top = new MainFrame
	{
		title = "SingingTree's (Scala) Sudoku Solver"

		val solveButton = new Button
		{
			text = "Solve"
		}

		contents = new BoxPanel(Orientation.Vertical)
		{
			/*contents.append
			(new BoxPanel(Orientation.Horizontal) =
			{

			}
			)*/

			contents.append(solveButton)
			
		}
	}
}
*/

object HelloWorld extends SimpleSwingApplication {
	def top = new MainFrame {
		title = "Hello, World!"
		contents = new Button {
			text = "Click Me!"
		}
	}
}
