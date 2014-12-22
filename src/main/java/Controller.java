

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextArea resultArea;
    public TextArea codeArea;
    public TextArea lineNumeration;
    public Label statusBar;
    public TextArea inputArea;
    private Fasade fasade;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //lineNumeration.setWrapText(true);
        fasade = Fasade.get();
        inputArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                codeAreaChanged();
            }
        });
        codeArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                codeAreaChanged();
            }
        });
        codeArea.scrollTopProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                lineNumeration.setScrollTop(codeArea.getScrollTop());
                lineNumeration.setScrollLeft(0.0);
            }
        });
    }
    private void codeAreaChanged()
    {
        lineNumeration.clear(); resultArea.clear();
        for (int i = 0; i < codeArea.getText().split("\n").length; i++) {
            lineNumeration.textProperty().setValue(String.format("%s%s\n", lineNumeration.getText(), Integer.toString(i + 1)));
        }
        lineNumeration.setScrollTop(codeArea.getScrollTop());
        lineNumeration.setScrollLeft(0.0);
        fasade.parseText(codeArea.getText(), inputArea.getText());
        if (fasade.getErrors() == null) {
            statusBar.setText("Состояние: Без ошибок");
        }
        else {
            statusBar.setText(String.format("Состояние: %s в строке %d", fasade.getErrors(), fasade.getLineWithError()));
            ErrorInLine(fasade.getLineWithError());
        }
        if (fasade.getRPN() != null) {
            if (fasade.getInterpResult() != null)
            {
                resultArea.setText(String.format("Преобразованная программа : %s\n\nВывод интерпретатора : %s", fasade.getRPN(), fasade.getInterpResult()));
            }
            else resultArea.setText(String.format("Преобразованная программа : %s", fasade.getRPN()));
        }

    }
    private void ErrorInLine(Integer n)
    {
        Integer lengthBefore = 0;
        for (int i = 1; i < n; i++) {
            lengthBefore += Integer.toString(i).length() + 1;
        }
        lineNumeration.selectRange(lengthBefore, lengthBefore + 1 + n.toString().length());
    }
}
