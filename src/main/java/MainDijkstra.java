/**
 * Programme principal pour tester l'algorithme de Dijkstra
 * Question 15 : MainDijkstra qui utilise un graphe par défaut,
 * calcule les chemins les plus courts et affiche des chemins pour des nœuds donnés
 */
public class MainDijkstra {

    public static void main(String[] args) {
        System.out.println("=== Test de l'algorithme de Dijkstra ===\n");

        // Utilise un graphe par défaut (celui de la Figure 1)
        GrapheListe graphe = creerGrapheParDefaut();

        System.out.println("Graphe utilisé :");
        System.out.println(graphe.toString());
        System.out.println();

        // Créer l'instance de l'algorithme de Dijkstra
        Dijkstra dijkstra = new Dijkstra();

        // Calcule les chemins les plus courts depuis A
        String noeudDepart = "A";
        System.out.println("Calcul des plus courts chemins depuis le nœud : " + noeudDepart);

        Valeurs resultats = dijkstra.resoudre(graphe, noeudDepart);

        System.out.println("Résultats de l'algorithme de Dijkstra :");
        System.out.println(resultats.toString());

        // Affiche des chemins pour des nœuds donnés
        String[] noeudsDestination = {"B", "C", "D", "E"};

        System.out.println("Chemins calculés depuis " + noeudDepart + " :");
        System.out.println("----------------------------------------");

        for (String destination : noeudsDestination) {
            afficherChemin(resultats, noeudDepart, destination);
        }

        System.out.println();

        // Test avec un autre nœud de départ
        testAvecAutreDepart(graphe, dijkstra, "B");

        // Comparaison avec Bellman-Ford
        comparerAvecBellmanFord(graphe, noeudDepart);
    }

    /**
     * Crée le graphe par défaut (Figure 1 du sujet)
     * @return le graphe créé
     */
    private static GrapheListe creerGrapheParDefaut() {
        GrapheListe graphe = new GrapheListe();

        // Ajout des arcs selon la Figure 1
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);

        return graphe;
    }

    /**
     * Affiche le chemin le plus court vers une destination
     * @param resultats les résultats de l'algorithme
     * @param depart le nœud de départ
     * @param destination le nœud de destination
     */
    private static void afficherChemin(Valeurs resultats, String depart, String destination) {
        double distance = resultats.getValeur(destination);

        if (distance == Double.MAX_VALUE) {
            System.out.println(depart + " -> " + destination + " : INACCESSIBLE");
        } else {
            // Reconstruction du chemin
            java.util.List<String> chemin = resultats.calculerChemin(destination);

            System.out.printf("%s -> %s : ", depart, destination);
            System.out.printf("Distance = %.0f, ", distance);
            System.out.print("Chemin = ");

            for (int i = 0; i < chemin.size(); i++) {
                System.out.print(chemin.get(i));
                if (i < chemin.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Test avec un autre nœud de départ
     * @param graphe le graphe à utiliser
     * @param dijkstra l'instance de l'algorithme
     * @param depart le nouveau nœud de départ
     */
    private static void testAvecAutreDepart(GrapheListe graphe, Dijkstra dijkstra, String depart) {
        System.out.println("Test avec un autre nœud de départ : " + depart);
        System.out.println("------------------------------------------");

        Valeurs resultats = dijkstra.resoudre(graphe, depart);

        System.out.println("Distances depuis " + depart + " :");
        for (String noeud : graphe.listeNoeuds()) {
            if (!noeud.equals(depart)) {
                double distance = resultats.getValeur(noeud);
                if (distance == Double.MAX_VALUE) {
                    System.out.println("  " + depart + " -> " + noeud + " : INACCESSIBLE");
                } else {
                    System.out.printf("  %s -> %s : %.0f%n", depart, noeud, distance);
                }
            }
        }
        System.out.println();
    }

    /**
     * Compare les résultats de Dijkstra avec ceux de Bellman-Ford
     * @param graphe le graphe à utiliser
     * @param depart le nœud de départ
     */
    private static void comparerAvecBellmanFord(GrapheListe graphe, String depart) {
        System.out.println("Comparaison Dijkstra vs Bellman-Ford depuis " + depart + " :");
        System.out.println("--------------------------------------------------------");

        Dijkstra dijkstra = new Dijkstra();
        BellmanFord bellmanFord = new BellmanFord();

        // Mesure du temps d'exécution
        long startTime, endTime;

        // Dijkstra
        startTime = System.nanoTime();
        Valeurs resultatsDijkstra = dijkstra.resoudre(graphe, depart);
        endTime = System.nanoTime();
        long tempsDijkstra = endTime - startTime;

        // Bellman-Ford
        startTime = System.nanoTime();
        Valeurs resultatsBellmanFord = bellmanFord.resoudre(graphe, depart);
        endTime = System.nanoTime();
        long tempsBellmanFord = endTime - startTime;

        System.out.printf("Temps d'exécution Dijkstra : %d nanoseconds%n", tempsDijkstra);
        System.out.printf("Temps d'exécution Bellman-Ford : %d nanoseconds%n", tempsBellmanFord);

        // Vérification que les résultats sont identiques
        boolean resultatsIdentiques = true;
        for (String noeud : graphe.listeNoeuds()) {
            if (Math.abs(resultatsDijkstra.getValeur(noeud) - resultatsBellmanFord.getValeur(noeud)) > 0.001) {
                resultatsIdentiques = false;
                break;
            }
        }

        if (resultatsIdentiques) {
            System.out.println("✓ Les deux algorithmes produisent des résultats identiques");
        } else {
            System.out.println("✗ ATTENTION : Les algorithmes produisent des résultats différents !");
        }

        System.out.println();
    }
}