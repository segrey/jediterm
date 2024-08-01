package com.jediterm.terminal.model

import junit.framework.TestCase

class HistoryLinesBufferTest : TestCase() {
  private val lines = listOf(
    terminalLine("line1"),
    terminalLine("line2"),
    terminalLine("line3"),
  )

  fun `test adding lines without overflow`() {
    val buffer = LinesBuffer(5, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    assertEquals(3, buffer.lineCount)
    for ((i, line) in lines.withIndex()) {
      assertEquals(line, buffer.getLine(i))
    }
  }

  fun `test adding lines with overflow`() {
    val buffer = LinesBuffer(2, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    assertEquals(2, buffer.lineCount)
    assertEquals(lines[1], buffer.getLine(0))
    assertEquals(lines[2], buffer.getLine(1))
  }

  fun `test remove line`() {
    val buffer = LinesBuffer(5, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    val line = buffer.removeBottomLine()
    assertEquals(lines[2], line)

    assertEquals(2, buffer.lineCount)
    assertEquals(lines[0], buffer.getLine(0))
    assertEquals(lines[1], buffer.getLine(1))
  }

  fun `test remove line when buffer is full`() {
    val buffer = LinesBuffer(3, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    val line = buffer.removeBottomLine()
    assertEquals(lines[2], line)

    assertEquals(2, buffer.lineCount)
    assertEquals(lines[0], buffer.getLine(0))
    assertEquals(lines[1], buffer.getLine(1))
  }

  fun `test remove line after overflow`() {
    val buffer = LinesBuffer(2, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    val line = buffer.removeBottomLine()
    assertEquals(lines[2], line)

    assertEquals(1, buffer.lineCount)
    assertEquals(lines[1], buffer.getLine(0))
  }

  fun `test remove all lines`() {
    val buffer = LinesBuffer(3, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    assertEquals(lines[2], buffer.removeBottomLine())
    assertEquals(lines[1], buffer.removeBottomLine())
    assertEquals(lines[0], buffer.removeBottomLine())

    assertEquals(0, buffer.lineCount)
  }

  fun `test remove line and add new one`() {
    val buffer = LinesBuffer(2, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    buffer.removeBottomLine()
    val newLine = terminalLine("new line")
    buffer.addLine(newLine)

    assertEquals(2, buffer.lineCount)
    assertEquals(lines[1], buffer.getLine(0))
    assertEquals(newLine, buffer.getLine(1))
  }

  fun `test clear lines`() {
    val buffer = LinesBuffer(5, null)
    for (line in lines) {
      buffer.addLine(line)
    }

    buffer.clearAll()
    assertEquals(0, buffer.lineCount)
  }
}