package ch.flandreas.techstack.domain

actual object Output {

    actual fun display(text: String) {
        console.info(text)
    }
}