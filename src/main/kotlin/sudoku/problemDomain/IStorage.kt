package sudoku.problemDomain

import java.io.IOException

interface IStorage {
    @Throws(IOException::class)
    fun updateGameData(game: SudokuGame?)

    @get:Throws(IOException::class)
    val gameData: SudokuGame?
}
