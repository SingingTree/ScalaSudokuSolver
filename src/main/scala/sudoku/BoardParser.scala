package sudoku

object BoardParser
{
	def parseBoard(board : List[String]) : Board =
		new Board(
			for
			{
				row <- board
				squareRow : List[Square] =
				(for
				{
					col <- row
					s : Square = col match
					{
						case x if('0' to '9' contains x) => new CompletedSquare(x.asInstanceOf[Int] - '0');
						case _ => EmptySquare
					}
				} yield s).toList
			} yield squareRow
		)
		
		
}
