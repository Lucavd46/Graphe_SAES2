import java.util.*;

/**
 * Classe implémentant l'algorithme de Dijkstra pour la recherche du plus court chemin
 */
public class Dijkstra {

    /**
     * Implémente l'algorithme de Dijkstra pour trouver les plus courts chemins
     * depuis un nœud de départ vers tous les autres nœuds du graphe
     *
     * Algorithme de Dijkstra (pseudo-code) :
     * Entrées :
     *   G un graphe orienté avec une pondération positive des arcs (coût ou poids)
     *   A un sommet (départ) de G
     * Début
     *   Q <- {} // utilisation d'une liste de noeuds à traiter
     *   Pour chaque sommet v de G faire
     *     v.valeur <- Infini
     *     v.parent <- Indéfini
     *     Q <- Q U {v} // ajouter le sommet v à la liste Q
     *   Fin Pour
     *   A.valeur <- 0
     *   Tant que Q est un ensemble non vide faire
     *     u <- un sommet de Q telle que u.valeur est minimal
     *     // enlever le sommet u de la liste Q
     *     Q <- Q \ {u}
     *     Pour chaque sommet v de Q tel que l'arc (u,v) existe faire
     *       d <- u.valeur + poids(u,v)
     *       Si d < v.valeur
     *         // le chemin est plus interessant
     *         Alors v.valeur <- d
     *         v.parent <- u
     *       Fin Si
     *     Fin Pour
     *   Fin Tant que
     * Fin
     *
     * @param g le graphe sur lequel appliquer l'algorithme
     * @param depart le nœud de départ
     * @return un objet Valeurs contenant les distances et parents calculés
     */
    public Valeurs resoudre(Graphe g, String depart) {
        // Initialisation de l'objet Valeurs pour stocker les résultats
        Valeurs valeurs = new Valeurs();

        // Q <- {} // utilisation d'une liste de noeuds à traiter
        Set<String> Q = new HashSet<>();

        // Pour chaque sommet v de G faire
        for (String v : g.listeNoeuds()) {
            // v.valeur <- Infini
            valeurs.setValeur(v, Double.MAX_VALUE);
            // v.parent <- Indéfini (null en Java)
            valeurs.setParent(v, null);
            // Q <- Q U {v} // ajouter le sommet v à la liste Q
            Q.add(v);
        }

        // A.valeur <- 0
        valeurs.setValeur(depart, 0);

        // Tant que Q est un ensemble non vide faire
        while (!Q.isEmpty()) {
            // u <- un sommet de Q telle que u.valeur est minimal
            String u = trouverSommetValeurMinimale(Q, valeurs);

            // enlever le sommet u de la liste Q
            // Q <- Q \ {u}
            Q.remove(u);

            // Pour chaque sommet v de Q tel que l'arc (u,v) existe faire
            for (Arc arc : g.suivants(u)) {
                String v = arc.getDest();

                // Vérifier que v est encore dans Q
                if (Q.contains(v)) {
                    // d <- u.valeur + poids(u,v)
                    double d = valeurs.getValeur(u) + arc.getCout();

                    // Si d < v.valeur
                    if (d < valeurs.getValeur(v)) {
                        // le chemin est plus interessant
                        // Alors v.valeur <- d
                        valeurs.setValeur(v, d);
                        // v.parent <- u
                        valeurs.setParent(v, u);
                    }
                }
            }
        }

        return valeurs;
    }

    /**
     * Méthode utilitaire pour trouver le sommet avec la valeur minimale dans Q
     * @param Q ensemble des sommets non traités
     * @param valeurs objet contenant les valeurs des sommets
     * @return le sommet avec la valeur minimale
     */
    private String trouverSommetValeurMinimale(Set<String> Q, Valeurs valeurs) {
        String sommetMin = null;
        double valeurMin = Double.MAX_VALUE;

        for (String sommet : Q) {
            double valeur = valeurs.getValeur(sommet);
            if (valeur < valeurMin) {
                valeurMin = valeur;
                sommetMin = sommet;
            }
        }

        return sommetMin;
    }

    /**
     * Version 2 de l'algorithme de Dijkstra avec pénalité pour changement de ligne
     * @param g le graphe
     * @param depart le nœud de départ
     * @return les valeurs avec distances et parents
     */
    public Valeurs resoudre2(Graphe g, String depart) {
        Valeurs valeurs = new Valeurs();
        final double PENALITE_CHANGEMENT = 10.0;

        // Initialisation
        List<String> noeuds = g.listeNoeuds();
        List<String> Q = new ArrayList<>(); // Liste des nœuds à traiter

        for (String noeud : noeuds) {
            valeurs.setValeur(noeud, Double.MAX_VALUE);
            valeurs.setParent(noeud, null);
            Q.add(noeud);
        }
        valeurs.setValeur(depart, 0.0);

        // Boucle principale
        while (!Q.isEmpty()) {
            // Trouver le nœud avec la valeur minimale dans Q
            String u = null;
            double minValeur = Double.MAX_VALUE;
            for (String noeud : Q) {
                if (valeurs.getValeur(noeud) < minValeur) {
                    minValeur = valeurs.getValeur(noeud);
                    u = noeud;
                }
            }

            if (u == null) break;

            // Retirer u de Q
            Q.remove(u);

            // Traiter tous les voisins de u qui sont encore dans Q
            List<Arc> arcs = g.suivants(u);
            for (Arc arc : arcs) {
                String v = arc.getDest();

                if (Q.contains(v)) {
                    double coutArc = arc.getCout();

                    // Calculer la pénalité de changement de ligne
                    double penalite = 0.0;
                    String parent = valeurs.getParent(u);

                    if (parent != null) {
                        // Trouver la ligne utilisée pour arriver à u
                        String ligneArrivee = getLigneEntreNoeuds(g, parent, u);
                        // Ligne de l'arc vers v
                        String ligneSortante = arc.getLigne();

                        // Si changement de ligne, ajouter pénalité
                        if (ligneArrivee != null && ligneSortante != null &&
                                !ligneArrivee.equals(ligneSortante)) {
                            penalite = PENALITE_CHANGEMENT;
                        }
                    }

                    double d = valeurs.getValeur(u) + coutArc + penalite;

                    if (d < valeurs.getValeur(v)) {
                        valeurs.setValeur(v, d);
                        valeurs.setParent(v, u);
                    }
                }
            }
        }

        return valeurs;
    }

    /**
     * Méthode auxiliaire pour trouver la ligne entre deux nœuds
     */
    private String getLigneEntreNoeuds(Graphe g, String noeud1, String noeud2) {
        List<Arc> arcs = g.suivants(noeud1);
        for (Arc arc : arcs) {
            if (arc.getDest().equals(noeud2)) {
                return arc.getLigne();
            }
        }
        return null;
    }
}
