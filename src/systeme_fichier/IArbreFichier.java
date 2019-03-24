package systeme_fichier;

import java.util.List;

public interface IArbreFichier {

    public String cheminAbsolu();

    public boolean supprimerNoeud();

    public String dessiner();

    public String getNom();

    public Dossier getPere();

    public void setPere(IArbreFichier pere);

    public String getContenu();

    public int getTaille();

    public void setNom(String nom);

    public boolean isFichier();

    public boolean estPremierFils();

    public List<IArbreFichier> getFreres();

    public IArbreFichier getFrereDroit();

    public IArbreFichier getFrereGauche();

    public void setFrereGauche(IArbreFichier a);

    public void setFrereDroit(IArbreFichier a);

}
