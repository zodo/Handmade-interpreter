

/**
 * Created by noname on 30.11.2014.
 */
public class TKBuild {
    TKBuild(){}
    private TokenJAV.Op tok;
    private String val = "";

    public TokenJAV create()
    {
        return new TokenJAV(tok, val);
    }
    public TKBuild T(TokenJAV.Op tok)
    {
        this.tok = tok;
        return this;
    }
    public TKBuild V(String val)
    {
        this.val = val;
        return this;
    }
}
