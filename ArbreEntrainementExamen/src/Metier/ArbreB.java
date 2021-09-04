package Metier;

import java.util.ArrayList;
import java.util.List;

public class ArbreB {

    // Pas de référence pour la racine dans chaque arbre

    private int valeur;
    private ArbreB filsG;
    private ArbreB filsD;

    public ArbreB(int valeur){ this.valeur = valeur; filsG = filsD = null;}

    // Par rapport à l'arbreT, il est obligatoire de passer la racine en paramètre,
    // car il n'y a pas d'attribut private ArbreB racine.
    public ArbreB inserer(int valeur, ArbreB racine)
    {
        if(racine == null){return new ArbreB(valeur); }
        else if(racine.valeur > valeur){ racine.filsG = inserer(valeur, racine.filsG); }
        else if(racine.valeur < valeur){ racine.filsD = inserer(valeur, racine.filsD); }
        return racine;
    }

    // Si l'arbre existe, on a une référence
    // Sinon on a la valeur null en retour
    public ArbreB chercher(int valeurRecherche, ArbreB arbreDepart)
    {
        if(arbreDepart == null){ return null;}
        else if(arbreDepart.valeur == valeurRecherche){ return arbreDepart; }
        else{
            if(arbreDepart.valeur > valeurRecherche){ return chercher(valeurRecherche, arbreDepart.filsG);}
            else{ return chercher(valeurRecherche, arbreDepart.filsD); }
        }
    }

    public boolean contains(int valeurRecherche, ArbreB arbreDepart) { return chercher(valeurRecherche, arbreDepart) != null; }

    List<ArbreB> arbreGenealogique = new ArrayList<>();
    public List<ArbreB> parcourirArbreGenealogique(int valeurRecherche, ArbreB noeudDepart)
    {
        if(noeudDepart.valeur == valeurRecherche){ arbreGenealogique.add(noeudDepart); }
        if(noeudDepart.valeur < valeurRecherche)
        {
            if(parcourirArbreGenealogique(valeurRecherche, noeudDepart.filsD) != null)
            {
                arbreGenealogique.add(noeudDepart);
                return arbreGenealogique;
            }
        }
        else if(noeudDepart.valeur > valeurRecherche)
        {
            if(parcourirArbreGenealogique(valeurRecherche, noeudDepart.filsG) != null)
            {
                arbreGenealogique.add(noeudDepart);
                return arbreGenealogique;
            }
        }
        return arbreGenealogique;
    }

    public int hauteur(ArbreB racine)
    {
        if(racine == null){ return -1;}
        int hauteurGauche = hauteur(racine.filsG);
        int hauteurDroite = hauteur(racine.filsD);
        return 1+Math.max(hauteurGauche, hauteurDroite);
    }

    public int hauteurNoeud(int valeurRecherchee, ArbreB racine)
    {
        arbreGenealogique.clear();
        if(chercher(valeurRecherchee, racine)!= null)
        {
            return parcourirArbreGenealogique(valeurRecherchee, racine).size();
        }
        return -1;
        //return chercher(valeurRecherchee, racine)!=null ? parcourirArbreGenealogique(valeurRecherchee, racine).size() : -1;
        //return parcourirArbreGenealogique(valeurRecherchee, racine).size();
    }

    public ArbreB maximum(ArbreB arbreParcouru)
    {
        if(arbreParcouru.filsD != null){ return maximum(arbreParcouru.filsD); }
        else{ return arbreParcouru;}
    }

    public ArbreB minimum(ArbreB arbreParcouru)
    {
        if(arbreParcouru.filsG != null){ return minimum(arbreParcouru.filsG);}
        else{ return arbreParcouru; }
    }

    //Méthode retournant la partie de l'arbre modifie après suppression
    public void supp(int valeurRecherche, ArbreB racine)
    {
        ArbreB arbrePourSupprimer = chercher(valeurRecherche, racine);
        // Si arbrePourSupprimer existe
        if(arbrePourSupprimer != null)
        {
            ArbreB pere = parcourirArbreGenealogique(valeurRecherche, racine).get(1);
            ArbreB predecesseur = maximum(chercher(valeurRecherche, racine).filsG);
            arbrePourSupprimer.valeur = predecesseur.valeur;
            /*
            // Si un seul fils (gauche ou droite)
            if(arbrePourSupprimer.filsG == null){ return arbrePourSupprimer.filsD; }
            else if(arbrePourSupprimer.filsD == null){ return arbrePourSupprimer.filsG; }

            else { // L'arbre à supprimer a deux fils
                ArbreB predecesseur = maximum(chercher(valeurRecherche, racine).filsG);
                chercher(valeurRecherche, racine).valeur = predecesseur.valeur;
                supprimerNoeud(predecesseur, racine);
            }
            return racine;
             */
        }
    }

    public ArbreB supprimer_v2(int x, ArbreB racine) { // x -> racine.contenu
        // =====  TODO: recherche le contenu x. D�s que trouv�, appelle supprimerNoeud en lui passant le noeud � supprimer  =====
        if( x == racine.valeur){
            if(racine.filsG == null && racine.filsD == null){
                return null;
            }else if(racine.filsD != null && racine.filsG != null){
                racine.valeur = maximum(racine).valeur;
                supprimer_v2(racine.valeur, racine.filsG);
                return racine;
            }else if(racine.filsD != null){
                return racine.filsD;
            }else {
                return racine.filsG;
            }
        }
        else if(x > racine.valeur){
            racine.filsD = supprimer_v2(x, racine.filsD);
        }else {
            racine.filsG = supprimer_v2(x, racine.filsG);
        }
        return racine;
    }



    public void supprimerNoeud(ArbreB noeudPourSupprimer, ArbreB racine)
    {
        ArbreB pere = parcourirArbreGenealogique(noeudPourSupprimer.valeur, racine).get(1);
        if(pere.filsD.equals(noeudPourSupprimer)){ pere.filsD =null; }
        else{pere.filsG =null;}
    }



    /******************************* Méthodes de parcours **************************/

    public void parcoursPrefixe(ArbreB noeudDepart)
    {
        if(noeudDepart == null){ return; }
        System.out.print(noeudDepart.valeur + " / ");
        parcoursPrefixe(noeudDepart.filsG);
        parcoursPrefixe(noeudDepart.filsD);
    }

    public void parcoursInfixe(ArbreB noeudDepart)
    {
        if(noeudDepart == null){ return; }
        parcoursPrefixe(noeudDepart.filsG);
        System.out.print(noeudDepart.valeur+ " / ");
        parcoursPrefixe(noeudDepart.filsD);
    }

    public void parcoursPostfixe(ArbreB noeudDepart)
    {
        if(noeudDepart == null){ return; }
        parcoursPrefixe(noeudDepart.filsG);
        parcoursPrefixe(noeudDepart.filsD);
        System.out.print(noeudDepart.valeur+ " / ");
    }

    @Override
    public String toString() {
        return "ArbreB{" +
                "valeur=" + valeur +
                '}';
    }
}
