package sudoku.userInterface.logic

import sudoku.computationLogic.GameLogic.checkForCompletion
import sudoku.computationLogic.GameLogic.newGame
import sudoku.constants.GameState
import sudoku.constants.Messages
import sudoku.problemDomain.IStorage
import sudoku.problemDomain.SudokuGame
import sudoku.userInterface.IUserInterfaceContract
import java.io.IOException

class ControlLogic(private val storage: IStorage, private val view: IUserInterfaceContract.View) :
    IUserInterfaceContract.EventListener {
    override fun onSudokuInput(x: Int, y: Int, input: Int) {
        try {
            var gameData = storage.gameData
            val newGridState = gameData!!.copyOfGridState

            newGridState[x][y] = input
            gameData = SudokuGame(checkForCompletion(newGridState), newGridState)
            storage.updateGameData(gameData)
            view.updateSquare(x, y, input)

            if (gameData.getGameState() === GameState.COMPLETE) {
                view.showDialog(Messages.GAME_COMPLETE)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            view.showError(Messages.ERROR)
        }
    }

    override fun onDialogClick() {
        try {
            storage.updateGameData(newGame)
        } catch (e: IOException) {
            e.printStackTrace()
            view.showError(Messages.ERROR)
        }
    }
}
