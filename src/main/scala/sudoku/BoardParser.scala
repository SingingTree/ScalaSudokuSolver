package sudoku

object BoardParser
{
	def parseBoard(board : Seq[String]) : Board =
		new Board(
			for
			{
				row <- board
				squareRow : Seq[Square] =
				for
				{
					col <- row
					s : Square = col match
					{
						case x if('0' to '9' contains x) => new CompletedSquare(x - '0');
						case _ => EmptySquare
					}
				} yield s
			} yield squareRow
		)
		
		
}
