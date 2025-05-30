import java.util.List;

/**
 * Classe implémentant l'algorithme de Bellman-Ford (point fixe)
 * pour la recherche du plus court chemin dans un graphe
 */
public class BellmanFord {

    /**
     * Résout le problème du plus court chemin en utilisant l'algorithme du point fixe
     *
     * @param g le graphe sur lequel appliquer l'algorithme
     * @param depart le nœud de départ
     * @return un objet Valeurs contenant les distances et parents de chaque nœud
     */
    public Valeurs resoudre(Graphe g, String depart) {
        // Initialisation de l'objet Valeurs
        Valeurs valeurs = new Valeurs();

        // Étape 1 : Initialisation des valeurs à +∞ pour tous les nœuds
        List<String> noeuds = g.listeNoeuds();
        for (String noeud : noeuds) {
            valeurs.setValeur(noeud, Double.MAX_VALUE);
            valeurs.setParent(noeud, null);
        }

        // Le nœud de départ a une distance de 0
        valeurs.setValeur(depart, 0.0);

        // Variable pour détecter s'il y a eu des modifications
        boolean modification = true;

        // Étape 2 : Répéter jusqu'à convergence (point fixe)
        while (modification) {
            modification = false;

            // Pour chaque nœud du graphe
            for (String noeudCourant : noeuds) {
                double valeurCourante = valeurs.getValeur(noeudCourant);

                // Si le nœud n'est pas encore atteignable, on passe au suivant
                if (valeurCourante == Double.MAX_VALUE) {
                    continue;
                }

                // Pour chaque arc partant de ce nœud
                List<Arc> arcs = g.suivants(noeudCourant);
                for (Arc arc : arcs) {
                    String voisin = arc.getDest();
                    double nouvelleValeur = valeurCourante + arc.getCout();

                    // Si on trouve un chemin plus court
                    if (nouvelleValeur < valeurs.getValeur(voisin)) {
                        valeurs.setValeur(voisin, nouvelleValeur);
                        valeurs.setParent(voisin, noeudCourant);
                        modification = true;
                    }
                }
            }
        }

        return valeurs;
    }

        /**
         * Version 2 de l'algorithme de Bellman-Ford avec pénalité pour changement de ligne
         * @param g le graphe
         * @param depart le nœud de départ
         * @return les valeurs avec distances et parents
         */
        public Valeurs resoudre2(Graphe g, String depart) {
            Valeurs valeurs = new Valeurs();
            final double PENALITE_CHANGEMENT = 10.0;

            // Initialisation : tous les nœuds à +∞ sauf le départ à 0
            List<String> noeuds = g.listeNoeuds();
            for (String noeud : noeuds) {
                valeurs.setValeur(noeud, Double.MAX_VALUE);
                valeurs.setParent(noeud, null);
            }
            valeurs.setValeur(depart, 0.0);

            // Variables pour détecter les changements
            boolean changement = true;

            // Boucle principale jusqu'au point fixe
            while (changement) {
                changement = false;

                for (String noeudCourant : noeuds) {
                    if (valeurs.getValeur(noeudCourant) != Double.MAX_VALUE) {
                        List<Arc> arcs = g.suivants(noeudCourant);

                        for (Arc arc : arcs) {
                            String voisin = arc.getDest();
                            double coutArc = arc.getCout();

                            // Calculer la pénalité de changement de ligne
                            double penalite = 0.0;
                            String parent = valeurs.getParent(noeudCourant);

                            if (parent != null) {
                                // Trouver la ligne utilisée pour arriver au nœud courant
                                String ligneArrivee = getLigneEntreNoeuds(g, parent, noeudCourant);
                                // Ligne de l'arc sortant
                                String ligneSortante = arc.getLigne();

                                // Si changement de ligne, ajouter pénalité
                                if (ligneArrivee != null && ligneSortante != null &&
                                        !ligneArrivee.equals(ligneSortante)) {
                                    penalite = PENALITE_CHANGEMENT;
                                }
                            }

                            double nouvelleDistance = valeurs.getValeur(noeudCourant) + coutArc + penalite;

                            if (nouvelleDistance < valeurs.getValeur(voisin)) {
                                valeurs.setValeur(voisin, nouvelleDistance);
                                valeurs.setParent(voisin, noeudCourant);
                                changement = true;
                            }
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
