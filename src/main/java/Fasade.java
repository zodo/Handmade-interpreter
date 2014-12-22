



import java.util.ArrayList;
import java.util.Arrays;


public class Fasade {
    private static Fasade fasade;
    public static Fasade get()
    {
        if (fasade == null) {
            fasade = new Fasade();
        }
        return fasade;
    }
    private Fasade(){}
    private String RPN;
    private int currentLine;
    private int currentSymbol = 0;
    private String ErrorText;
    private String InterpResult;
    public void parseText(String text, String input)
    {
        RPN = null; ErrorText = null; currentLine = 0;
        ArrayList<String> strInput = new ArrayList<String>(Arrays.asList(input.split(" ")));
        try {
            //LexerJAV lexerJAV = new LexerJAV(text);
            Lexer lexer = new Lexer("ВАРКОНСТ ПРОГРАММА " + text);
            lexer.lexIT();
            Parser parser = new Parser();
            System.out.println("**************");
            while (true)
            {
//                Integer lexema = lexerJAV.getNext();
//                parser.parse(lexema, lexerJAV.getValue());
//                currentLine = lexerJAV.getCurrentLine();

                Word lexema = lexer.getNext();
                parser.parse(Adapter.Adapt(lexema.tag()),lexema.lexeme());
                currentLine = lexer.getLine();
                currentSymbol = lexer.getLine();
                System.out.println("lexema: " + S.getSymbolName(Adapter.Adapt(lexema.tag())) + " val: " + lexema.lexeme());
                if (Adapter.Adapt(lexema.tag()) == S.THEEND) break;
            }
            RPN = parser.getRPNString();
            Interpretator interpretator = new Interpretator(parser);
            interpretator.forInput(strInput);
            InterpResult = interpretator.getResult();
        }
        catch (Exception e){
            System.out.println(e.toString());
//            e.printStackTrace();
            ErrorText = e.getMessage();
        }
    }
    public String getErrors()
    {
        return ErrorText;
    }
    public String getInterpResult()
    {
        return InterpResult;
    }
    public String getRPN()
    {
        return RPN;
    }
    public Integer getLineWithError()
    {
        if (ErrorText == null) {
            return -1;
        }
        return currentLine + 1;
    }
}
