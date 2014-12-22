import java.util.*;

/**
 * Created by noname on 23.11.2014.
 */
public class Parser {
    private Stack<Integer> shop;
    private boolean workIsDone;
    private RPNhandler RPN;

    public void reset()
    {
        RPN = new RPNhandler();
        shop = new Stack<Integer>();
        shop.push(S.THEEND);
        shop.push(S.PROGRAM);
        workIsDone = false;
    }
    Parser()
    {
        reset();
    }

    public String getRPNString()
    {
        return RPN.getStr();
    }
    public ArrayList<TokenJAV> getTokenString() {
        return RPN.tokenString;
    }
    public HashMap<String, Integer> getVARMap() {
        return RPN.varMap;
    }
    public HashMap<String, Integer> getCONSTMap() {
        return RPN.constMap;
    }
    public HashMap<String, ArrayList<Integer>> getARRAYMap(){
        return RPN.masMap;
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public boolean parse(Integer term, String value) throws Exception
    {
//        if (workIsDone) return true;
        if (term.equals(S.NUMBER) && !isNumeric(value))
            throw new Exception("Не правильное значение числа");
        if (term.equals(S.ID) && (value.equals("") || Character.isDigit(value.charAt(0))))
                throw new Exception("Не правильное значение ID");

            RPN.pushValue(value);
        while (true)
        {
            if (shop.peek() == S.THEEND && term == S.THEEND)
            {
                shop.pop(); RPN.goOut();
                //System.out.println("finished shop size = " + shop.size() + ", RPN.shop = " + RPN.shop.size());
                workIsDone = true;
                break;
            }
            if (shop.peek() < 100)  //Нетерминал
            {
                List<Integer> zamena = table.getMove(shop.peek(), term );

                if (zamena == null)
                    throw new Exception("Hет перехода в \"" + S.getSymbolName(term) +
                                                "\" из \"" + S.getSymbolName(shop.peek()) + "\"");
                List<Integer> RPNzamena = table.getRPNmove(shop.peek(), term);
                Collections.reverse(zamena);
                shop.pop();
                boolean lambda = false;
                for (int i : zamena)
                    if (i != S.LAM) {
                        shop.push(i);
                    }
                    else{
                        lambda = true;
                    }
                Collections.reverse(RPNzamena);
                RPN.goOut();
                for (Integer i: RPNzamena) if (!lambda) RPN.push(i);

            }
            else //Терминал
            {
                if (term.equals(shop.peek()))
                {
                    shop.pop();
                    RPN.goOut();
                    break;
                }
                else
                {
                    throw new Exception("Hа входе \"" + S.getSymbolName(term) + "\",ожидался \"" + S.getSymbolName(shop.peek()) + "\". ");
                }
            }
        }
        return workIsDone;
    }
    private class RPNhandler
    {
        private int count;
        private String compOperator;
        private Stack<Integer> shop;
        private Stack<Integer> LBLshop;
        private Stack<String> TokenValues;
        private ArrayList<String> string;
        private ArrayList<TokenJAV> tokenString;
        private HashMap<String, Integer> varMap;
        private HashMap<String, Integer> constMap;
        private HashMap<String, ArrayList<Integer>> masMap;
        RPNhandler()
        {
            shop = new Stack<Integer>();
            LBLshop = new Stack<Integer>();
            string = new ArrayList<String>();
            TokenValues = new Stack<String>();
            varMap = new HashMap<String, Integer>();
            constMap = new HashMap<String, Integer>();
            masMap = new HashMap<String, ArrayList<Integer>>();
            tokenString = new ArrayList<TokenJAV>();
            shop.push(S.N);
            shop.push(S.N);
            count = 0;
            compOperator = "";
        }
        private String getStrFromInt(int symbol)
        {
            if (symbol>= 200) return "";
            switch(symbol) {
                case S.NUMBER:  TKSadd(new TKBuild().T(TokenJAV.Op.num).V(TokenValues.peek()).create());
                    return TokenValues.pop();
                case S.PLUS: TKSadd(new TKBuild().T(TokenJAV.Op.plus).create());
                    return "+";
                case S.MINUS: TKSadd(new TKBuild().T(TokenJAV.Op.minus).create());
                    return "-";
                case S.MULT: TKSadd(new TKBuild().T(TokenJAV.Op.mult).create());
                    return "*";
                case S.DIV: TKSadd(new TKBuild().T(TokenJAV.Op.div).create());
                    return "/";
                case S.ID: TKSadd(new TKBuild().T(TokenJAV.Op.id).V(TokenValues.peek()).create());
                    return TokenValues.pop();
                case S.EQ: TKSadd(new TKBuild().T(TokenJAV.Op.ravno).create());
                    return ":=";
                case S.DOT: TKSadd(new TKBuild().T(TokenJAV.Op.arr).create());
                    return "<i>";
                case S.MLE:
                    if (compOperator.equals(">"))
                    {
                        TKSadd(new TKBuild().T(TokenJAV.Op.more).create());
                        return ">";
                    }
                    else if (compOperator.equals("<"))
                    {
                        TKSadd(new TKBuild().T(TokenJAV.Op.less).create());
                        return "<";
                    }
                    else if (compOperator.equals("=="))
                    {
                        TKSadd(new TKBuild().T(TokenJAV.Op.eq).create());
                        return "==";
                    }
                case S.P:  return "";
                case S.VIVOD: TKSadd(new TKBuild().T(TokenJAV.Op.out).create());
                    return "<OUT>";
                case S.VVOD: TKSadd(new TKBuild().T(TokenJAV.Op.in).create());
                    return "<IN>";
                default: return Integer.toString(symbol);
            }
        }
        private void P1()
        {
            LBLshop.push(count);
            addToRPN("pystP1");
            addToRPN("<jf>");
            TKSadd(new TKBuild().T(TokenJAV.Op.lbl).create());
            TKSadd(new TKBuild().T(TokenJAV.Op.jumpFalse).create());
        }
        private void P2()
        {
            String m = "<m" + (count + 2 ) + ">";
            tokenString.get(LBLshop.peek()).val = String.valueOf(count + 2);
            string.set(LBLshop.pop(), m);
            LBLshop.push(count);
            addToRPN("pystP2");
            addToRPN("<j>");
            TKSadd(new TKBuild().T(TokenJAV.Op.lbl).create());
            TKSadd(new TKBuild().T(TokenJAV.Op.jump).create());
        }
        private void P3()
        {
            String m = "<m" + (count) + ">";
            string.set(LBLshop.peek(), m);
            tokenString.get(LBLshop.pop()).val = String.valueOf(count);
        }
        private void P4()
        {
            LBLshop.push(count);
        }
        private void P5()
        {
            String m = "<m" + (count + 2 ) + ">";
            string.set(LBLshop.peek(), m);
            tokenString.get(LBLshop.pop()).val = String.valueOf(count + 2);

            addToRPN("<m" + LBLshop.peek() + ">");
            addToRPN("<j>");
            TKSadd(new TKBuild().T(TokenJAV.Op.lbl).V(String.valueOf(LBLshop.pop())).create());
            TKSadd(new TKBuild().T(TokenJAV.Op.jump).create());
        }
        private void addToRPN(String s)
        {
            if (!s.equals("")) {
                string.add(s);
                count++;
            }

        }
        private void TKSadd(TokenJAV token)
        {
            tokenString.add(token);
        }
        public void goOut()
        {
            int symbol = shop.pop();
            if (symbol != S.N) {
            switch (symbol)
            {
                case S.P1: P1();  break;
                case S.P2: P2();  break;
                case S.P3: P3();   break;
                case S.P4: P4();  break;
                case S.P5: P5();  break;
                case S.Pvar: varMap.put(TokenValues.empty()? "MissedID&N":TokenValues.pop(), 0); break;
                case S.Pconst: constMap.put(TokenValues.empty()? "MissedID&N":TokenValues.pop(), 0); break;
                case S.Pmas: masMap.put(TokenValues.empty()? "MissedID&N":TokenValues.pop(), new ArrayList<Integer>());
                case S.MORE: compOperator = ">";  break;
                case S.LESS: compOperator = "<";  break;
                case S.COMP: compOperator = "=="; break;
                default: addToRPN(getStrFromInt(symbol));
            }

        }
        }

        public void push(int symbol)
        {
            shop.push(symbol);
        }
        public void pushValue(String s)
        {
            TokenValues.push(s);
        }
        public String getStr()
        {
            if (workIsDone)
                while (!shop.empty())
                    addToRPN(getStrFromInt(shop.pop()));
            string.removeAll(Collections.singleton(""));
            return string.toString();
        }
    }
}
