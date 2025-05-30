import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe BellmanFord
 */
public class BellmanFordTest {

    private GrapheListe graphe;
    private BellmanFord bellmanFord;

    @BeforeEach
    public void setUp() {
        // Création du graphe de test (Figure 1)
        graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);

        bellmanFord = new BellmanFord();
    }

    @Test
    public void testDistancesDepuisA() {
        Valeurs resultats = bellmanFord.resoudre(graphe, "A");

        // Vérification des distances calculées
        assertEquals(0.0, resultats.getValeur("A"), 0.001, "Distance A->A devrait être 0");
        assertEquals(12.0, resultats.getValeur("B"), 0.001, "Distance A->B devrait être 12");
        assertEquals(76.0, resultats.getValeur("C"), 0.001, "Distance A->C devrait être 76");
        assertEquals(66.0, resultats.getValeur("D"), 0.001, "Distance A->D devrait être 66");
        assertEquals(23.0, resultats.getValeur("E"), 0.001, "Distance A->E devrait être 23");
    }

    @Test
    public void testParentsDepuisA() {
        Valeurs resultats = bellmanFord.resoudre(graphe, "A");

        // Vérification des parents calculés
        assertNull(resultats.getParent("A"), "Parent de A devrait être null");
        assertEquals("A", resultats.getParent("B"), "Parent de B devrait être A");
        assertEquals("D", resultats.getParent("C"), "Parent de C devrait être D");
        assertEquals("E", resultats.getParent("D"), "Parent de D devrait être E");
        assertEquals("B", resultats.getParent("E"), "Parent de E devrait être B");
    }

    @Test
    public void testDistancesDepuisB() {
        Valeurs resultats = bellmanFord.resoudre(graphe, "B");

        // Vérification des distances depuis B
        assertEquals(0.0, resultats.getValeur("B"), 0.001, "Distance B->B devrait être 0");
        assertEquals(11.0, resultats.getValeur("E"), 0.001, "Distance B->E devrait être 11");
        assertEquals(54.0, resultats.getValeur("D"), 0.001, "Distance B->D devrait être 54");
        assertEquals(64.0, resultats.getValeur("C"), 0.001, "Distance B->C devrait être 64");
    }

    @Test
    public void testGrapheSimple() {
        // Test avec un graphe plus simple
        GrapheListe grapheSimple = new GrapheListe();
        grapheSimple.ajouterArc("X", "Y", 5);
        grapheSimple.ajouterArc("Y", "Z", 3);

        Valeurs resultats = bellmanFord.resoudre(grapheSimple, "X");

        assertEquals(0.0, resultats.getValeur("X"), 0.001);
        assertEquals(5.0, resultats.getValeur("Y"), 0.001);
        assertEquals(8.0, resultats.getValeur("Z"), 0.001);

        assertEquals("X", resultats.getParent("Y"));
        assertEquals("Y", resultats.getParent("Z"));
    }

    @Test
    public void testNoeudIsolé() {
        // Test avec un nœud isolé
        GrapheListe grapheAvecIsole = new GrapheListe();
        grapheAvecIsole.ajouterArc("A", "B", 10);
        grapheAvecIsole.ajouterArc("C", "D", 5); // C et D isolés de A-B

        Valeurs resultats = bellmanFord.resoudre(grapheAvecIsole, "A");

        assertEquals(0.0, resultats.getValeur("A"), 0.001);
        assertEquals(10.0, resultats.getValeur("B"), 0.001);
        assertEquals(Double.MAX_VALUE, resultats.getValeur("C"), 0.001, "C devrait être inaccessible");
        assertEquals(Double.MAX_VALUE, resultats.getValeur("D"), 0.001, "D devrait être inaccessible");
    }
}