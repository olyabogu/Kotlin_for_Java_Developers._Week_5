package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation: List<Int> by lazy {
        val shuffled = (1..15).shuffled().toMutableList()
        val even = isEven(shuffled)
        if (!even) {
            val n = shuffled.size
            for (i in 0 until n) for (j in i + 1 until n) if (shuffled[i] > shuffled[j]) {
                val a = shuffled[i]
                val b = shuffled[j]
                shuffled[i] = b
                shuffled[j] = a
                break
            }
        }
        shuffled
    }
}

