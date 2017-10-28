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
    //lineNumeration.setWrapText(true);
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
    lineNumeration.clear()
    resultArea.clear()
    var i: Int = 0
    while (i < codeArea.getText.split("\n").length) {
      {
        lineNumeration.textProperty.setValue(
          s"""${lineNumeration.getText}${ i + 1 }
             |
           """.stripMargin)
      }
      {
        i += 1; i - 1
      }
    }
    lineNumeration.setScrollTop(codeArea.getScrollTop)
    lineNumeration.setScrollLeft(0.0)
    GuiFacade.parseText(codeArea.getText, inputArea.getText)
    if (GuiFacade.getErrors == null) {
      statusBar.setText("Состояние: Без ошибок")
    }
    else {
      statusBar.setText(s"Состояние: ${GuiFacade.getErrors} в строке ${GuiFacade.getLineWithError}")
      ErrorInLine(GuiFacade.getLineWithError)
    }
    if (GuiFacade.getRPN != null) {
      if (GuiFacade.getInterpResult != null) {
        resultArea.setText(String.format("Преобразованная программа : %s\n\nВывод интерпретатора : %s", GuiFacade.getRPN, GuiFacade.getInterpResult))
      }
      else resultArea.setText(String.format("Преобразованная программа : %s", GuiFacade.getRPN))
    }
  }

  private def ErrorInLine(n: Int) {
    var lengthBefore: Int = 0
    var i: Int = 1
    while (i < n) {
      {
        lengthBefore += i.toString.length + 1
      }
      {
        i += 1; i - 1
      }
    }
    lineNumeration.selectRange(lengthBefore, lengthBefore + 1 + n.toString.length)
  }
}