import java.util.ArrayList;
import java.util.List;

public class ComparaisonAlgorithmes {

    public static void main(String[] args) {
        System.out.println("Comparaison Dijkstra vs Bellman-Ford");
        System.out.println("=====================================");

        // Liste des graphes à tester
        List<Graphe> graphes = new ArrayList<>();

        // Graphe 1 : Graphe simple (exemple du sujet)
        graphes.add(creerGrapheExemple());

        // Graphe 2 : Graphe linéaire
        graphes.add(creerGrapheLineaire());

        // Graphe 3 : Graphe en étoile
        graphes.add(creerGrapheEtoile());

        // Graphe 4 : Graphe dense (8 nœuds, connectivité élevée)
        graphes.add(creerGrapheDense());

        // Graphe 5 : Graphe clairsemé
        graphes.add(creerGrapheClairseme());

        // Comparaison sur chaque graphe
        for (int i = 0; i < graphes.size(); i++) {
            System.out.println("\n-- Graphe " + (i + 1) + " --");
            Graphe g = graphes.get(i);
            String noeudDepart = g.listeNoeuds().get(0); // Premier nœud comme départ

            comparer(g, noeudDepart, "Graphe " + (i + 1));
        }
    }

    /**
     * Compare les performances de Dijkstra et Bellman-Ford
     */
    private static void comparer(Graphe g, String depart, String nomGraphe) {
        BellmanFord bf = new BellmanFord();
        Dijkstra dijkstra = new Dijkstra();

        // Test Dijkstra
        long startTime = System.nanoTime();
        Valeurs resultDijkstra = dijkstra.resoudre(g, depart);
        long endTime = System.nanoTime();
        long tempsDijkstra = endTime - startTime;

        // Test Bellman-Ford
        startTime = System.nanoTime();
        Valeurs resultBellmanFord = bf.resoudre(g, depart);
        endTime = System.nanoTime();
        long tempsBellmanFord = endTime - startTime;

        // Affichage des résultats
        System.out.println("Temps d'exécution Dijkstra : " + tempsDijkstra + " nanosecondes");
        System.out.println("Temps d'exécution Bellman-Ford : " + tempsBellmanFord + " nanosecondes");

        // Vérification que les deux algorithmes donnent les mêmes résultats
        boolean identiques = verifierResultatsIdentiques(resultDijkstra, resultBellmanFord, g.listeNoeuds());
        System.out.println("Les deux algorithmes produisent des résultats identiques : " + identiques);

        if (tempsDijkstra < tempsBellmanFord) {
            System.out.println("Dijkstra est plus rapide");
        } else if (tempsBellmanFord < tempsDijkstra) {
            System.out.println("Bellman-Ford est plus rapide");
        } else {
            System.out.println("Temps d'exécution similaires");
        }
    }

    /**
     * Crée le graphe exemple du sujet (Figure 1)
     */
    private static Graphe creerGrapheExemple() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12);
        g.ajouterArc("A", "D", 87);
        g.ajouterArc("B", "E", 11);
        g.ajouterArc("C", "A", 19);
        g.ajouterArc("D", "B", 23);
        g.ajouterArc("D", "C", 10);
        g.ajouterArc("E", "D", 43);
        return g;
    }

    /**
     * Crée un graphe linéaire A -> B -> C -> D -> E
     */
    private static Graphe creerGrapheLineaire() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 10);
        g.ajouterArc("B", "C", 15);
        g.ajouterArc("C", "D", 20);
        g.ajouterArc("D", "E", 25);
        return g;
    }

    /**
     * Crée un graphe en étoile avec A au centre
     */
    private static Graphe creerGrapheEtoile() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 5);
        g.ajouterArc("A", "C", 8);
        g.ajouterArc("A", "D", 12);
        g.ajouterArc("A", "E", 15);
        g.ajouterArc("A", "F", 18);
        // Quelques arcs de retour
        g.ajouterArc("B", "A", 5);
        g.ajouterArc("C", "A", 8);
        return g;
    }

    /**
     * Crée un graphe dense (beaucoup de connexions)
     */
    private static Graphe creerGrapheDense() {
        GrapheListe g = new GrapheListe();
        String[] noeuds = {"A", "B", "C", "D", "E", "F", "G", "H"};

        // Connexions multiples entre la plupart des nœuds
        for (int i = 0; i < noeuds.length; i++) {
            for (int j = 0; j < noeuds.length; j++) {
                if (i != j && Math.random() > 0.4) { // 60% de chance de connexion
                    double cout = 10 + Math.random() * 40; // Coût entre 10 et 50
                    g.ajouterArc(noeuds[i], noeuds[j], cout);
                }
            }
        }
        return g;
    }

    /**
     * Crée un graphe clairsemé (peu de connexions)
     */
    private static Graphe creerGrapheClairseme() {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 30);
        g.ajouterArc("B", "C", 25);
        g.ajouterArc("A", "D", 40);
        g.ajouterArc("D", "E", 20);
        g.ajouterArc("C", "F", 35);
        g.ajouterArc("E", "F", 15);
        g.ajouterArc("F", "G", 10);
        return g;
    }

    /**
     * Vérifie que les deux algorithmes produisent les mêmes résultats
     */
    private static boolean verifierResultatsIdentiques(Valeurs v1, Valeurs v2, List<String> noeuds) {
        for (String noeud : noeuds) {
            try {
                double val1 = v1.getValeur(noeud);
                double val2 = v2.getValeur(noeud);

                if (Math.abs(val1 - val2) > 0.001) { // Tolérance pour les doubles
                    return false;
                }
            } catch (Exception e) {
                // Si une valeur n'existe pas, on considère que c'est différent
                return false;
            }
        }
        return true;
    }
}