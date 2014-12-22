import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by noname on 07.12.2014.
 */
public class Interpretator {
    private HashMap<String, Integer> varMap;
    private ArrayList<TokenJAV> tokens;
    private Stack<TokenJAV> mainStack;
    private int caret;
    private ArrayList<String> inputs;

    Interpretator(Parser p) {
        varMap = new HashMap<>();
        tokens = p.getTokenString();
        mainStack = new Stack<TokenJAV>();
        caret = 0;

    }

    public void forInput(ArrayList<String> inputs) {
        this.inputs = inputs;
    }

    public String getResult() throws Exception {
        ArrayList<String> result = new ArrayList<>();
        long maxTimeForProcess = new Date().getTime() + 1000;
        while (caret < tokens.size()) {
            if (new Date().getTime() > maxTimeForProcess)
                throw new Exception("Алгоритм работал дольше секунды");
            TokenJAV currentToken = tokens.get(caret);
            switch (currentToken.type) {
                case num: {
                    mainStack.push(currentToken);
                    caret++;
                    break;
                }
                case id: {
                    mainStack.push(currentToken);
                    caret++;
                    break;
                }
                case plus: {
                    int a = getNumericNext(), b = getNumericNext();
                    int sum = b + a;
                    mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(Integer.toString(sum)).create());
                    caret++;
                    break;
                }
                case minus: {
                    int a = getNumericNext(), b = getNumericNext();
                    int raznost = b - a;
                    mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(Integer.toString(raznost)).create());
                    caret++;
                    break;
                }
                case jump: {
                    caret = Integer.parseInt(mainStack.pop().val);
                    break;
                }
                case jumpFalse: {
                    int newCaret = Integer.parseInt(mainStack.pop().val);
                    int condition = Integer.parseInt(mainStack.pop().val);
                    if (condition == 0) caret = newCaret;
                    else if (condition == 1) caret++;
                    else {
                        throw new Exception("Hе правильное условие");
                    }
                    break;
                }
                case lbl: {
                    mainStack.push(currentToken);
                    caret++;
                    break;
                }
                case in: {
                    String idName = mainStack.pop().val;
                    try {
                        varMap.put(idName, Integer.parseInt(inputs.iterator().next()));
                    } catch (Exception e) {
                        throw new Exception("Ожидается ввод");
                    }
                    caret++;
                    break;
                }
                case out: {
                    result.add(Integer.toString(getNumericNext()));
                    caret++;
                    break;
                }
                case mult: {
                    int a = getNumericNext(), b = getNumericNext();
                    int mult = a * b;
                    mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(Integer.toString(mult)).create());
                    caret++;
                    break;
                }
                case div: {
                    int a = getNumericNext(), b = getNumericNext();
                    if (a == 0) {
                        throw new Exception("Деление на ноль");
                    }
                    int div = b / a;
                    mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(Integer.toString(div)).create());
                    caret++;
                    break;
                }
                case more: {
                    int a = getNumericNext(), b = getNumericNext();
                    if (b > a) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create());
                    else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create());
                    caret++;
                    break;
                }
                case less: {
                    int a = getNumericNext();
                    int b = getNumericNext();
                    if (b < a) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create());
                    else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create());
                    caret++;
                    break;
                }
                case eq: {
                    int a = getNumericNext();
                    int b = getNumericNext();
                    if (a == b) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create());
                    else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create());
                    caret++;
                    break;
                }
                case ravno: {
                    int left = getNumericNext();
                    String idName = mainStack.pop().val;
                    varMap.put(idName, left);
                    caret++;
                    break;
                }
                case arr: {
                    int index = getNumericNext();
                    String idName = mainStack.pop().val;
                    varMap.put(String.format("mID: %s, index: %d", idName, index), varMap.get(String.format("mID: %s, index: %d", idName, index)));
                    mainStack.push(new TKBuild().T(TokenJAV.Op.id).V(String.format("mID: %s, index: %d", idName, index)).create());
                    caret++;
                    break;
                }
            }

        }
        return result.toString();
    }

    private int getNumericNext() throws Exception {
        int result;
        if (mainStack.peek().type == TokenJAV.Op.id) {
            try {
                result = varMap.get(mainStack.pop().val);
            } catch (Exception e) {
                throw new Exception("Использование неинициализированной переменной");
            }
        } else if (mainStack.peek().type == TokenJAV.Op.num) {
            result = Integer.parseInt(mainStack.pop().val);
        } else throw new Exception("Невозможно получить численное значение");
        return result;
    }
}
