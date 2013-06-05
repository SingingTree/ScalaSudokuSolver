package sudoku

import scala.swing._
import scala.swing.event.ButtonClicked


object SudokuGUI extends SimpleSwingApplication 
{
	def top = new MainFrame
	{
		title = "SingingTree's (Scala) Sudoku Solver"

		val textFields : Seq[Seq[TextField]] = Seq.fill(9){Seq.fill(9){new TextField(2)}}

		val labels : Seq[Seq[Label]] = Seq.fill(9){Seq.fill(9){new Label("x")}}

		val inputGridPanel = new GridPanel(9, 9)
		{
			for(row <- textFields)
			{
				for(field <- row)
				{
					contents.append(field)
				}
			}
		}

		val outputGridPanel = new GridPanel(9, 9)
		{
			for(row <- labels)
			{
				for(label <- row)
				{
					contents.append(label)
				}
			}
		}

		val gridsPanel = new BoxPanel(Orientation.Horizontal)
		{
			contents.append(inputGridPanel)
			contents.append(outputGridPanel)
		}

		val solveButton = new Button("Solve")

		contents = new BoxPanel(Orientation.Vertical)
		{
			contents.append(gridsPanel)

			contents.append(solveButton)
			
		}

		listenTo(solveButton)
		reactions +=
		{
			case ButtonClicked(solveButton) => 
			{
				val board = new Board(
					textFields.map(x => x.map(
						y => if(y.text.size == 1 && ('0' to '9' contains y.text(0))) CompletedSquare(y.text(0) - '0') else EmptySquare
					))
				)

				val solvedBoard = board.solve()

				if(solvedBoard.isSolved)
				{
					for(i <- 0 until solvedBoard.board.length)
					{
						for(j <- 0 until solvedBoard.board(i).length)
						{
							labels(i)(j).text = solvedBoard.board(i)(j).asInstanceOf[CompletedSquare].value.toString
						}
					}

					println("solved")
				}
				else println("not solved")
			}
		}
	}
}
