package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    val destination = mutableListOf<T>()

    val iterator = this.filterNotNull().listIterator()

    var first = iterator.nextOrNull()
    var second = iterator.nextOrNull()

    while (first != null) {
        if (first == second) {
            destination.add(merge(first))
            first = iterator.nextOrNull()
            second = iterator.nextOrNull()
        } else {
            destination.add(first)
            first = second
            second = iterator.nextOrNull()
        }
    }

    return destination
}

private fun <T : Any> ListIterator<T>.nextOrNull() = if (hasNext()) next() else null




