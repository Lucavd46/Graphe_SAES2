import java.util.List;

/**
 * Interface définissant le comportement d'un graphe
 */
public interface Graphe {

    /**
     * Retourne la liste des nœuds du graphe
     * @return la liste des nœuds sous forme de chaînes de caractères
     */
    public List<String> listeNoeuds();

    /**
     * Retourne la liste des arcs partant d'un nœud donné
     * @param n le nœud de départ
     * @return la liste des arcs partant de ce nœud
     */
    public List<Arc> suivants(String n);
}