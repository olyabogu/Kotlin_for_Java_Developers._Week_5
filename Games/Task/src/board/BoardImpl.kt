package board

open class SquareBoardImpl(final override val width: Int) : SquareBoard {
    val cells: List<Cell>

    init {
        cells = createBoard(width)
    }

    private fun createBoard(width: Int): List<Cell> {
        val list: MutableList<Cell> = mutableListOf()
        for (i in 1..width) {
            for (j in 1..width) {
                list.add(Cell(i, j))
            }
        }
        return list
    }

    private fun toIndex(i: Int, j: Int): Int {
        if (i < 1 || j < 1) {
            throw IllegalArgumentException()
        }
        return (i - 1) * width + j - 1
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i > width || j > width) {
            return null
        }

        return cells[toIndex(i, j)]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i > width || j > width) {
            throw IllegalArgumentException()
        }
        return cells[toIndex(i, j)]
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list: MutableList<Cell> = mutableListOf()
        jRange.filter { j -> j <= width }.map { j ->
            list.add(cells[toIndex(i, j)])
        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list: MutableList<Cell> = mutableListOf()
        iRange.filter { i -> i <= width }.map { i ->
            list.add(cells[toIndex(i, j)])
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        var i = 0
        var j = 0
        when (direction) {
            Direction.UP -> {
                i = this.i - 1; j = this.j
            }

            Direction.DOWN -> {
                i = this.i + 1; j = this.j
            }

            Direction.RIGHT -> {
                i = this.i; j = this.j + 1
            }

            Direction.LEFT -> {
                i = this.i; j = this.j - 1
            }
        }
        if (i < 1 || j < 1 || i > width || j > width) {
            return null
        }
        return cells[toIndex(i, j)]
    }
}

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    private val cellToValue: MutableMap<Cell, T?> = mutableMapOf()

    init {
        cells.forEach { cell ->
            cellToValue[cell] = null
        }
    }

    override fun get(cell: Cell): T? {
        return cellToValue[cell]
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cells.all { cell -> predicate(get(cell)) }
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cells.any { cell -> predicate(get(cell)) }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellToValue.entries.find { (_, value) -> predicate(value) }?.key
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellToValue.filter { (_, value) -> predicate(value) }.keys
    }

    override fun set(cell: Cell, value: T?) {
        cellToValue[cell] = value
    }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

