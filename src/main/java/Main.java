/**
 * Classe principale pour tester la représentation du graphe
 */
public class Main {
    public static void main(String[] args) {
        // Création du graphe de la Figure 1
        GrapheListe graphe = new GrapheListe();

        // Ajout des arcs comme dans la Figure 1
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);

        // Affichage du graphe
        System.out.println("Graphe de la Figure 1:");
        System.out.println(graphe);

        // Test de la méthode listeNoeuds
        System.out.println("Liste des nœuds:");
        for (String noeud : graphe.listeNoeuds()) {
            System.out.print(noeud + " ");
        }
        System.out.println();

        // Test de la méthode suivants pour le nœud A
        System.out.println("Nœuds adjacents à A:");
        for (Arc arc : graphe.suivants("A")) {
            System.out.print(arc + " ");
        }
        System.out.println();
    }
}