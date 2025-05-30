/**
 * Classe représentant un arc dans un graphe
 * Un arc est défini par son nœud de destination et son coût
 */
public class Arc {
    private String dest;
    private double cout;
    private String ligne; // Attribut ajouté pour la partie 6 (application métro)

    /**
     * Constructeur pour un arc sans ligne (pour les parties 1-5)
     * @param dest le nœud de destination de l'arc
     * @param cout le coût (ou poids) de l'arc
     */
    public Arc(String dest, double cout) {
        this.dest = dest;
        this.cout = cout;
        this.ligne = null;
    }

    /**
     * Constructeur pour un arc avec une ligne (pour la partie 6 - métro)
     * @param dest le nœud de destination de l'arc
     * @param cout le coût (ou poids) de l'arc
     * @param ligne la ligne de métro associée à l'arc
     */
    public Arc(String dest, double cout, String ligne) {
        this.dest = dest;
        this.cout = cout;
        this.ligne = ligne;
    }

    /**
     * Retourne le nœud de destination de l'arc
     * @return le nœud de destination
     */
    public String getDest() {
        return dest;
    }

    /**
     * Retourne le coût de l'arc
     * @return le coût de l'arc
     */
    public double getCout() {
        return cout;
    }

    /**
     * Retourne la ligne de métro associée à l'arc
     * @return la ligne de métro (peut être null)
     */
    public String getLigne() {
        return ligne;
    }

    /**
     * Méthode d'affichage d'un arc
     * @return Une chaîne décrivant l'arc
     */
    @Override
    public String toString() {
        if (ligne == null) {
            return dest + "(" + cout + ")";
        } else {
            return dest + "(" + cout + "," + ligne + ")";
        }
    }
}