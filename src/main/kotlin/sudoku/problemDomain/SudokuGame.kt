package sudoku.problemDomain

import sudoku.computationLogic.SudokuUtilities
import sudoku.constants.GameState
import java.io.Serializable

class SudokuGame(gameState: GameState, gridState: Array<IntArray>) : Serializable {
    private val gameState: GameState
    private val gridState: Array<IntArray>

    init {
        this.gameState = gameState
        this.gridState = gridState
    }

    fun getGameState(): GameState {
        return gameState
    }

    val copyOfGridState: Array<IntArray>
        get() = SudokuUtilities.copyToNewArray(gridState)

    companion object {
        const val GRID_BOUNDARY = 9
    }
}
