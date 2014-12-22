/**
 * Created by noname on 19.12.2014.
 */
public class Adapter {
    public static int Adapt(int source)
    {
        switch (source)
        {
            case 256: return S.VARCONST;  // ВАРКОНСТ
            case 257: return S.PROGRAMA;  // ПРОГРАММА
            case 258: return S.VAR;  // ПЕРЕМЕННАЯ
            case 259: return S.CONST;  // КОНСТ
            case 260: return S.MASSIV;  // МАССИВ
            case 261: return S.ESLI;  // ЕСЛИ
            case 262: return S.INACHE;  // ИНАЧЕ
            case 263: return S.POKA;  // ПОКА
            case 264: return S.VIVOD;  // ВЫВОД
            case 265: return S.VVOD;  // ВВОД
            case 286: return S.ID ;  // ID
            case 287: return S.NUMBER ;  // ЧИСЛО
            case 289: return S.THEEND ;
            case (int)'(': return S.LSKOB ;
            case (int)')': return S.RSKOB ;
            case (int)'.': return S.DOT ;
            case (int)'<': return S.LESS ;
            case (int)'=': return S.EQ ;
            case (int)'>': return S.MORE ;
            case (int)'{': return S.LFSCOB ;
            case (int)'}': return S.RFSCOB ;
            case (int)'+': return S.PLUS ;
            case (int)'-': return S.MINUS ;
            case (int)'*': return S.MULT ;
            case (int)'/': return S.DIV ;
            case (int)';': return S.DOTCOM;

        }
        return 289;
    }
}
//1. (
//        2. )
//        3. .
//        4. <
//5. =
//        6. >
//        7. ID
//        8. {
//        9. }