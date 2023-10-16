package sudoku.userInterface

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import sudoku.constants.GameState
import sudoku.problemDomain.Coordinates
import sudoku.problemDomain.SudokuGame
import java.awt.TextField

class UserInterfaceImpl(private val stage: Stage) : IUserInterfaceContract.View,
    EventHandler<KeyEvent> {
    private val root: Group = Group()
    private val textFieldCoordinates: HashMap<Coordinates, SudokuTextField> = HashMap()
    private var listener: IUserInterfaceContract.EventListener? = null

    init {
        initializeUserInterface()
    }

    private fun initializeUserInterface() {
        drawBackground(root)
        drawTitle(root)
        drawSudokuBoard(root)
        drawTextFields(root)
        drawGridLines(root)
        stage.show()
    }

    private fun drawGridLines(root: Group) {
        val X_AND_Y = 114
        var index = 0

        while (index < 8) {
            val thickness: Int = if (index == 2 || index == 5) {
                3
            } else {
                2
            }
            val verticalLine =
                getLine((X_AND_Y + 64 * index).toDouble(), BOARD_PADDING, BOARD_X_AND_Y, thickness.toDouble())
            val horizontalLine =
                getLine(BOARD_PADDING, (X_AND_Y + 64 * index).toDouble(), thickness.toDouble(), BOARD_X_AND_Y)

            root.children.addAll(verticalLine, horizontalLine)
            index++
        }
    }

    private fun getLine(x: Double, y: Double, height: Double, width: Double): Rectangle {
        val line = Rectangle()

        line.x = x
        line.y = y
        line.height = height
        line.width = width
        line.fill = Color.BLACK

        return line
    }

    private fun drawTextFields(root: Group) {
        val xOrigin = 50
        val yOrigin = 50
        val xAndYDelta = 64

        for (xIndex in 0..8) {
            for (yIndex in 0..8) {
                val x = xOrigin + xIndex * xAndYDelta
                val y = yOrigin + yIndex * xAndYDelta
                val tile = SudokuTextField(xIndex, yIndex)

                styleSudokuTile(tile, x.toDouble(), y.toDouble())
                tile.onKeyPressed = this
                textFieldCoordinates[Coordinates(xIndex, yIndex)] = tile
                root.children.add(tile)
            }
        }
    }

    private fun styleSudokuTile(tile: SudokuTextField, x: Double, y: Double) {
        val numberFont = Font(32.0)

        tile.font = numberFont
        tile.alignment = Pos.CENTER
        tile.layoutX = x
        tile.layoutY = y
        tile.prefHeight = 64.0
        tile.prefWidth = 64.0
        tile.background = Background.EMPTY
    }

    private fun drawSudokuBoard(root: Group) {
        val boardBackground = Rectangle()

        boardBackground.x = BOARD_PADDING
        boardBackground.y = BOARD_PADDING
        boardBackground.width = BOARD_X_AND_Y
        boardBackground.height = BOARD_X_AND_Y
        boardBackground.fill = BOARD_BACKGROUND_COLOR

        root.children.add(boardBackground)
    }

    private fun drawTitle(root: Group) {
        val title = Text(235.0, 690.0, SUDOKU)
        title.fill = Color.WHITE
        val titleFont = Font(43.0)

        title.font = titleFont
        root.children.add(title)
    }

    private fun drawBackground(root: Group) {
        val scene = Scene(root, WINDOW_X, WINDOW_Y)

        scene.fill = WINDOW_BACKGROUND_COLOR
        stage.scene = scene
    }

    override fun setListeners(listener: IUserInterfaceContract.EventListener?) {
        this.listener = listener
    }

    override fun updateSquare(x: Int, y: Int, input: Int) {
        val tile = textFieldCoordinates[Coordinates(x, y)]
        var value = input.toString()

        if (value == "0") value = ""

        tile!!.textProperty().value = value
    }

    override fun updateBoard(game: SudokuGame?) {
        for (xIndex in 0..8) {
            for (yIndex in 0..8) {
                val tile = textFieldCoordinates[Coordinates(xIndex, yIndex)]
                var value = game!!.copyOfGridState[xIndex][yIndex].toString()

                if (value == "0") value = ""

                tile!!.text = value

                if (game.getGameState() === GameState.NEW) {
                    if (value.isEmpty()) {
                        tile.style = "-fx-opacity: 1;"
                        tile.isDisable = false
                    } else {
                        tile.style = "-fx-opacity: 0.8;"
                        tile.isDisable = true
                    }
                }
            }
        }
    }

    override fun showDialog(message: String?) {
        val dialog = Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK)

        dialog.showAndWait()
    }

    override fun showError(message: String?) {
        val dialog = Alert(Alert.AlertType.ERROR, message, ButtonType.OK)

        dialog.showAndWait()
    }

    override fun handle(keyEvent: KeyEvent) {
        if (keyEvent.eventType == KeyEvent.KEY_PRESSED) {
            if (keyEvent.text.matches("[0-9]".toRegex())) {
                val value = keyEvent.text.toInt()

                handleInput(value, keyEvent.source)
            } else if (keyEvent.code == KeyCode.BACK_SPACE) {
                handleInput(0, keyEvent.source)
            } else {
                (keyEvent.source as TextField).setText("")
            }
        }
    }

    private fun handleInput(value: Int, source: Any) {
        listener!!.onSudokuInput(
            (source as SudokuTextField).x,
            source.y,
            value
        )
    }

    companion object {
        private const val WINDOW_Y = 732.0
        private const val WINDOW_X = 668.0
        private const val BOARD_PADDING = 50.0
        private const val BOARD_X_AND_Y = 576.0
        private val WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136)
        private val BOARD_BACKGROUND_COLOR = Color.rgb(0, 150, 136)
        private const val SUDOKU = "Sudoku"
    }
}
