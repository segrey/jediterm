package com.jediterm.terminal.model

import kotlin.math.min

/**
 * @param maxCapacity maximum number of stored lines; -1 means no restriction
 */
internal class CyclicBufferLinesStorage(private val maxCapacity: Int) : LinesStorage {

  private val lines: ArrayDeque<TerminalLine> = ArrayDeque()

  override val count: Int
    get() = lines.size

  override fun get(y: Int): TerminalLine = lines[y]

  override fun indexOf(line: TerminalLine): Int = lines.indexOf(line)

  override fun addTop(line: TerminalLine) {
    lines.addFirst(line)
    trimIfNeeded()
  }

  override fun addAllTop(linesToAdd: List<TerminalLine>) {
    for (i in linesToAdd.size - 1 downTo 0) {
      addTop(linesToAdd[i])
    }
  }

  override fun addBottom(line: TerminalLine) {
    lines.addLast(line)
    trimIfNeeded()
  }

  private fun trimIfNeeded() {
    if (maxCapacity >= 0 && lines.size > maxCapacity) {
      lines.removeFirst()
    }
  }

  override fun addAllBottom(linesToAdd: List<TerminalLine>) {
    lines.addAll(linesToAdd)
    if (maxCapacity >= 0) {
      while (lines.size > maxCapacity) {
        lines.removeFirst()
      }
    }
  }

  override fun removeTopLines(count: Int): List<TerminalLine> {
    val resultCount = min(count, lines.size)
    if (resultCount == 1) {
      return listOf(lines.removeFirst())
    }
    return (1..resultCount).map { lines.removeFirst() }
  }

  override fun removeBottomLines(count: Int): List<TerminalLine> {
    val size = min(count, lines.size)
    if (size == 0) return emptyList()
    val result = ArrayList<TerminalLine>(size)
    for (i in 1..size) {
      result.add(lines.removeLast())
    }
    result.reverse()
    return result
  }

  override fun clearAll() = lines.clear()

  override fun iterator(): Iterator<TerminalLine> = lines.iterator()
}
