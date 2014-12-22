

/**
 * Created by noname on 29.11.2014.
 */
public class TokenJAV {
    public enum Op{num, id, more, less, eq, ravno, jump, jumpFalse, lbl, in, out, mult, div, plus, minus, arr};
    public Op type;
    public String val;
    TokenJAV(Op type, String val)
    {
        this.type = type;
        this.val = val;
    }
}

