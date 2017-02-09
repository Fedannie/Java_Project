package project.fedorova.polyglotte.data.language;

import java.util.Arrays;
import java.util.List;

public class Alphabet {
    private static volatile Alphabet instance;

    private Alphabet() {}

    public static Alphabet getInstance() {
        Alphabet localInstance = instance;
        if (localInstance == null) {
            synchronized (Alphabet.class){
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Alphabet();
                }
            }
        }
        return localInstance;
    }

    public List<String> getAlphabet(String language) {
        String str = "";
        switch (language){
            case "sq":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064" +
                        "\u002C\u0020\u0064\u0068\u002C\u0020\u0065\u002C\u0020\u00EB\u002C\u0020\u0066" +
                        "\u002C\u0020\u0067\u002C\u0020\u0067\u006A\u002C\u0020\u0068\u002C\u0020\u0069" +
                        "\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006C\u006C" +
                        "\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006E\u006A\u002C\u0020\u006F" +
                        "\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0072\u0072" +
                        "\u002C\u0020\u0073\u002C\u0020\u0073\u0068\u002C\u0020\u0074\u002C\u0020\u0074" +
                        "\u0068\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078" +
                        "\u002C\u0020\u0078\u0068\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u007A\u0068";
                break;
            case "hy":
                str = "\u0561\u002C\u0020\u0562\u002C\u0020\u0563\u002C\u0020\u0564\u002C\u0020\u0565\u002C\u0020\u0566\u002C\u0020\u0567\u002C\u0020\u0568\u002C\u0020\u0569\u002C\u0020\u056A\u002C\u0020\u056B\u002C\u0020\u056C\u002C\u0020\u056D\u002C\u0020\u056E\u002C\u0020\u056F\u002C\u0020\u0570\u002C\u0020\u0571\u002C\u0020\u0572\u002C\u0020\u0573\u002C\u0020\u0574\u002C\u0020\u0575\u002C\u0020\u0576\u002C\u0020\u0577\u002C\u0020\u0578\u002C\u0020\u0579\u002C\u0020\u057A\u002C\u0020\u057B\u002C\u0020\u057C\u002C\u0020\u057D\u002C\u0020\u057E\u002C\u0020\u057F\u002C\u0020\u0580\u002C\u0020\u0581\u002C\u0020\u0582\u002C\u0020\u0583\u002C\u0020\u0584\u002C\u0020\u0587\u057E\u002C\u0020\u0585\u002C\u0020\u0586";
                break;
            case "az":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0259\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u011F\u002C\u0020\u0068\u002C\u0020\u0078\u002C\u0020\u0131\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u0071\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F6\u002C\u0020\u0070\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u015F\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "ar":
                return null;
            case "be":
                str = "\u0430\u002C\u0020\u0431\u002C\u0020\u0432\u002C\u0020\u0433\u002C\u0020\u0434\u002C\u0020\u0435\u002C\u0020\u0451\u002C\u0020\u0436\u002C\u0020\u0437\u002C\u0020\u0438\u002C\u0020\u0456\u002C\u0020\u0439\u002C\u0020\u043A\u002C\u0020\u043B\u002C\u0020\u043C\u002C\u0020\u043D\u002C\u0020\u043E\u002C\u0020\u043F\u002C\u0020\u0440\u002C\u0020\u0441\u002C\u0020\u0442\u002C\u0020\u0443\u002C\u0020\u045E\u002C\u0020\u0444\u002C\u0020\u0445\u002C\u0020\u0446\u002C\u0020\u0447\u002C\u0020\u0448\u002C\u0020\u0449\u002C\u0020\u044A\u002C\u0020\u044B\u002C\u0020\u044C\u002C\u0020\u044D\u002C\u0020\u044E\u002C\u0020\u044F";
                break;
            case "bg":
                str = "\u0430\u002C\u0020\u0431\u002C\u0020\u0432\u002C\u0020\u0433\u002C\u0020\u0434\u002C\u0020\u0435\u002C\u0020\u0436\u002C\u0020\u0437\u002C\u0020\u0438\u002C\u0020\u0439\u002C\u0020\u043A\u002C\u0020\u043B\u002C\u0020\u043C\u002C\u0020\u043D\u002C\u0020\u043E\u002C\u0020\u043F\u002C\u0020\u0440\u002C\u0020\u0441\u002C\u0020\u0442\u002C\u0020\u0443\u002C\u0020\u0444\u002C\u0020\u0445\u002C\u0020\u0446\u002C\u0020\u0447\u002C\u0020\u0448\u002C\u0020\u0449\u002C\u0020\u044A\u002C\u0020\u044C\u002C\u0020\u044E\u002C\u0020\u044F";
                break;
            case "ca":
                str = "\u0061\u002C\u0020\u00E0\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064\u002C\u0020\u00E9\u002C\u0020\u00E8\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u00EF\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F2\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "hr":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0107\u002C\u0020\u0064\u002C\u0020\u0064\u017E\u002C\u0020\u0111\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006C\u006A\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006E\u006A\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "cs":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0064\u002C\u0020\u010F\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u011B\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0063\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u0148\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0159\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0165\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u016F\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FD\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "da":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FC\u002C\u0020\u007A\u002C\u0020\u00E6\u002C\u0020\u00E4\u002C\u0020\u00F8\u002C\u0020\u00F6\u002C\u0020\u00E5\u002C\u0020\u0061\u0061";
                break;
            case "nl":
                str = "\u0061\u002C\u0020\u00E4\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00EB\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00EF\u002C\u0020\u0133\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F6\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "en":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "et":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u007A\u002C\u0020\u017E\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u00F5\u002C\u0020\u00E4\u002C\u0020\u00F6\u002C\u0020\u00FC\u002C\u0020\u0078\u002C\u0020\u0079";
                break;
            case "fi":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FC\u002C\u0020\u007A\u002C\u0020\u017E\u002C\u0020\u00E5\u002C\u0020\u00E4\u002C\u0020\u00E6\u002C\u0020\u00F6\u002C\u0020\u00F8";
                break;
            case "fr":
                str = "\u0061\u002C\u0020\u00E0\u002C\u0020\u00E2\u002C\u0020\u00E6\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u00E8\u002C\u0020\u00EA\u002C\u0020\u00EB\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00EE\u002C\u0020\u00EF\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u00F1\u002C\u0020\u006F\u002C\u0020\u00F4\u002C\u0020\u0153\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00F9\u002C\u0020\u00FB\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FF\u002C\u0020\u007A";
                break;
            case "de":
                str = "\u0061\u002C\u0020\u00E4\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F6\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u00DF\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "ka":
                str = "\u10D0\u002C\u0020\u10D1\u002C\u0020\u10D2\u002C\u0020\u10D3\u002C\u0020\u10D4\u002C\u0020\u10D5\u002C\u0020\u10D6\u002C\u0020\u10D7\u002C\u0020\u10D8\u002C\u0020\u10D9\u002C\u0020\u10DA\u002C\u0020\u10DB\u002C\u0020\u10DC\u002C\u0020\u10DD\u002C\u0020\u10DE\u002C\u0020\u10DF\u002C\u0020\u10E0\u002C\u0020\u10E1\u002C\u0020\u10E2\u002C\u0020\u10F3\u002C\u0020\u10E3\u002C\u0020\u10E4\u002C\u0020\u10E5\u002C\u0020\u10E6\u002C\u0020\u10E7\u002C\u0020\u10E8\u002C\u0020\u10E9\u002C\u0020\u10EA\u002C\u0020\u10EB\u002C\u0020\u10EC\u002C\u0020\u10ED\u002C\u0020\u10EE\u002C\u0020\u10F4\u002C\u0020\u10EF\u002C\u0020\u10F0";
                break;
            case "el":
                str = "\u03B1\u002C\u0020\u03AC\u002C\u0020\u03B2\u002C\u0020\u03B3\u002C\u0020\u03B4\u002C\u0020\u03B5\u002C\u0020\u03AD\u002C\u0020\u03B6\u002C\u0020\u03B7\u002C\u0020\u03AE\u002C\u0020\u03B8\u002C\u0020\u03B9\u002C\u0020\u03AF\u002C\u0020\u03CA\u002C\u0020\u0390\u002C\u0020\u03BA\u002C\u0020\u03BB\u002C\u0020\u03BC\u002C\u0020\u03BD\u002C\u0020\u03BE\u002C\u0020\u03BF\u002C\u0020\u03CC\u002C\u0020\u03C0\u002C\u0020\u03C1\u002C\u0020\u03C3\u03C2\u002C\u0020\u03C4\u002C\u0020\u03C5\u002C\u0020\u03CD\u002C\u0020\u03CB\u002C\u0020\u03B0\u002C\u0020\u03C6\u002C\u0020\u03C7\u002C\u0020\u03C8\u002C\u0020\u03C9\u002C\u0020\u03CE";
                break;
            case "hu":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0063\u0073\u002C\u0020\u0064\u002C\u0020\u0064\u007A\u002C\u0020\u0064\u007A\u0073\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0067\u0079\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006C\u0079\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006E\u0079\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F6\u002C\u0020\u0151\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0073\u007A\u002C\u0020\u0074\u002C\u0020\u0074\u0079\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u00FC\u002C\u0020\u0171\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u007A\u0073";
                break;
            case "it":
                str = "\u0061\u002C\u0020\u00E0\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u00E8\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u00EC\u002C\u0020\u00EF\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F2\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u00F9\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "lv":
                str = "\u0061\u002C\u0020\u0101\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0113\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0123\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u012B\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u0137\u002C\u0020\u006C\u002C\u0020\u013C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u0146\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u016B\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "lt":
                str = "\u0061\u002C\u0020\u0105\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0119\u002C\u0020\u0117\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u012F\u002C\u0020\u0079\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0173\u002C\u0020\u016B\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "mk":
                str = "\u0430\u002C\u0020\u0431\u002C\u0020\u0432\u002C\u0020\u0433\u002C\u0020\u0434\u002C\u0020\u0453\u002C\u0020\u0435\u002C\u0020\u0436\u002C\u0020\u0437\u002C\u0020\u0455\u002C\u0020\u0438\u002C\u0020\u0458\u002C\u0020\u043A\u002C\u0020\u043B\u002C\u0020\u0459\u002C\u0020\u043C\u002C\u0020\u043D\u002C\u0020\u045A\u002C\u0020\u043E\u002C\u0020\u043F\u002C\u0020\u0440\u002C\u0020\u0441\u002C\u0020\u0442\u002C\u0020\u045C\u002C\u0020\u0443\u002C\u0020\u0444\u002C\u0020\u0445\u002C\u0020\u0446\u002C\u0020\u0447\u002C\u0020\u045F\u002C\u0020\u0448";
                break;
            case "no":
                str = "\u0061\u002C\u0020\u00E0\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u00EA\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F2\u002C\u0020\u00F4\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FC\u002C\u0020\u007A\u002C\u0020\u00E6\u002C\u0020\u00E4\u002C\u0020\u00F8\u002C\u0020\u00F6\u002C\u0020\u00E5";
                break;
            case "pl":
                str = "\u0061\u002C\u0020\u0105\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0107\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0119\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u0142\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u0144\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u015B\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u017A\u002C\u0020\u017C";
                break;
            case "pt":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u00E0\u002C\u0020\u00E2\u002C\u0020\u00E3\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u00EA\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F4\u002C\u0020\u00F5\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "ro":
                str = "\u0061\u002C\u0020\u0103\u002C\u0020\u00E2\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00EE\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u015F\u002C\u0020\u0074\u002C\u0020\u0163\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "ru":
                str = "";
                break;
            case "sr":
                str = "\u0430\u002C\u0020\u0431\u002C\u0020\u0432\u002C\u0020\u0433\u002C\u0020\u0434\u002C\u0020\u0435\u002C\u0020\u0451\u002C\u0020\u0436\u002C\u0020\u0437\u002C\u0020\u0438\u002C\u0020\u0439\u002C\u0020\u043A\u002C\u0020\u043B\u002C\u0020\u043C\u002C\u0020\u043D\u002C\u0020\u043E\u002C\u0020\u043F\u002C\u0020\u0440\u002C\u0020\u0441\u002C\u0020\u0442\u002C\u0020\u0443\u002C\u0020\u0444\u002C\u0020\u0445\u002C\u0020\u0446\u002C\u0020\u0447\u002C\u0020\u0448\u002C\u0020\u0449\u002C\u0020\u044A\u002C\u0020\u044B\u002C\u0020\u044C\u002C\u0020\u044D\u002C\u0020\u044E\u002C\u0020\u044F";
                break;
            case "sk":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u00E4\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0064\u002C\u0020\u010F\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0063\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u013A\u002C\u0020\u013E\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u0148\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u00F4\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0155\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0165\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u00FD\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "sl":
                str = "\u0061\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u010D\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0161\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A\u002C\u0020\u017E";
                break;
            case "es":
                str = "\u0061\u002C\u0020\u00E1\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u00ED\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u00F1\u002C\u0020\u006F\u002C\u0020\u00F3\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FA\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "sv":
                str = "\u0061\u002C\u0020\u00E0\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u00E9\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u0068\u002C\u0020\u0069\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u0020\u00FC\u002C\u0020\u007A\u002C\u0020\u00E5\u002C\u0020\u00E4\u002C\u0020\u00E6\u002C\u0020\u00F6\u002C\u0020\u00F8";
                break;
            case "tr":
                str = "\u0061\u002C\u0020\u00E2\u002C\u0020\u0062\u002C\u0020\u0063\u002C\u0020\u00E7\u002C\u0020\u0064\u002C\u0020\u0065\u002C\u0020\u0066\u002C\u0020\u0067\u002C\u0020\u011F\u002C\u0020\u0068\u002C\u0020\u0131\u002C\u0020\u0069\u002C\u0020\u00EE\u002C\u0020\u006A\u002C\u0020\u006B\u002C\u0020\u006C\u002C\u0020\u006D\u002C\u0020\u006E\u002C\u0020\u006F\u002C\u0020\u00F6\u002C\u0020\u0070\u002C\u0020\u0071\u002C\u0020\u0072\u002C\u0020\u0073\u002C\u0020\u015F\u002C\u0020\u0074\u002C\u0020\u0075\u002C\u0020\u00FB\u002C\u0020\u00FC\u002C\u0020\u0076\u002C\u0020\u0077\u002C\u0020\u0078\u002C\u0020\u0079\u002C\u0020\u007A";
                break;
            case "uk":
                str = "\u0430\u002C\u0020\u0431\u002C\u0020\u0432\u002C\u0020\u0433\u002C\u0020\u0491\u002C\u0020\u0434\u002C\u0020\u0435\u002C\u0020\u0454\u002C\u0020\u0436\u002C\u0020\u0437\u002C\u0020\u0438\u002C\u0020\u0456\u002C\u0020\u0457\u002C\u0020\u0439\u002C\u0020\u043A\u002C\u0020\u043B\u002C\u0020\u043C\u002C\u0020\u043D\u002C\u0020\u043E\u002C\u0020\u043F\u002C\u0020\u0440\u002C\u0020\u0441\u002C\u0020\u0442\u002C\u0020\u0443\u002C\u0020\u0444\u002C\u0020\u0445\u002C\u0020\u0446\u002C\u0020\u0447\u002C\u0020\u0448\u002C\u0020\u0449\u002C\u0020\u044A\u002C\u0020\u044B\u002C\u0020\u044D\u002C\u0020\u044E\u002C\u0020\u044F\u002C\u0020\u044C";
                break;
            default:
                break;
        }
        return Arrays.asList(str.split("\\s*,\\s*"));
    }
}
