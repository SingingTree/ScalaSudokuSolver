import sudoku._

class SudokuSolverTester extends org.scalatest.FunSuite
{
	val emptyBoard = new Board(List(
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare)
	))

	val easyBoard = new Board(List(
		List(CompletedSquare(4), CompletedSquare(8), CompletedSquare(7), EmptySquare, CompletedSquare(5), EmptySquare, EmptySquare, CompletedSquare(6), EmptySquare),
		List(CompletedSquare(9), EmptySquare, EmptySquare, CompletedSquare(4), EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(3)),
		List(CompletedSquare(2), EmptySquare, CompletedSquare(6), EmptySquare, CompletedSquare(8), CompletedSquare(9), CompletedSquare(5), EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, CompletedSquare(4), EmptySquare, CompletedSquare(1), CompletedSquare(5), CompletedSquare(6), EmptySquare, EmptySquare),
		List(CompletedSquare(1), EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(4), EmptySquare, CompletedSquare(5), EmptySquare),
		List(EmptySquare, CompletedSquare(7), CompletedSquare(8), CompletedSquare(2), EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(8), EmptySquare, CompletedSquare(7), EmptySquare),
		List(CompletedSquare(7), CompletedSquare(5), EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(3), EmptySquare),
		List(EmptySquare, CompletedSquare(2), EmptySquare, EmptySquare, CompletedSquare(3), CompletedSquare(7), CompletedSquare(4), CompletedSquare(1), EmptySquare)
	))

	val easyBoardSolution = new Board(List(
		List(CompletedSquare(4), CompletedSquare(8), CompletedSquare(7), CompletedSquare(3), CompletedSquare(5), CompletedSquare(1), CompletedSquare(9), CompletedSquare(6), CompletedSquare(2)),
		List(CompletedSquare(9), CompletedSquare(1), CompletedSquare(5), CompletedSquare(4), CompletedSquare(2), CompletedSquare(6), CompletedSquare(7), CompletedSquare(8), CompletedSquare(3)),
		List(CompletedSquare(2), CompletedSquare(3), CompletedSquare(6), CompletedSquare(7), CompletedSquare(8), CompletedSquare(9), CompletedSquare(5), CompletedSquare(4), CompletedSquare(1)),
		List(CompletedSquare(3), CompletedSquare(9), CompletedSquare(4), CompletedSquare(8), CompletedSquare(1), CompletedSquare(5), CompletedSquare(6), CompletedSquare(2), CompletedSquare(7)),
		List(CompletedSquare(1), CompletedSquare(6), CompletedSquare(2), CompletedSquare(9), CompletedSquare(7), CompletedSquare(4), CompletedSquare(3), CompletedSquare(5), CompletedSquare(8)),
		List(CompletedSquare(5), CompletedSquare(7), CompletedSquare(8), CompletedSquare(2), CompletedSquare(6), CompletedSquare(3), CompletedSquare(1), CompletedSquare(9), CompletedSquare(4)),
		List(CompletedSquare(6), CompletedSquare(4), CompletedSquare(3), CompletedSquare(1), CompletedSquare(9), CompletedSquare(8), CompletedSquare(2), CompletedSquare(7), CompletedSquare(5)),
		List(CompletedSquare(7), CompletedSquare(5), CompletedSquare(1), CompletedSquare(6), CompletedSquare(4), CompletedSquare(2), CompletedSquare(8), CompletedSquare(3), CompletedSquare(9)),
		List(CompletedSquare(8), CompletedSquare(2), CompletedSquare(9), CompletedSquare(5), CompletedSquare(3), CompletedSquare(7), CompletedSquare(4), CompletedSquare(1), CompletedSquare(6))
	))

	val easyBoardIncorrectSolution = new Board(List(
		List(CompletedSquare(4), CompletedSquare(8), CompletedSquare(7), CompletedSquare(3), CompletedSquare(5), CompletedSquare(1), CompletedSquare(9), CompletedSquare(6), CompletedSquare(2)),
		List(CompletedSquare(9), CompletedSquare(1), CompletedSquare(5), CompletedSquare(4), CompletedSquare(2), CompletedSquare(6), CompletedSquare(7), CompletedSquare(8), CompletedSquare(3)),
		List(CompletedSquare(2), CompletedSquare(3), CompletedSquare(6), CompletedSquare(7), CompletedSquare(8), CompletedSquare(9), CompletedSquare(5), CompletedSquare(4), CompletedSquare(1)),
		List(CompletedSquare(3), CompletedSquare(9), CompletedSquare(4), CompletedSquare(8), CompletedSquare(1), CompletedSquare(5), CompletedSquare(6), CompletedSquare(2), CompletedSquare(7)),
		List(CompletedSquare(1), CompletedSquare(6), CompletedSquare(2), CompletedSquare(9), CompletedSquare(7), CompletedSquare(4), CompletedSquare(3), CompletedSquare(5), CompletedSquare(8)),
		List(CompletedSquare(5), CompletedSquare(7), CompletedSquare(8), CompletedSquare(2), CompletedSquare(6), CompletedSquare(3), CompletedSquare(1), CompletedSquare(9), CompletedSquare(4)),
		List(CompletedSquare(6), CompletedSquare(4), CompletedSquare(3), CompletedSquare(1), CompletedSquare(9), CompletedSquare(8), CompletedSquare(2), CompletedSquare(7), CompletedSquare(5)),
		List(CompletedSquare(7), CompletedSquare(5), CompletedSquare(1), CompletedSquare(6), CompletedSquare(4), CompletedSquare(2), CompletedSquare(8), CompletedSquare(3), CompletedSquare(9)),
		List(CompletedSquare(2), CompletedSquare(2), CompletedSquare(9), CompletedSquare(5), CompletedSquare(3), CompletedSquare(7), CompletedSquare(4), CompletedSquare(1), CompletedSquare(6))
	))

	val hardBoard = new Board(List(
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(6), CompletedSquare(1), EmptySquare, EmptySquare, CompletedSquare(2)),
		List(CompletedSquare(1), EmptySquare, EmptySquare, CompletedSquare(7), EmptySquare, EmptySquare, EmptySquare, CompletedSquare(4), EmptySquare),
		List(EmptySquare, EmptySquare, EmptySquare, CompletedSquare(8), EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, CompletedSquare(6), CompletedSquare(9), EmptySquare, CompletedSquare(2), EmptySquare, EmptySquare, EmptySquare),
		List(EmptySquare, EmptySquare, CompletedSquare(5), EmptySquare, CompletedSquare(8), EmptySquare, EmptySquare, EmptySquare, CompletedSquare(3)),
		List(EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(5), CompletedSquare(4)),
		List(EmptySquare, EmptySquare, CompletedSquare(7), EmptySquare, EmptySquare, EmptySquare, CompletedSquare(5), EmptySquare, CompletedSquare(6)),
		List(CompletedSquare(4), EmptySquare, EmptySquare, EmptySquare, EmptySquare, CompletedSquare(7), EmptySquare, EmptySquare, CompletedSquare(9)),
		List(CompletedSquare(5), EmptySquare, CompletedSquare(3), EmptySquare, EmptySquare, CompletedSquare(9), EmptySquare, EmptySquare, CompletedSquare(8))
	))

	test("Square comparisons")
	{
		assert(EmptySquare === EmptySquare)
		assert(new CompletedSquare(2) === new CompletedSquare(2))
		assert(new CompletedSquare(2) != new CompletedSquare(5))
	}

	test("List of squares")
	{
		assert(List(new CompletedSquare(3)) === List(new CompletedSquare(3)))
		assert(List(new CompletedSquare(3)) != List(new CompletedSquare(2)))
	}

	test("Parse empty board")
	{
		assert(
			BoardParser.parseBoard(
			  """---------
				|---------
				|---------
				|---------
				|---------
				|---------
				|---------
				|---------
				|---------""".stripMargin.split("\\r?\\n").toList // deal with Windows and Unix lines seperators (Sorry old Mac OS)
			)

			===

			emptyBoard
		)
	}

	test("Parse easy board")
	{
		assert(
			BoardParser.parseBoard(
			  """487-5--6-
				|9--4----3
				|2-6-895--
				|--4-156--
				|1----4-5-
				|-782-----
				|-----8-7-
				|75-----3-
				|-2--3741-""".stripMargin.split("\\r?\\n").toList // deal with Windows and Unix lines seperators (Sorry old Mac OS)
			)

			===

			easyBoard
		)
	}

	test("Parse hard board")
	{
		assert(
			BoardParser.parseBoard(
			  """----61--2
				|1--7---4-
				|---8-----
				|--69-2---
				|--5-8---3
				|-------54
				|--7---5-6
				|4----7--9
				|5-3--9--8""".stripMargin.split("\\r?\\n").toList // deal with Windows and Unix lines seperators (Sorry old Mac OS)
			)

			===

			hardBoard
		)
	}

	test("Coords to subboard")
	{
		assert(Board.coordsToSubboard(4, 3) === (1, 1))
		assert(Board.coordsToSubboard(2, 8) === (0, 2))
	}

	test("Subboard member coords")
	{
		assert(Board.subboardMemberCoords(0, 0) === Set((0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)))
		assert(Board.subboardMemberCoords(1, 0) === Set((3, 0), (3, 1), (3, 2), (4, 0), (4, 1), (4, 2), (5, 0), (5, 1), (5, 2)))
	}

	test("Possible values")
	{
		assert(emptyBoard.possibleValues(0, 0) === (1 to 9).toSet)
		assert(easyBoard.possibleValues(0, 0) === Set(4))
		assert(easyBoard.possibleValues(0, 3) === Set(1, 3))
		assert(easyBoard.possibleValues(8, 0) === Set(6, 8))
		assert(easyBoard.possibleValues(0, 8) === Set(1, 2, 9))

	}

	test("Easy solution") // fix this to make it not hax
	{
		assert(easyBoard.solve()
			===
			easyBoardSolution
		)
	}

	test("Incorrect solution is full but not solved")
	{
		assert(easyBoardIncorrectSolution.full)
		assert(!easyBoardIncorrectSolution.isSolved)
	}

	val harderBoard = BoardParser.parseBoard(
		"""----7-5--
		  |-4------6
		  |5--83-1--
		  |1--4---9-
		  |-7--5--2-
		  |-9---1--8
		  |--4-62--7
		  |9------5-
		  |--3-1----""".stripMargin.split("\\r?\\n").toList)

	val harderBoard2 = BoardParser.parseBoard(
		"""37--5----
		  |---9--41-
		  |-24-8----
		  |-15--7---
		  |--6---9--
		  |---4--12-
		  |----1-86-
		  |-87--9---
		  |----4--95""".stripMargin.split("\\r?\\n").toList)

	test("Hardboard solution appears valid")
	{
		assert(hardBoard.solve().isSolved)
	}

	test("Harderboard solution appears valid")
	{
		assert(harderBoard.solve().isSolved)
	}

	test("Harderboard2 solution appears valid")
	{
		assert(harderBoard2.solve().isSolved)
	}
}
