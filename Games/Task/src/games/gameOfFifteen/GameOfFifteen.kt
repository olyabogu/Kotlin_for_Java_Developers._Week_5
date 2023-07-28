package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val gameBoard = createGameBoard<Int?>(4)
    override fun initialize() {
        val allCells = gameBoard.getAllCells()
        val permutation = initializer.initialPermutation
        allCells.forEachIndexed { index, cell -> gameBoard[cell] = permutation.getOrNull(index) }
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        val winValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, null)
        val actualValues = gameBoard.getAllCells().map { gameBoard[it] }
        return winValues == actualValues
    }

    override fun processMove(direction: Direction) {
        val emptyElement = gameBoard.find { it == null }!!

        when (direction) {
            Direction.UP -> {
                with(gameBoard) {
                    emptyElement.getNeighbour(Direction.DOWN)?.let { swap(emptyElement, it) }
                }
            }

            Direction.DOWN -> {
                with(gameBoard) {
                    emptyElement.getNeighbour(Direction.UP)?.let { swap(emptyElement, it) }
                }
            }

            Direction.RIGHT -> {
                with(gameBoard) {
                    emptyElement.getNeighbour(Direction.LEFT)?.let { swap(emptyElement, it) }
                }
            }

            Direction.LEFT -> {
                with(gameBoard) {
                    emptyElement.getNeighbour(Direction.RIGHT)?.let { swap(emptyElement, it) }
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? {
        val cell = gameBoard.getCell(i, j)
        return gameBoard[cell]
    }

    private fun swap(emptyElement: Cell, neighbour: Cell) {
        val a = gameBoard[emptyElement]
        val b = gameBoard[neighbour]
        gameBoard[emptyElement] = b
        gameBoard[neighbour] = a
    }
}