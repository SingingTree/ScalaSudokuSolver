package sudoku

object Board
{
	def coordsToSubboard(row: Int, col : Int) : (Int, Int) =
		(row / 3, col / 3)

	def subboardMemberCoords(subboardRow : Int, subboardCol : Int) : Set[(Int, Int)] =
		(for
		{
			row <- (subboardRow * 3 to subboardRow * 3 + 2)
			col <- (subboardCol * 3 to subboardCol * 3 + 2)
		} yield (row, col)
		).toSet
}

class Board(val board : Seq[Seq[Square]])
{
	def subboardMembers(subboardRow : Int, subboardCol : Int) : Set[Square] = 
		(for
		{
			subboardMemCoords <- Board.subboardMemberCoords(subboardRow, subboardCol)
			elem = board(subboardMemCoords._1)(subboardMemCoords._2)
		} yield elem
		).toSet

	def possibleValues(row : Int, col : Int) : Set[Int] = 
	{
		board(row)(col) match
		{
			case CompletedSquare(v) => Set(v)
			case EmptySquare =>
			{
				val allPossibilities : Set[Int] = (1 to 9).toSet
				val eliminatedViaRow =
				board(row).filter(x => x.isInstanceOf[CompletedSquare]).map{case CompletedSquare(v) => v}.toSet

				val eliminatedViaCol =
				(for
				{
					row <- board
					elem = row(col) if(elem.isInstanceOf[CompletedSquare])
				} yield elem.asInstanceOf[CompletedSquare].value
				).toSet

				val eliminatedViaSubboard =
				{
					val subboard = Board.coordsToSubboard(row, col)
					val subboardMem = subboardMembers(subboard._1, subboard._2)
					subboardMem.filter(x => x.isInstanceOf[CompletedSquare]).map{case CompletedSquare(v) => v}
				}

				val eliminatedPossibilities : Set[Int] = eliminatedViaRow ++ eliminatedViaCol ++ eliminatedViaSubboard
				allPossibilities diff eliminatedPossibilities
			}
			case _ => throw new Exception("Unexpected match in possibleValues")
		}
	}

	def updateBoard(b : Seq[Seq[Square]], solvableSquares : Seq[(Int, Int, Int)]) : Seq[Seq[Square]] =
	{
		solvableSquares.foldLeft(b)(
			(board, solvableSquare) =>
			{
				val row = board(solvableSquare._1)
				val updatedRow = row.updated(solvableSquare._2, new CompletedSquare(solvableSquare._3))
				board.updated(solvableSquare._1, updatedRow)
			}
		)
	}
	
	def iterateSolve = 
	{	

		val possibleVals : Seq[Seq[Set[Int]]] =
		(for
		{
			row <- (0 until board.length)
			possValsForRow = 
			(for
			{
				col <- (0 until board(row).length)
			} yield possibleValues(row, col))
		} yield possValsForRow
		)

		// Solves squares if they only have 1 possible number
		val simpleSolvableSquares : Seq[(Int, Int, Int)] =
		(for
		{
			row <- (0 until board.length)
			col <- (0 until board(row).length)
			solvableSquare = board(row)(col) if(solvableSquare == EmptySquare && possibleVals(row)(col).size == 1)
		} yield (row, col, possibleVals(row)(col).head)
		)

		// Solves squares by comparing possibilities to other squares in same row/col/subboard, if no other squares share a possibility this square must be solved with it
		val moreComplexSolvableSquares : Seq[(Int, Int, Int)] =
		(for
		{
			row <- (0 until board.length)
			col <- (0 until board(row).length)
			subboard = Board.coordsToSubboard(row, col)
			posVal <- possibleVals(row)(col)
			solvableSquare = board(row)(col) if(solvableSquare == EmptySquare && 
				(
					// If no squares in the same row have a possibility this square has, then this square can be solved
					(possibleVals(row).take(col) ++ possibleVals(row).drop(col + 1)).forall(x => !x.contains(posVal))
					|| 
					
					// If no squares in the same col have a possibility this square has, then this square can be solved
					(for
					{
						col2 <- (0 until board(row).length) if(col2 != col)
					} yield possibleVals(row)(col2)
					).forall(x => !x.contains(posVal))
					|| 
					
					// If no squares in the same subboard have a possibility this square has, the this square can be solved
					(for
					{
						coords <- Board.subboardMemberCoords(subboard._1, subboard._2) if(coords != (row, col))
					} yield possibleVals(coords._1)(coords._2)
					).forall(x => !x.contains(posVal))
				)	
			)
		} yield (row, col, posVal)
		)

		new Board(updateBoard(board, simpleSolvableSquares union moreComplexSolvableSquares))
	}

