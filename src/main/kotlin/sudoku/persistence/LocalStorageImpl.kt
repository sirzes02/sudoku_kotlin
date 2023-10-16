package sudoku.persistence

import sudoku.problemDomain.IStorage
import sudoku.problemDomain.SudokuGame
import java.io.*

class LocalStorageImpl : IStorage {
    @Throws(IOException::class)
    override fun updateGameData(game: SudokuGame?) {
        try {
            val fileOutputStream = FileOutputStream(GAME_DATA)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(game)
            objectOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            throw IOException("Unable to access Game Data")
        }
    }

    @get:Throws(IOException::class)
    override val gameData: SudokuGame
        get() {
            val fileInputStream = FileInputStream(GAME_DATA)
            val objectInputStream = ObjectInputStream(fileInputStream)

            return try {
                val gameState = objectInputStream.readObject() as SudokuGame

                objectInputStream.close()
                gameState
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                throw IOException("File not found")
            }
        }

    companion object {
        private val GAME_DATA = File(System.getProperty("user.home"), "gamedata.txt")
    }
}
