import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Dijkstra
 */
public class DijkstraTest {

    private GrapheListe graphe;
    private Dijkstra dijkstra;

    @BeforeEach
    public void setUp() {
        // Création du graphe de test (même que Figure 1 du sujet)
        graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);

        dijkstra = new Dijkstra();
    }

    @Test
    public void testDijkstraDepuisA() {
        Valeurs resultats = dijkstra.resoudre(graphe, "A");

        // Vérification des distances calculées depuis A
        assertEquals(0, resultats.getValeur("A"), 0.001, "Distance A->A devrait être 0");
        assertEquals(12, resultats.getValeur("B"), 0.001, "Distance A->B devrait être 12");
        assertEquals(76, resultats.getValeur("C"), 0.001, "Distance A->C devrait être 76");
        assertEquals(66, resultats.getValeur("D"), 0.001, "Distance A->D devrait être 66");
        assertEquals(23, resultats.getValeur("E"), 0.001, "Distance A->E devrait être 23");

        // Vérification des parents pour reconstruire les chemins
        assertNull(resultats.getParent("A"), "A ne devrait pas avoir de parent");
        assertEquals("A", resultats.getParent("B"), "Parent de B devrait être A");
        assertEquals("D", resultats.getParent("C"), "Parent de C devrait être D");
        assertEquals("E", resultats.getParent("D"), "Parent de D devrait être E");
        assertEquals("B", resultats.getParent("E"), "Parent de E devrait être B");
    }

    @Test
    public void testDijkstraDepuisB() {
        Valeurs resultats = dijkstra.resoudre(graphe, "B");

        // Vérification des distances calculées depuis B
        assertEquals(0, resultats.getValeur("B"), 0.001, "Distance B->B devrait être 0");
        assertEquals(11, resultats.getValeur("E"), 0.001, "Distance B->E devrait être 11");
        assertEquals(54, resultats.getValeur("D"), 0.001, "Distance B->D devrait être 54");
        assertEquals(64, resultats.getValeur("C"), 0.001, "Distance B->C devrait être 64");

        // Vérification des parents
        assertNull(resultats.getParent("B"), "B ne devrait pas avoir de parent");
        assertEquals("B", resultats.getParent("E"), "Parent de E devrait être B");
        assertEquals("E", resultats.getParent("D"), "Parent de D devrait être E");
        assertEquals("D", resultats.getParent("C"), "Parent de C devrait être D");
    }

    @Test
    public void testGrapheSimple() {
        // Test avec un graphe plus simple : A->B(5), B->C(3)
        GrapheListe grapheSimple = new GrapheListe();
        grapheSimple.ajouterArc("A", "B", 5);
        grapheSimple.ajouterArc("B", "C", 3);

        Valeurs resultats = dijkstra.resoudre(grapheSimple, "A");

        assertEquals(0, resultats.getValeur("A"), 0.001);
        assertEquals(5, resultats.getValeur("B"), 0.001);
        assertEquals(8, resultats.getValeur("C"), 0.001);

        assertEquals("A", resultats.getParent("B"));
        assertEquals("B", resultats.getParent("C"));
    }

    @Test
    public void testNoeudIsole() {
        // Test avec un nœud isolé (sans connexion)
        GrapheListe grapheAvecIsole = new GrapheListe();
        grapheAvecIsole.ajouterArc("A", "B", 10);
        grapheAvecIsole.ajouterArc("C", "D", 5); // C et D isolés de A-B

        Valeurs resultats = dijkstra.resoudre(grapheAvecIsole, "A");

        assertEquals(0, resultats.getValeur("A"), 0.001);
        assertEquals(10, resultats.getValeur("B"), 0.001);
        assertEquals(Double.MAX_VALUE, resultats.getValeur("C"), 0.001, "C devrait rester inaccessible");
        assertEquals(Double.MAX_VALUE, resultats.getValeur("D"), 0.001, "D devrait rester inaccessible");
    }

    @Test
    public void testComparaisonAvecBellmanFord() {
        // Comparaison des résultats avec l'algorithme de Bellman-Ford
        BellmanFord bellmanFord = new BellmanFord();

        Valeurs resultatsDijkstra = dijkstra.resoudre(graphe, "A");
        Valeurs resultatsBellmanFord = bellmanFord.resoudre(graphe, "A");

        // Les deux algorithmes doivent donner les mêmes résultats
        for (String noeud : graphe.listeNoeuds()) {
            assertEquals(resultatsBellmanFord.getValeur(noeud),
                    resultatsDijkstra.getValeur(noeud),
                    0.001,
                    "Les distances calculées par Dijkstra et Bellman-Ford doivent être identiques pour le nœud " + noeud);

            assertEquals(resultatsBellmanFord.getParent(noeud),
                    resultatsDijkstra.getParent(noeud),
                    "Les parents calculés par Dijkstra et Bellman-Ford doivent être identiques pour le nœud " + noeud);
        }
    }
}