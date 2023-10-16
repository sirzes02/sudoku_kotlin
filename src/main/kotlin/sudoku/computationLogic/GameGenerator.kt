package sudoku.computationLogic

import sudoku.computationLogic.GameLogic.sudokuIsInvalid
import sudoku.computationLogic.SudokuSolver.puzzleIsSolvable
import sudoku.computationLogic.SudokuUtilities.copySudokuArrayValue
import sudoku.problemDomain.Coordinates
import sudoku.problemDomain.SudokuGame
import java.util.*
import java.util.function.Consumer

object GameGenerator {
    val newGameGrid: Array<IntArray>
        get() = unsolveGame(solvedGame)

    private fun unsolveGame(solvedGame: Array<IntArray>): Array<IntArray> {
        val random = Random(System.currentTimeMillis())
        var solvable = false
        val solvableArray = Array(SudokuGame.GRID_BOUNDARY) {
            IntArray(
                SudokuGame.GRID_BOUNDARY
            )
        }

        while (!solvable) {
            copySudokuArrayValue(solvedGame, solvableArray)
            var index = 0

            while (index < 40) {
                val xCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY)
                val yCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY)

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0
                    index++
                }
            }

            val toBeSolved = Array(SudokuGame.GRID_BOUNDARY) {
                IntArray(
                    SudokuGame.GRID_BOUNDARY
                )
            }

            copySudokuArrayValue(solvableArray, toBeSolved)
            solvable = puzzleIsSolvable(toBeSolved)
        }

        return solvableArray
    }

    private val solvedGame: Array<IntArray>
        get() {
            val random = Random(System.currentTimeMillis())
            val newGrid = Array(SudokuGame.GRID_BOUNDARY) {
                IntArray(
                    SudokuGame.GRID_BOUNDARY
                )
            }
            var value = 0

            while (value < SudokuGame.GRID_BOUNDARY) {
                var allocations = 0
                var interrupt = 0
                val allocTracker: MutableList<Coordinates> = ArrayList()
                var attempts = 0

                while (allocations < SudokuGame.GRID_BOUNDARY) {
                    if (interrupt > 200) {
                        allocTracker.forEach(Consumer { coordinates: Coordinates ->
                            newGrid[coordinates.x][coordinates.y] = 0
                        })
                    }

                    interrupt = 0
                    allocations = 0
                    allocTracker.clear()
                    attempts++

                    if (attempts > 500) {
                        clearArray(newGrid)
                        attempts = 0
                        value = 1
                    }
                }

                val xCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY)
                val yCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY)

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value

                    if (sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0
                        interrupt++
                    } else {
                        allocTracker.add(Coordinates(xCoordinate, yCoordinate))
                        allocations++
                    }
                }

                value++
            }

            return newGrid
        }

    private fun clearArray(newGrid: Array<IntArray>) {
        for (xIndex in 0..<SudokuGame.GRID_BOUNDARY) {
            for (yIndex in 0..<SudokuGame.GRID_BOUNDARY) {
                newGrid[xIndex][yIndex] = 0
            }
        }
    }
}
