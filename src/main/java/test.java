

/**
 * Created by noname on 23.11.2014.
 */
public class test {
    public static void main(String[] args)
    {

        Parser p = new Parser();
        int[] prog =         new int[]{S.VARCONST,  S.PROGRAMA, S.ESLI, S.LSKOB, S.NUMBER, S.EQ, S.NUMBER, S.RSKOB, S.ID, S.EQ, S.NUMBER, S.DOTCOM, S.INACHE, S.ID, S.DOT, S.NUMBER, S.EQ, S.NUMBER, S.MULT, S.NUMBER, S.DIV, S.LSKOB, S.NUMBER, S.PLUS, S.NUMBER, S.RSKOB, S.DOTCOM, S.ID, S.EQ, S.ID, S.DOTCOM, S.VIVOD, S.NUMBER, S.DOTCOM ,S.THEEND};
        String[] values = new String[] {"",         "",         "",     "",     "12",      "",   "13",     "",      "X",  "",   "45",     "",       "",       "Y",    "",  "50",     "",   "666",    "",     "1994",   "",    "",      "30",     "",     "31",     "",      "",       "Z",  "",   "N",  "",       "",      "55",     "" ,      ""};
        System.out.println("варконст массив ID; программа если(12=13){X=45;} иначе Y.50 = 666*1994/(30+31);  Z=N; вывод 55;");
//
        try
        {
            for (int i = 0; i < prog.length; i++) {
                p.parse(prog[i], values[i]);
            }
            System.out.println("RPN for input: " + p.getRPNString());

        }
        catch (Exception e)
        {
            System.out.println(" Неправильный ввод!");
            e.printStackTrace();
        }


    }
}