	// Attempts to find a solution via 100% deterministic means, will a solved board or an unsolved board in the case deterministic means get stuck
	def deterministicSolve : Board =
	{
		val iteratedBoard = iterateSolve
		if(this == iteratedBoard) this
		else
			if(iteratedBoard.full) iteratedBoard
			else iteratedBoard.deterministicSolve
	}

	// Returns a set of boards based on the current board with n squares now guessed (so the size of this Set is based on ~EmptySquares^n)
	def guessNSquares(n : Int) : Set[Board] =
	{
		val guessedBoards =
		(for
		{
			row <- (0 until board.length)
			col <- (0 until board(row).length)
			posVal <- possibleValues(row, col) if(board(row)(col) == EmptySquare) // Only take possible values for squares that aren't solved
			updatedRow : Seq[Square] = board(row).updated(col, CompletedSquare(posVal))
		} yield new Board(board.updated(row, updatedRow))
		).toSet

		if(n <= 1) guessedBoards
		else guessedBoards.foldLeft(Set[Board]())((x, y) => x ++ y.guessNSquares(n -1))
	}

	def solve(remainingGuesses : Int = 1) : Board =
	{
		if(full) this
		else
		{
			val newBoard = deterministicSolve
			if(newBoard.full || remainingGuesses <= 0) newBoard
			else
			{	
				val guessedBoards = newBoard.guessNSquares(1)
				val solutions =
				for
				{
					guessedBoard <- guessedBoards
					solvedBoard = guessedBoard.deterministicSolve if(solvedBoard.isSolved)
				} yield solvedBoard
				if(!solutions.isEmpty) solutions.head
				else
				{
					val solutions =
					for
					{
						guessedBoard <- guessedBoards
						solvedBoard = guessedBoard.solve(remainingGuesses - 1) if(solvedBoard.isSolved)
					} yield solvedBoard
					if(solutions.isEmpty) newBoard
					else solutions.head
				}
			}
		}
	}

	def numEmptySquares : Int =
		board.flatten.foldLeft(0)((x, y) => if(y == EmptySquare) x + 1 else x)

	def full : Boolean =
	{
		board.forall(x => x.forall(y => y != EmptySquare))
		//numEmptySquares == 0
	}

	def isSolved : Boolean =
	{
		full &&
		(for
		{
			row <- (0 until board.length)
			col <- (0 until board(row).length)
			subboard = Board.coordsToSubboard(row, col)
			value = board(row)(col).asInstanceOf[CompletedSquare].value
			duplicateSquare = (row, col, value) if(
				// Make sure no squares in the same row share a value
				(board(row).take(col) ++ board(row).drop(col + 1)).exists(x => x.asInstanceOf[CompletedSquare].value == value)
				||
				
				// Make sure no squares in the same col share a value
				(for
				{
					col2 <- (0 until board(row).length) if(col2 != col)
				} yield board(row)(col2)
				).exists(x => x.asInstanceOf[CompletedSquare].value == value)
				||

				// make sure no squares in the same subboard share a value
				(for
				{
					coords <- Board.subboardMemberCoords(subboard._1, subboard._2) if(coords != (row, col))
				} yield board(coords._1)(coords._2)
				).exists(x => x.asInstanceOf[CompletedSquare].value == value)
			)
		} yield duplicateSquare
		).isEmpty
	}

	override def equals(that : Any) = that match
	{
		case b : Board => b.board == this.board
		case _ => false
	}

	override def toString() =
	(for
	{
		row <- board
	} yield row mkString ""
	) mkString "\n"

}
