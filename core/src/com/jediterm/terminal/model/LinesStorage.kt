package com.jediterm.terminal.model

/**
 * Base interface for storing terminal lines.
 * Supports adding new lines to the end and removing lines from the end.
 * Also accessing lines by index.
 */
internal interface LinesStorage: Iterable<TerminalLine> {
  /** Count of the available lines in the buffer */
  val count: Int

  /** [y] must be in the range [0, count) */
  fun get(y: Int): TerminalLine

  fun indexOf(line: TerminalLine): Int

  fun addTop(line: TerminalLine)

  fun addAllTop(linesToAdd: List<TerminalLine>)

  /**
   * Adds a new line to the top of the buffer.
   * If the maximum buffer size is limited in the implementation,
   * then the least recently added line will be removed.
   */
  fun addBottom(line: TerminalLine)

  fun addAllBottom(linesToAdd: List<TerminalLine>)

  /**
   * Removes the top lines from the storage.
   */
  fun removeTopLines(count: Int): List<TerminalLine>

  /**
   * Removes the bottom lines from the storage.
   */
  fun removeBottomLines(count: Int): List<TerminalLine>

  fun clearAll()
}
