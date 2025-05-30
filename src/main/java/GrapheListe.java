import java.util.ArrayList;
import java.util.List;

/**
 * Classe GrapheListe - Implémentation de l'interface Graphe
 * avec support pour les numéros de ligne de transport
 */
public class GrapheListe implements Graphe {

    /** Liste des nœuds du graphe */
    private ArrayList<String> noeuds;

    /** Liste des arcs partant de chaque nœud (même indice que noeuds) */
    private ArrayList<Arcs> adjacence;

    /**
     * Constructeur par défaut - crée un graphe vide
     */
    public GrapheListe() {
        this.noeuds = new ArrayList<>();
        this.adjacence = new ArrayList<>();
    }

    /**
     * Constructeur à partir d'un fichier (pour compatibilité avec question 16)
     * @param nomFichier le nom du fichier contenant la description du graphe
     */
    public GrapheListe(String nomFichier) {
        this();
        // Code pour charger depuis un fichier simple (format question 16)
        // Ce constructeur reste pour la compatibilité
        chargerDepuisFichierSimple(nomFichier);
    }

    /**
     * Retourne la liste de tous les nœuds du graphe
     * @return la liste des nœuds
     */
    @Override
    public List<String> listeNoeuds() {
        return new ArrayList<>(this.noeuds);
    }

    /**
     * Retourne la liste des arcs partant du nœud donné
     * @param n le nœud de départ
     * @return la liste des arcs partant de n
     */
    @Override
    public List<Arc> suivants(String n) {
        int indice = getIndice(n);
        if (indice == -1) {
            return new ArrayList<>(); // Nœud non trouvé
        }
        return this.adjacence.get(indice).getArcs();
    }

    /**
     * Trouve l'indice d'un nœud dans la liste des nœuds
     * @param n le nom du nœud recherché
     * @return l'indice du nœud, ou -1 si non trouvé
     */
    private int getIndice(String n) {
        return this.noeuds.indexOf(n);
    }

    /**
     * Ajoute un nœud au graphe s'il n'existe pas déjà
     * @param noeud le nom du nœud à ajouter
     */
    public void ajouterNoeud(String noeud) {
        if (!this.noeuds.contains(noeud)) {
            this.noeuds.add(noeud);
            this.adjacence.add(new Arcs());
        }
    }

    /**
     * Ajoute un arc entre deux nœuds (version originale sans ligne)
     * @param depart le nœud de départ
     * @param destination le nœud de destination
     * @param cout le coût de l'arc
     */
    public void ajouterArc(String depart, String destination, double cout) {
        ajouterArc(depart, destination, cout, null);
    }

    /**
     * Ajoute un arc entre deux nœuds avec numéro de ligne
     * @param depart le nœud de départ
     * @param destination le nœud de destination
     * @param cout le coût de l'arc
     * @param ligne le numéro de ligne (peut être null)
     */
    public void ajouterArc(String depart, String destination, double cout, String ligne) {
        // Ajouter les nœuds s'ils n'existent pas
        ajouterNoeud(depart);
        ajouterNoeud(destination);

        // Créer l'arc avec ou sans ligne
        Arc arc = (ligne != null) ? new Arc(destination, cout, ligne) : new Arc(destination, cout);

        // Ajouter l'arc à la liste d'adjacence du nœud de départ
        int indiceDepart = getIndice(depart);
        this.adjacence.get(indiceDepart).ajouterArc(arc);
    }

    /**
     * Méthode toString pour afficher le graphe
     * @return représentation textuelle du graphe
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.noeuds.size(); i++) {
            String noeud = this.noeuds.get(i);
            sb.append(noeud).append(" -> ");

            List<Arc> arcs = this.adjacence.get(i).getArcs();
            for (int j = 0; j < arcs.size(); j++) {
                Arc arc = arcs.get(j);
                sb.append(arc.toString());
                if (j < arcs.size() - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Méthode privée pour charger un graphe depuis un fichier simple
     * (format de la question 16: noeud1 noeud2 cout par ligne)
     * @param nomFichier le nom du fichier
     */
    private void chargerDepuisFichierSimple(String nomFichier) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.FileReader(nomFichier));
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty()) {
                    String[] parties = ligne.split("\t"); // Tabulation comme séparateur
                    if (parties.length >= 3) {
                        String depart = parties[0].trim();
                        String destination = parties[1].trim();
                        double cout = Double.parseDouble(parties[2].trim());
                        ajouterArc(depart, destination, cout);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier: " + e.getMessage());
        }
    }
}