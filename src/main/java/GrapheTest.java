import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests unitaires pour la classe GrapheListe
 */
public class GrapheTest {
    private GrapheListe graphe;

    @BeforeEach
    public void setUp() {
        // Création du graphe de la Figure 1 avant chaque test
        graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 12);
        graphe.ajouterArc("A", "D", 87);
        graphe.ajouterArc("B", "E", 11);
        graphe.ajouterArc("C", "A", 19);
        graphe.ajouterArc("D", "B", 23);
        graphe.ajouterArc("D", "C", 10);
        graphe.ajouterArc("E", "D", 43);
    }

    @Test
    public void testListeNoeuds() {
        List<String> noeuds = graphe.listeNoeuds();
        assertEquals(5, noeuds.size(), "Le graphe devrait contenir 5 nœuds");
        assertTrue(noeuds.contains("A"), "Le graphe devrait contenir le nœud A");
        assertTrue(noeuds.contains("B"), "Le graphe devrait contenir le nœud B");
        assertTrue(noeuds.contains("C"), "Le graphe devrait contenir le nœud C");
        assertTrue(noeuds.contains("D"), "Le graphe devrait contenir le nœud D");
        assertTrue(noeuds.contains("E"), "Le graphe devrait contenir le nœud E");
    }

    @Test
    public void testSuivants() {
        // Test des arcs partant de A
        List<Arc> arcsA = graphe.suivants("A");
        assertEquals(2, arcsA.size(), "Le nœud A devrait avoir 2 arcs sortants");

        // Vérification que B et D sont bien les destinations des arcs partant de A
        boolean hasB = false;
        boolean hasD = false;

        for (Arc arc : arcsA) {
            if (arc.getDest().equals("B")) {
                hasB = true;
                assertEquals(12, arc.getCout(), "L'arc A->B devrait avoir un coût de 12");
            } else if (arc.getDest().equals("D")) {
                hasD = true;
                assertEquals(87, arc.getCout(), "L'arc A->D devrait avoir un coût de 87");
            }
        }

        assertTrue(hasB, "Il devrait y avoir un arc de A vers B");
        assertTrue(hasD, "Il devrait y avoir un arc de A vers D");

        // Test des arcs partant de D
        List<Arc> arcsD = graphe.suivants("D");
        assertEquals(2, arcsD.size(), "Le nœud D devrait avoir 2 arcs sortants");

        // Vérification que B et C sont bien les destinations des arcs partant de D
        boolean hasDBToB = false;
        boolean hasDToC = false;

        for (Arc arc : arcsD) {
            if (arc.getDest().equals("B")) {
                hasDBToB = true;
                assertEquals(23, arc.getCout(), "L'arc D->B devrait avoir un coût de 23");
            } else if (arc.getDest().equals("C")) {
                hasDToC = true;
                assertEquals(10, arc.getCout(), "L'arc D->C devrait avoir un coût de 10");
            }
        }

        assertTrue(hasDBToB, "Il devrait y avoir un arc de D vers B");
        assertTrue(hasDToC, "Il devrait y avoir un arc de D vers C");
    }

    @Test
    public void testAjouterArc() {
        // Ajout d'un nouvel arc
        graphe.ajouterArc("E", "C", 15);

        // Vérification que l'arc a bien été ajouté
        List<Arc> arcsE = graphe.suivants("E");
        assertEquals(2, arcsE.size(), "Le nœud E devrait maintenant avoir 2 arcs sortants");

        boolean hasEToC = false;
        for (Arc arc : arcsE) {
            if (arc.getDest().equals("C")) {
                hasEToC = true;
                assertEquals(15, arc.getCout(), "L'arc E->C devrait avoir un coût de 15");
            }
        }

        assertTrue(hasEToC, "Il devrait y avoir un arc de E vers C");
    }

    @Test
    public void testNoeudInexistant() {
        // Test des arcs partant d'un nœud inexistant
        List<Arc> arcsZ = graphe.suivants("Z");
        assertTrue(arcsZ.isEmpty(), "Un nœud inexistant ne devrait pas avoir d'arcs sortants");
    }
}