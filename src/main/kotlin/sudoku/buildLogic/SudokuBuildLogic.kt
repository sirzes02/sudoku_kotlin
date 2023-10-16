package sudoku.buildLogic

import sudoku.computationLogic.GameLogic.newGame
import sudoku.persistence.LocalStorageImpl
import sudoku.problemDomain.IStorage
import sudoku.problemDomain.SudokuGame
import sudoku.userInterface.IUserInterfaceContract
import sudoku.userInterface.logic.ControlLogic
import java.io.IOException

object SudokuBuildLogic {
    @Throws(IOException::class)
    fun build(userInterface: IUserInterfaceContract.View) {
        var initialState: SudokuGame
        val storage: IStorage = LocalStorageImpl()

        try {
            initialState = storage.gameData!!
        } catch (e: IOException) {
            initialState = newGame
            storage.updateGameData(initialState)
            e.printStackTrace()
        }

        val uiLogic: IUserInterfaceContract.EventListener = ControlLogic(storage, userInterface)

        userInterface.setListeners(uiLogic)
        userInterface.updateBoard(initialState)
    }
}
