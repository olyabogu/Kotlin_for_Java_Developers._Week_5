package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val p = P(permutation.size, permutation.toIntArray())
    return p == 0
}

fun P(n: Int, a: IntArray): Int {
    var cnt = 0
    for (i in 0 until n) for (j in i + 1 until n) if (a[i] > a[j]) cnt++
    return cnt % 2
}