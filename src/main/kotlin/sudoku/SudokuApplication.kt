package sudoku

import javafx.application.Application
import javafx.stage.Stage
import sudoku.buildLogic.SudokuBuildLogic.build
import sudoku.userInterface.IUserInterfaceContract
import sudoku.userInterface.UserInterfaceImpl
import java.io.IOException

class SudokuApplication : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val uiImpl: IUserInterfaceContract.View = UserInterfaceImpl(stage)

        try {
            build(uiImpl)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(*args)
        }
    }
}