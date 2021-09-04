package Metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArbreT{

    private Noeud racine;

    public ArbreT() { racine=null; }

    static class Noeud
    {
        private int valeur;
        private ArrayList<Noeud> fils;

        public Noeud(int valeur) { this(valeur, new ArrayList<>()); }
        public Noeud(int valeur, ArrayList<Noeud> fils) { this.valeur = valeur; this.fils=fils; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Noeud noeud = (Noeud) o;
            return valeur == noeud.valeur;
        }

        @Override
        public int hashCode() {
            return Objects.hash(valeur);
        }

        @Override
        public String toString() {
            return "Noeud{" +
                    "valeur=" + valeur +
                    '}';
        }
    }

    public void inserer(int nouveauNoeud, int valeurPere)
    {
        // Cela signifie qu'il n'y a aucune valeur existante avant cette insertion
        // Création de la racine, puis retour
        if(racine == null){ racine = new Noeud(nouveauNoeud); return; }
        // Si la racine n'est pas null, alors on exécute la suite
        Noeud pere = chercher(valeurPere);
        if(pere != null){ pere.fils.add(new Noeud(nouveauNoeud)); }
        else{ System.out.println("Le père avec la valeur: " + valeurPere + " n'existe pas. Veuillez l'insérer."); }
    }

    public Noeud chercher(int valeur) { return chercher(valeur, racine); }
    private Noeud chercher(int valeur, Noeud noeudParcouru)
    {
        if(noeudParcouru.valeur == valeur){ return noeudParcouru; }
        for(Noeud fils: noeudParcouru.fils)
        {
            Noeud resultatRecherche = chercher(valeur, fils);
            if(resultatRecherche != null){ return resultatRecherche; }
        }
        return null;
    }

    // Principe de base: on part toujours de racine, car on a pas accès à toutes les références de la table
    // ce qui pourrait donner une réponse erronée puisque on a parcouru qu'une partie de l'arbreT
    public boolean contains(int valeur) { return chercher(valeur, racine) != null; }

    // Parcours généalogique de l'arbreT
    public List<Noeud> parcourirArbreGenealogique(int valeur)
    {
        List<Noeud> arbreGenealogique = new ArrayList<>();
        parcourirArbreGenealogique(valeur, racine, arbreGenealogique);
        return arbreGenealogique;
    }
    private List<Noeud> parcourirArbreGenealogique(int valeur, Noeud noeudParcouru, List<Noeud> arbreGenealogique)
    {
        if(noeudParcouru.fils != null)
        {
            for(Noeud fils : noeudParcouru.fils)
            {
                if(parcourirArbreGenealogique(valeur, fils, arbreGenealogique) != null)
                {
                    arbreGenealogique.add(noeudParcouru); return arbreGenealogique;
                }
            }
        }
        if(noeudParcouru.valeur == valeur) { arbreGenealogique.add(noeudParcouru); return arbreGenealogique; } // condition d'arrêt
        return null; // si le noeud n'est pas présent dans l'arbre
    }

    public int hauteur(int valeurRecherche) { return hauteur(valeurRecherche, racine, 0); }
    private int hauteur(int valeurRecherche, Noeud arbreDepart, int hauteur)
    {
        if(arbreDepart.valeur == valeurRecherche) { return hauteur; } // condition d'arrêt
        else if(arbreDepart.fils != null)
        {
            for(Noeud fils : arbreDepart.fils)
            {
                int previousCPT = hauteur(valeurRecherche, fils, hauteur+1);
                if(previousCPT != -1) return previousCPT; // mieux que previousCPT > hauteur
            }
        }
        return -1; // si le noeud n'est pas présent dans l'arbre
    }

    public int hauteur_iteratif(int valeurRecherche)
    {
        List<Noeud> etageActuel = new ArrayList<>(); // les noeuds dans l'étage actuel
        List<Noeud> etageSuivant = new ArrayList<>();
        etageActuel.add(racine);
        int cpt = 0;
        while (!etageActuel.isEmpty())
        {
            for(Noeud noeudActuel : etageActuel)
            {
                if(noeudActuel.valeur == valeurRecherche){ return cpt; }
                if(noeudActuel.fils != null){ etageSuivant.addAll(noeudActuel.fils); }
            }
            etageActuel = etageSuivant;
            etageSuivant = new ArrayList<>();
            cpt+=1;
        }
        return -1;
    }

    public Noeud supprimer(int valeurRecherchee)
    {
        Noeud arbrePourSupprimer = chercher(valeurRecherchee);
        if(arbrePourSupprimer != null){
            Noeud pere = parcourirArbreGenealogique(valeurRecherchee).get(1);
            pere.fils.addAll(arbrePourSupprimer.fils);
            pere.fils.remove(arbrePourSupprimer);
        }
        return racine;
    }

    public void parcoursEnLargeur()
    {
        if(racine == null){return;}
        List<Noeud> marked = new ArrayList<>();
        List<Noeud> noeudPourParcourir = new ArrayList<>();
        noeudPourParcourir.add(racine);
        while(!noeudPourParcourir.isEmpty())
        {
            Noeud noeudParcouru = noeudPourParcourir.remove(0);
            System.out.println(noeudParcouru);
            marked.add(noeudParcouru);
            if(noeudParcouru.fils != null){
                for(Noeud fils: noeudParcouru.fils)
                {
                    if(!marked.contains(fils))
                    {
                        noeudPourParcourir.add(fils);
                    }
                }
            }
        }
    }
}
