package sudoku.userInterface

import sudoku.problemDomain.SudokuGame

interface IUserInterfaceContract {
    interface EventListener {
        fun onSudokuInput(x: Int, y: Int, input: Int)
        fun onDialogClick()
    }

    interface View {
        fun setListeners(listener: EventListener?)
        fun updateSquare(x: Int, y: Int, input: Int)
        fun updateBoard(game: SudokuGame?)
        fun showDialog(message: String?)
        fun showError(message: String?)
    }
}
