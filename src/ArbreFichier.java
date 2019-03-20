public interface ArbreFichier extends Comparable<ArbreFichier> {

    public State getInfos();

    public void addOnRigthIgnoringFather(ArbreFichier a);

    public ArbreFichier getThis();

    public String draw(int n,ArbreFichier exRS);

    public String draw();

}
