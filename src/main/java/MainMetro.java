import java.util.List;
public class MainMetro {

    public static void main(String[] args) {
        try {
            // Chargement du graphe du métro
            Graphe metro = LireReseau.lire("src/main/java/metro.txt");

            // 5 trajets de test
            String[][] trajets = {
                    {"1", "10"},    // Grande Arche -> Châtelet
                    {"25", "45"},   // République -> Gare du Nord
                    {"50", "75"},   // Bastille -> Trocadéro
                    {"100", "125"}, // Montparnasse -> Opéra
                    {"150", "175"}  // Nation -> Invalides
            };

            System.out.println("COMPARAISON AVEC ET SANS PÉNALITÉ DE CHANGEMENT DE LIGNE");
            System.out.println("========================================================");
            System.out.println();

            BellmanFord bf = new BellmanFord();
            Dijkstra dijkstra = new Dijkstra();

            // Tableau de résultats
            System.out.printf("DÉPART       ARRIVÉ      CHEMIN SANS PÉNALITÉ       TEMPS BF      TEMPS DIJKSTRA    TEMPS BF v2   TEMPS DIJKSTRA v2");
            System.out.println("=".repeat(160));

            for (String[] trajet : trajets) {
                String depart = trajet[0];
                String arrivee = trajet[1];

                System.out.println("\n--- TRAJET : " + depart + " -> " + arrivee + " ---");

                // ========== VERSION ORIGINALE (sans pénalité) ==========

                // Bellman-Ford original
                long startTime = System.nanoTime();
                Valeurs resultBF = bf.resoudre(metro, depart);
                long endTime = System.nanoTime();
                long tempsBF = endTime - startTime;

                // Dijkstra original
                startTime = System.nanoTime();
                Valeurs resultDijkstra = dijkstra.resoudre(metro, depart);
                endTime = System.nanoTime();
                long tempsDijkstra = endTime - startTime;

                // Calcul du chemin sans pénalité
                List<String> cheminSansPenalite = resultBF.calculerChemin(arrivee);
                double distanceSansPenalite = resultBF.getValeur(arrivee);

                // ========== VERSION 2 (avec pénalité) ==========

                // Bellman-Ford v2                startTime = System.nanoTime();
                Valeurs resultBF2 = bf.resoudre2(metro, depart);
                endTime = System.nanoTime();
                long tempsBF2 = endTime - startTime;

                // Dijkstra v2
                startTime = System.nanoTime();
                Valeurs resultDijkstra2 = dijkstra.resoudre2(metro, depart);
                endTime = System.nanoTime();
                long tempsDijkstra2 = endTime - startTime;

                // Calcul du chemin avec pénalité
                List<String> cheminAvecPenalite = resultBF2.calculerChemin(arrivee);
                double distanceAvecPenalite = resultBF2.getValeur(arrivee);

                // ========== AFFICHAGE DES RÉSULTATS ==========

                System.out.println("SANS PÉNALITÉ :");
                System.out.println("Chemin : " + cheminSansPenalite);
                System.out.println("Distance : " + distanceSansPenalite + " unités");
                System.out.println("Temps Bellman-Ford : " + tempsBF + " ns");
                System.out.println("Temps Dijkstra : " + tempsDijkstra + " ns");

                System.out.println("\nAVEC PÉNALITÉ (changement de ligne = +10) :");
                System.out.println("Chemin : " + cheminAvecPenalite);
                System.out.println("Distance : " + distanceAvecPenalite + " unités");
                System.out.println("Temps Bellman-Ford v2 : " + tempsBF2 + " ns");
                System.out.println("Temps Dijkstra v2 : " + tempsDijkstra2 + " ns");

                // Analyse des changements
                int nbChangementsSans = compterChangementsLigne(cheminSansPenalite, metro);
                int nbChangementsAvec = compterChangementsLigne(cheminAvecPenalite, metro);

                System.out.println("\n");
                System.out.println("Changements de ligne (sans pénalité) : " + nbChangementsSans);
                System.out.println("Changements de ligne (avec pénalité) : " + nbChangementsAvec);
                System.out.println("Différence de distance : " +
                        String.format("%.2f", distanceAvecPenalite - distanceSansPenalite));

                if (cheminAvecPenalite.size() != cheminSansPenalite.size() ||
                        !cheminAvecPenalite.equals(cheminSansPenalite)) {
                    System.out.println("=> Le chemin a changé avec la pénalité !");
                } else {
                    System.out.println("=> Le chemin reste identique");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Compte le nombre de changements de ligne dans un chemin
     */
    private static int compterChangementsLigne(List<String> chemin, Graphe metro) {
        if (chemin.size() <= 1) return 0;

        int changements = 0;
        String ligneActuelle = null;

        for (int i = 0; i < chemin.size() - 1; i++) {
            String station1 = chemin.get(i);
            String station2 = chemin.get(i + 1);

            // Trouver la ligne de l'arc entre station1 et station2
            List<Arc> arcs = metro.suivants(station1);
            for (Arc arc : arcs) {
                if (arc.getDest().equals(station2)) {
                    String nouvelleLigne = arc.getLigne();
                    if (ligneActuelle != null && !ligneActuelle.equals(nouvelleLigne)) {
                        changements++;
                    }
                    ligneActuelle = nouvelleLigne;
                    break;
                }
            }
        }

        return changements;
    }
}