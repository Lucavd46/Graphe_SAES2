import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un ensemble d'arcs partant d'un nœud
 */
public class Arcs {
    private List<Arc> arcs;

    /**
     * Constructeur qui initialise une liste d'arcs vide
     */
    public Arcs() {
        this.arcs = new ArrayList<>();
    }

    /**
     * Ajoute un arc à la liste
     * @param a l'arc à ajouter
     */
    public void ajouterArc(Arc a) {
        this.arcs.add(a);
    }

    /**
     * Retourne la liste des arcs
     * @return la liste des arcs
     */
    public List<Arc> getArcs() {
        return this.arcs;
    }

    /**
     * Méthode d'affichage des arcs
     * @return Une chaîne décrivant tous les arcs de la liste
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Arc arc : arcs) {
            sb.append(arc.toString()).append(" ");
        }
        return sb.toString();
    }
}