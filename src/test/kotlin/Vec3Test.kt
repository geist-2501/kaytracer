import kotlin.test.Test
import kotlin.test.assertEquals

internal class Vec3Test {

    @Test
    fun shouldAddAssign() {
        // Arrange.
        val v = Vec3(1, 2, 3)

        // Act.
        v += v

        // Assert.
        val expected = Vec3(2, 4, 6)
        assertEquals(expected, v)
    }

    @Test
    fun shouldAddVec() {
        // Arrange.
        val v1 = Vec3(1, 2, 3)
        val v2 = Vec3(1, 2, 3)

        // Act.
        val v3 = v1 + v2

        // Assert.
        val expected = Vec3(2, 4, 6)
        assertEquals(expected, v3)
    }

    @Test
    fun shouldTimesInt() {
        // Arrange.
        val v1 = Vec3(1, 2, 3)

        // Act.
        val v3 = v1 * 3.0

        // Assert.
        val expected = Vec3(3, 6, 9)
        assertEquals(expected, v3)
    }

    @Test
    fun shouldTimesIntPrefix() {
        // Arrange.
        val v1 = Vec3(1, 2, 3)

        // Act.
        val v3 = 3.0 * v1

        // Assert.
        val expected = Vec3(3, 6, 9)
        assertEquals(expected, v3)
    }

}
