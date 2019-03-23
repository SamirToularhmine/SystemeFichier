package systeme_fichier;

public class ToolBox {
    static String nChar(String c, int n, boolean t){
        String s=(n==0||t)?"":"│";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s;
    }
}
