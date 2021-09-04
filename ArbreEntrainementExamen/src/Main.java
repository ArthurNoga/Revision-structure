import Metier.ArbreB;
import Metier.ArbreT;

public class Main {

    public static void main(String[] args) {
        System.out.println("**********************************");
        System.out.println("********** ARBRE TABLEAU *********");
        System.out.println("**********************************");
        ArbreT arbreT = new ArbreT();
        arbreT.inserer(0,0);
        arbreT.inserer(1,0);
        arbreT.inserer(2,0);
        arbreT.inserer(3,0);
        arbreT.inserer(4,0);
        arbreT.inserer(11,1);
        arbreT.inserer(12,1);
        arbreT.inserer(21,2); // Tester si le noeud père existe
        arbreT.inserer(55,5);
        System.out.println("Est-ce que l'arbre racine contient la valeur " + 112 + ": " + arbreT.contains(112));
        System.out.println("Arbre généalogique de la valeur 21: " + arbreT.parcourirArbreGenealogique(21));
        System.out.println("Hauteur de la valeur 21: " + arbreT.hauteur(21));
        System.out.println("Hauteur avec parcours itératif pour la valeur 21: " + arbreT.hauteur_iteratif(21));
        arbreT.supprimer(1);
        arbreT.parcoursEnLargeur();
        System.out.println();


        System.out.println("**********************************");
        System.out.println("********** ARBRE BINAIRE *********");
        System.out.println("**********************************");
        ArbreB arbreB = new ArbreB(7); // racine
        arbreB.inserer(1, arbreB);
        arbreB.inserer(2, arbreB);
        arbreB.inserer(10, arbreB);
        System.out.print("Parcours préfixe: ");arbreB.parcoursPrefixe(arbreB);System.out.println();
        System.out.print("Parcours infixe: ");arbreB.parcoursInfixe(arbreB);System.out.println();
        System.out.print("Parcours postfixe: ");arbreB.parcoursPostfixe(arbreB);System.out.println();
        System.out.println("Est-ce que l'arbre racine contient la valeur " + 1 + ": " + arbreB.contains(1, arbreB));
        System.out.println("Arbre généalogique de la valeur 10: " + arbreB.parcourirArbreGenealogique(10, arbreB));
        System.out.println("Hauteur de l'arbre depuis la racine: " + arbreB.hauteur(arbreB)); // gérer l'erreur
        System.out.println("Hauteur de la valeur 10: " + arbreB.hauteurNoeud(10, arbreB));
        arbreB.supprimer_v2(10, arbreB);
        //System.out.println(arbreB);
        System.out.print("Parcours préfixe: ");arbreB.parcoursPrefixe(arbreB);System.out.println();




    }
}
