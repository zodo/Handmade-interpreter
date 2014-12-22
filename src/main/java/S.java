import java.util.HashMap;

/**
 * Created by noname on 23.11.2014.
 */
public abstract class S {
    public static final int PROGRAM = 0;
    public static final int BL_PEREM = 1;
    public static final int CODE = 2;
    public static final int BLOK = 3;
    public static final int ELSE = 4;
    public static final int IF = 5;
    public static final int OPERATOR = 6;
    public static final int VYRAZ = 7;
    public static final int VYRAZ_2 = 8;
    public static final int TERM = 9;
    public static final int TERM_2 = 10;
    public static final int FACTOR = 11;
    public static final int OPERAND = 12;
    public static final int VARIABLE = 13;
    public static final int ARRAY = 14;
    public static final int LAM = 15;
    public static final int P = 16;

    public static final int VARCONST = 100;
    public static final int PROGRAMA = 101;
    public static final int VAR = 102;
    public static final int CONST = 103;
    public static final int MASSIV = 104;
    public static final int ESLI = 105;
    public static final int INACHE = 106;
    public static final int POKA = 107;
    public static final int DOTCOM = 108;
    public static final int VIVOD = 109;
    public static final int VVOD = 110;
    public static final int EQ = 111;
    public static final int LESS = 112;
    public static final int MORE = 113;
    public static final int PLUS = 114;
    public static final int MINUS = 115;
    public static final int MULT = 116;
    public static final int DIV = 117;
    public static final int LSKOB = 118;
    public static final int RSKOB = 119;
    public static final int LFSCOB = 120;
    public static final int RFSCOB = 121;
    public static final int DOT = 122;
    public static final int ID = 123;
    public static final int NUMBER = 124;
    public static final int THEEND = 125;
    public static final int COMP = 126;
    public static final int MLE = 127;

    public static final int N = 200;
    public static final int P1 = 201;
    public static final int P2 = 202;
    public static final int P3 = 203;
    public static final int P4 = 204;
    public static final int P5 = 205;
    public static final int Pvar = 206;
    public static final int Pconst = 207;
    public static final int Pmas = 208;


    static HashMap<Integer, String> map;
    private static void fillMap()
    {
        map = new HashMap<Integer, String>();
        map.put(0, "PROGRAM");
        map.put(1, "BL_PEREM");
        map.put(2, "CODE");
        map.put(3, "BLOK");
        map.put(4, "ELSE");
        map.put(5, "IF");
        map.put(6, "OPERATOR");
        map.put(7, "VYRAZ");
        map.put(8, "VYRAZ_2");
        map.put(9, "TERM");
        map.put(10, "TERM_2");
        map.put(11, "FACTOR");
        map.put(12, "OPERAND");
        map.put(13, "VARIABLE");
        map.put(14, "ARRAY");
        map.put(15, "LAM");
        map.put(16, "P");
        map.put(100, "варконст");
        map.put(101, "програма");
        map.put(102, "вар");
        map.put(103, "конст");
        map.put(104, "массив");
        map.put(105, "если");
        map.put(106, "иначе");
        map.put(107, "пока");
        map.put(108, "\" ; \"");
        map.put(109, "вывод");
        map.put(110, "ввод");
        map.put(111, "=");
        map.put(112, "<");
        map.put(113, ">");
        map.put(114, "+");
        map.put(115, "-");
        map.put(116, "*");
        map.put(117, "/");
        map.put(118, "(");
        map.put(119, ")");
        map.put(120, "{");
        map.put(121, "}");
        map.put(122, "\" . \"");
        map.put(123, "ID");
        map.put(124, "NUMBER");
        map.put(125, "конец программы");
        map.put(126, "COMP");
        map.put(127, "MLE");
        map.put(200, "N");
        map.put(201, "P1");
        map.put(202, "P2");
        map.put(203, "P3");
        map.put(204, "P4");
        map.put(205, "P5");
        map.put(206, "Pvar");
        map.put(207, "Pconst");
        map.put(208, "Pmas");
    }
    public static String getSymbolName(Integer symbol)
    {
        if (map == null) {
            fillMap();
        }
        return map.get(symbol);
    }
}
