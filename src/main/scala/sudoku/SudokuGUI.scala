package sudoku

import scala.swing._


object SudokuGUI extends SimpleSwingApplication 
{
	def top = new MainFrame
	{
		title = "SingingTree's (Scala) Sudoku Solver"

		val solveButton = new Button
		{
			text = "Solve"
		}

		val inputGridPanel = new GridPanel(9, 9)
		{
			//IndexedSeq(
		}

		val gridsPanel = new BoxPanel(Orientation.Horizontal)
		{
			contents.append(new Button{ text = "test" })
		}

		contents = new BoxPanel(Orientation.Vertical)
		{
			contents.append(gridsPanel)

			contents.append(solveButton)
			
		}
	}
}


/*object HelloWorld extends SimpleSwingApplication {
	def top = new MainFrame {
		title = "Hello, World!"
		contents = new Button {
			text = "Click Me!"
		}
	}
}*/
