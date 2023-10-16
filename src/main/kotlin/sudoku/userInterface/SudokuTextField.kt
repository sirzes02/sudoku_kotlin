package sudoku.userInterface

import javafx.scene.control.TextField

class SudokuTextField(val x: Int, val y: Int) : TextField() {
    override fun replaceText(i: Int, i1: Int, s: String) {
        if (!s.matches("[0-9]".toRegex())) {
            super.replaceText(i, i1, s)
        }
    }

    override fun replaceSelection(s: String) {
        if (!s.matches("[0-9]".toRegex())) {
            super.replaceSelection(s)
        }
    }
}
