

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by noname on 05.12.2014.
 */
public class LexerJAV {
    ArrayList<Integer> input = new ArrayList<Integer>();
    ArrayList<String> inputValues = new ArrayList<String>();
    Integer count = 0;
    Integer stringNumber = 0;
    LexerJAV(String textForParse) throws Exception {
        ArrayList<String> strInput = new ArrayList<String>(Arrays.asList(textForParse.split(" ")));
        for (String s: strInput)
        {
            if (s.equals("")){

            }
            else if (s.equals("varconst")) {
                input.add(S.VARCONST);
                inputValues.add("??");
            }
            else if(s.equals("program")){
                input.add(S.PROGRAMA);
                inputValues.add("??");
            }
            else if (s.equals("var")){
                input.add(S.VAR);
                inputValues.add("??");
            }
            else if (s.equals("const")){
                input.add(S.CONST);
                inputValues.add("??");
            }
            else if (s.equals("mas")){
                input.add(S.MASSIV);
                inputValues.add("??");
            }
            else if (s.equals("if")){
                input.add(S.ESLI);
                inputValues.add("??");
            }
            else if (s.equals("else")){
                input.add(S.INACHE);
                inputValues.add("??");
            }
            else if (s.equals("while")){
                input.add(S.POKA);
                inputValues.add("??");
            }
            else if (s.equals(";")){
                input.add(S.DOTCOM);
                inputValues.add("??");
            }
            else if (s.equals("out")){
                input.add(S.VIVOD);
                inputValues.add("??");
            }
            else if (s.equals("in")){
                input.add(S.VVOD);
                inputValues.add("??");
            }
            else if (s.equals("=")){
                input.add(S.EQ);
                inputValues.add("??");
            }
            else if (s.equals("<")){
                input.add(S.LESS);
                inputValues.add("??");
            }
            else if (s.equals(">")){
                input.add(S.MORE);
                inputValues.add("??");
            }
            else if (s.equals("+")){
                input.add(S.PLUS);
                inputValues.add("??");
            }
            else if (s.equals("-")){
                input.add(S.MINUS);
                inputValues.add("??");
            }
            else if (s.equals("*")){
                input.add(S.MULT);
                inputValues.add("??");
            }
            else if (s.equals("/")){
                input.add(S.DIV);
                inputValues.add("??");
            }
            else if (s.equals("(")){
                input.add(S.LSKOB);
                inputValues.add("??");
            }
            else if (s.equals(")")){
                input.add(S.RSKOB);
                inputValues.add("??");
            }
            else if (s.equals("{")){
                input.add(S.LFSCOB);
                inputValues.add("??");
            }
            else if (s.equals("}")){
                input.add(S.RFSCOB);
                inputValues.add("??");
            }
            else if (s.equals(".")){
                input.add(S.DOT);
                inputValues.add("??");
            }
            else if(s.equals("\n")){
                stringNumber++;
            }
            else if (Parser.isNumeric(s)){
                input.add(S.NUMBER);
                inputValues.add(s);
            }
            else if (!Character.isDigit(s.charAt(0))){
                input.add(S.ID);
                inputValues.add(s);
            }
            else {
                throw new Exception("на входе беспредел");
            }
        }
//        System.out.println("--------------");
//        System.out.println(input + "////" + inputValues);
//        System.out.println("--------------");
    }
    public int getNext()
    {
        if (input.size() <= count) return S.THEEND;
        return input.get(count++);
    }
    public String getValue()
    {
        if (input.size() <= count) return "??";
        return inputValues.get(count - 1);
    }
    public int getCurrentLine()
    {
        return stringNumber;
    }
}
