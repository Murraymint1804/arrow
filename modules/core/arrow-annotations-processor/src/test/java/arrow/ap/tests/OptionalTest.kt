package arrow.ap.tests

import arrow.optics.OpticsProcessor

class OptionalTest : APTest("arrow.ap.objects.optional") {

  init {

    testProcessor(AnnotationProcessor(
      name = "Optionals cannot be generated for sealed class",
      sourceFiles = listOf("OptionalSealed.java"),
      errorMessage = """
    |Cannot generate arrow.optics.Optional for arrow.ap.objects.optional.OptionalSealed
    |                                             ^
    |  arrow.optics.OpticsTarget.OPTIONAL is an invalid @optics argument for arrow.ap.objects.optional.OptionalSealed.
    |  It is only valid for data classes.
    """.trimMargin(),
      processor = OpticsProcessor()
    ))

    testProcessor(AnnotationProcessor(
      name = "Optional generation requires companion object declaration",
      sourceFiles = listOf("OptionalCompanion.java"),
      errorMessage = "@optics annotated class arrow.ap.objects.optional.OptionalCompanion needs to declare companion object.",
      processor = OpticsProcessor()
    ))

    testProcessor(AnnotationProcessor(
      name = "Optionals will be generated for data class",
      sourceFiles = listOf("Optional.java"),
      destFile = "Optional.kt",
      processor = OpticsProcessor()
    ))

  }

}