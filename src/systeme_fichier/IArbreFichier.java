package systeme_fichier;

import java.util.List;

public interface IArbreFichier {

    public String cheminAbsolu();
    
    public String cheminRelatif(IArbreFichier debut);

    public boolean supprimerNoeud();

    public String dessiner();

    public String getNom();

    public IArbreFichier getPere();

    public void setPere(IArbreFichier pere);

    public String getContenu();

    public int getTaille();

    public void setNom(String nom);

    public boolean isFichier();

    public boolean estPremierFils();

    public IArbreFichier getPremierFils();

    public List<IArbreFichier> getFreres();

    public IArbreFichier getFrereDroit();

    public IArbreFichier getFrereGauche();

    public void setFrereGauche(IArbreFichier a);

    public void setFrereDroit(IArbreFichier a);

}
