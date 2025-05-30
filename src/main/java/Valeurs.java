import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

/**
 * Classe fournie, permet de stocker des valeurs associées au noeud et
 * des parents
 * - un noeud est represente par un String (son nom)
 * - on accede avec des get (getValeur et getParent)
 * - on modifie avec des set (setValeur et setParent)
 */
public class Valeurs {
    /**
     * attributs pour stocker les informations
     * (type Table = Dictionnaire)
     * dans le programme de 2 annee.
     */
    Map<String, Double> valeur;
    Map<String, String> parent;

    /**
     * constructeur vide (initialise la possibilité de stocker
     * des valeurs)
     */
    public Valeurs() {
        this.valeur = new TreeMap<>();
        this.parent = new TreeMap<>();
    }

    /**
     * permet d'associer une valeur a un nom de noeud (ici L(X))
     *
     * @param nom le nom du noeud
     * @param valeur la valeur associée
     */
    public void setValeur(String nom, double valeur) {
        // modifie valeur
        this.valeur.put(nom, valeur);
    }

    /**
     * permet d'associer un parent a un nom de noeud (ici parent(X))
     *
     * @param nom nom du noeud
     * @param parent nom du noeud parent associe
     */
    public void setParent(String nom, String parent) {
        this.parent.put(nom, parent);
    }

    /**
     * accede au parent stocke associe au noeud nom passe en
     * parametre
     *
     * @param nom nom du noeud
     * @return le nom du noeud parent
     */
    public String getParent(String nom) {
        return this.parent.get(nom);
    }

    /**
     * accede a la valeur associee au noeud nom passe en
     * parametre
     *
     * @param nom nom du noeud
     * @return la valeur stockee
     */
    public double getValeur(String nom) {
        return this.valeur.get(nom);
    }

    /**
     * retourne une chaine qui affiche le contenu
     * - par noeud stocke
     * - a chaque noeud, affiche la valeur puis le noeud
     * parent
     *
     * @return descriptif du noeud
     */
    public String toString() {
        String res = "";
        // pour chaque noeud
        for (String s : this.valeur.keySet()) {
            // ajoute la valeur et le noeud parent
            Double valeurNoeud = valeur.get(s);
            String noeudParent = parent.get(s);
            res += s + " -> V:" + valeurNoeud + " p:" + noeudParent + "\n";
        }
        return res;
    }

    /**
     * Calcule et retourne le chemin depuis le nœud de départ jusqu'à la destination
     * @param destination le nœud de destination
     * @return une liste de nœuds représentant le chemin
     */
    public List<String> calculerChemin(String destination) {
        List<String> chemin = new ArrayList<>();
        String noeud = destination;

        // Si la destination n'a pas de valeur ou pas de parent, il n'y a pas de chemin
        if (!this.valeur.containsKey(noeud) || this.parent.get(noeud) == null) {
            return chemin; // Retourne une liste vide
        }

        // Ajouter la destination au chemin
        chemin.add(0, noeud);

        // Remonter le chemin en suivant les parents jusqu'à ce qu'on atteigne un nœud sans parent
        while (this.parent.get(noeud) != null) {
            noeud = this.parent.get(noeud);
            chemin.add(0, noeud); // Ajouter au début de la liste

            // Éviter les boucles infinies en cas de problème dans le graphe
            if (chemin.size() > this.valeur.size()) {
                break;
            }
        }

        return chemin;
    }
}