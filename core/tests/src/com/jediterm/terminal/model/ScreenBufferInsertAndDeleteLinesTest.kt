package com.jediterm.terminal.model

import junit.framework.TestCase

class ScreenBufferInsertAndDeleteLinesTest : TestCase() {
  private val lines = listOf(
    terminalLine("line1"),
    terminalLine("line2"),
    terminalLine("line3"),
    terminalLine("line4"),
  )

  fun `test insert lines to start`() {
    val buffer = createScreenBuffer(lines)

    buffer.insertLines(0, 2, 3, createFillerEntry(0))
    val expected = """
      
      
      line1
      line2
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert lines in the middle`() {
    val buffer = createScreenBuffer(lines)

    buffer.insertLines(1, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      
      
      line2
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert lines to the end`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.insertLines(4, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert lines preserving end lines`() {
    val buffer = createScreenBuffer(lines)

    buffer.insertLines(1, 2, 2, createFillerEntry(0))
    val expected = """
      line1
      
      
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert more lines than in the y to lastLine range`() {
    val buffer = createScreenBuffer(lines)

    // Effectively clears all lines between [1, 2] indexes.
    buffer.insertLines(1, 10, 2, createFillerEntry(0))
    val expected = """
      line1
      
      
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert lines with y after lastLine`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.insertLines(4, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert lines with y and lastLine out of lines range`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.insertLines(5, 2, 7, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test insert zero lines`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.insertLines(0, 0, 3, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines from start`() {
    val buffer = createScreenBuffer(lines)

    buffer.deleteLines(0, 2, 3, createFillerEntry(0))
    val expected = """
      line3
      line4
      
      
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines in the middle`() {
    val buffer = createScreenBuffer(lines)

    buffer.deleteLines(1, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      line4
      
      
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines at the end`() {
    val buffer = createScreenBuffer(lines)

    buffer.deleteLines(2, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      line2


    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines preserving end lines`() {
    val buffer = createScreenBuffer(lines)

    buffer.deleteLines(1, 2, 2, createFillerEntry(0))
    val expected = """
      line1


      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete more lines than in the y to lastLine range`() {
    val buffer = createScreenBuffer(lines)

    buffer.deleteLines(1, 4, 2, createFillerEntry(0))
    val expected = """
      line1


      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines with y after lastLine`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.deleteLines(4, 2, 3, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete lines with y and lastLine out of lines range`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.deleteLines(5, 2, 7, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  fun `test delete zero lines`() {
    val buffer = createScreenBuffer(lines)

    // Effectively does nothing
    buffer.deleteLines(0, 0, 3, createFillerEntry(0))
    val expected = """
      line1
      line2
      line3
      line4
    """.trimIndent()
    assertEquals(expected, buffer.lines)
  }

  private fun createScreenBuffer(lines: List<TerminalLine>): LinesBuffer {
    val buffer = LinesBuffer(-1, null)
    buffer.addLines(lines)
    return buffer
  }
}