package gui

import java.net.URL
import java.util.ResourceBundle
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Label, TextArea}

class Controller extends Initializable {
  @FXML
  var resultArea: TextArea = _
  @FXML
  var codeArea: TextArea = _
  @FXML
  var lineNumeration: TextArea = _
  @FXML
  var statusBar: Label = _
  @FXML
  var inputArea: TextArea = _

  override def initialize(url: URL, resourceBundle: ResourceBundle) {
    lineNumeration.setWrapText(true)
    inputArea.textProperty.addListener(new ChangeListener[String]() {
      def changed(observableValue: ObservableValue[_ <: String], s: String, s2: String) {
        codeAreaChanged()
      }
    })
    codeArea.textProperty.addListener(new ChangeListener[String]() {
      def changed(observableValue: ObservableValue[_ <: String], s: String, s2: String) {
        codeAreaChanged()
      }
    })
    codeArea.scrollTopProperty.addListener(new ChangeListener[Number]() {
      def changed(observableValue: ObservableValue[_ <: Number], number: Number, number2: Number) {
        lineNumeration.setScrollTop(codeArea.getScrollTop)
        lineNumeration.setScrollLeft(0.0)
      }
    })
  }

  private def codeAreaChanged() {

    def prepareArea = {
      val numberOfLines = codeArea.getText.count(_ == '\n')
      val lineNumbersString = 1.to(numberOfLines + 1).mkString("\n")

      lineNumeration.clear()
      lineNumeration.textProperty().setValue(lineNumbersString)
      lineNumeration.setScrollTop(codeArea.getScrollTop)
      lineNumeration.setScrollLeft(0.0)

      resultArea.clear()
    }

    def printErrors = {
      def selectLineNumber(n: Int) = {
        val lengthBefore = 1.to(n + 1).mkString(" ").length
        lineNumeration.selectRange(lengthBefore, lengthBefore + 1 + n.toString.length)
      }

      GuiFacade.getErrors match {
        case None =>
          statusBar.setText("Состояние: Без ошибок")
        case Some((error, lineNumber)) =>
          statusBar.setText(s"Состояние: $error в строке $lineNumber")
          selectLineNumber(lineNumber)
      }
    }

    def printResults = {
      for {
        rpn <- GuiFacade.getRPN
        result <- GuiFacade.getInterpResult
      } yield resultArea.setText(s"Преобразованная программа : $rpn\n\nВывод интерпретатора : $result")
    }

    prepareArea
    GuiFacade.parseText(codeArea.getText, inputArea.getText)
    printErrors
    printResults
  }
}