package fixtures

import fixtures.a.ClassA

class Class(val a: ClassA) {
    val field = 0
    val list = listOf(0, 1)

    fun method() {
        ClassA().method()
        fixtures.b.ClassA().method()
    }
}