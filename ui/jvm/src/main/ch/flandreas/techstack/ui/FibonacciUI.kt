package ch.flandreas.techstack.ui;

import ch.flandreas.techstack.domain.Fibonacci
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.*
import kotlin.system.exitProcess

class FibonacciUI : JFrame() {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            // TEST BEGIN
            println(System.getProperty("java.class.path"))
            // TEST END

            SwingUtilities.invokeLater {
                val ui = FibonacciUI()
                ui.addWindowListener(object : WindowAdapter() {
                    override fun windowClosing(e: WindowEvent?) {
                        exitProcess(0)
                    }
                })
                ui.isVisible = true
            }
        }
    }

    private val inputField = JTextField()
    private val resultLabel = JLabel("Enter number")
    private val button = JButton(Calculator())

    init {
        //title = "Fibonacci"
        //title = Fibonacci.javaClass.getResource("/title")
        title = ResourceBundle.getBundle("ui").getString("title")
        setSize(400, 300)
        setLocationRelativeTo(null)

        inputField.columns = 10
        inputField.maximumSize = inputField.preferredSize

        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        addComponent(inputField)
        addComponent(button)
        addComponent(resultLabel)
    }

    private fun addComponent(c: JComponent) {
        c.alignmentX = Component.CENTER_ALIGNMENT
        contentPane.add(c)
    }

    private inner class Calculator : AbstractAction("Calculate") {
        override fun actionPerformed(e: ActionEvent?) {
            try {
                val n = Integer.parseInt(inputField.text)
                val result = Fibonacci.of(n)
                resultLabel.text = result.toString()
                Fibonacci.display(n)

            } catch (e: NumberFormatException) {
                resultLabel.text = "Error"
            }
        }
    }
}
