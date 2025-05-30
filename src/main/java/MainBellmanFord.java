/**
 * Classe principale pour tester l'algorithme de Bellman-Ford
 */
public class MainBellmanFord {

    public static void main(String[] args) {
        // Création du graphe de la Figure 1
        GrapheListe graphe = new GrapheListe();

        // Ajout des arcs du graphe exemple
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);

        System.out.println("=== Graphe créé ===");
        System.out.println(graphe.toString());

        // Application de l'algorithme de Bellman-Ford
        BellmanFord bellmanFord = new BellmanFord();
        Valeurs resultats = bellmanFord.resoudre(graphe, "A");

        System.out.println("\n=== Résultats de l'algorithme du point fixe (Bellman-Ford) ===");
        System.out.println("Nœud de départ : A");
        System.out.println();
        System.out.println("Distances et parents calculés :");
        System.out.println(resultats.toString());

        // Vérification des valeurs attendues
        System.out.println("=== Vérification des résultats ===");
        System.out.println("Distance A->A : " + resultats.getValeur("A") + " (attendu : 0.0)");
        System.out.println("Distance A->B : " + resultats.getValeur("B") + " (attendu : 12.0)");
        System.out.println("Distance A->C : " + resultats.getValeur("C") + " (attendu : 76.0)");
        System.out.println("Distance A->D : " + resultats.getValeur("D") + " (attendu : 66.0)");
        System.out.println("Distance A->E : " + resultats.getValeur("E") + " (attendu : 23.0)");

        System.out.println("\nParents :");
        System.out.println("Parent de A : " + resultats.getParent("A") + " (attendu : null)");
        System.out.println("Parent de B : " + resultats.getParent("B") + " (attendu : A)");
        System.out.println("Parent de C : " + resultats.getParent("C") + " (attendu : D)");
        System.out.println("Parent de D : " + resultats.getParent("D") + " (attendu : E)");
        System.out.println("Parent de E : " + resultats.getParent("E") + " (attendu : B)");

        // Le plus court chemin A->C devrait être [A, B, E, D, C] avec un coût de 76
        System.out.println("\n=== Vérification du plus court chemin A->C ===");
        System.out.println("Chemin attendu : A -> B -> E -> D -> C");
        System.out.println("Coût attendu : 12 + 11 + 43 + 10 = 76");
    }
}