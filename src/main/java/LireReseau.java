import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe LireReseau pour charger un graphe représentant un réseau de transport
 * à partir d'un fichier texte contenant les stations et connexions
 */
public class LireReseau {

    /**
     * Lit un fichier de réseau et construit le graphe correspondant
     * @param fichier le nom du fichier contenant le plan du réseau
     * @return le graphe construit à partir du fichier
     * @throws IOException si erreur de lecture du fichier
     */
    public static Graphe lire(String fichier) throws IOException {
        GrapheListe graphe = new GrapheListe();
        Map<String, String> stations = new HashMap<>(); // id -> nom

        BufferedReader reader = new BufferedReader(new FileReader(fichier));
        String ligne;
        boolean lectureStations = false;
        boolean lectureConnexions = false;

        while ((ligne = reader.readLine()) != null) {
            ligne = ligne.trim();

            // Ignorer les lignes vides et les commentaires
            if (ligne.isEmpty() || ligne.startsWith("%")) {
                // Vérifier les sections
                if (ligne.contains("stations") || ligne.contains("Stations")) {
                    lectureStations = true;
                    lectureConnexions = false;
                    continue;
                } else if (ligne.contains("connexions") || ligne.contains("Connexions")) {
                    lectureStations = false;
                    lectureConnexions = true;
                    continue;
                } else {
                    continue; // Ignorer les autres commentaires
                }
            }

            if (lectureStations) {
                // Format: id_station:nom_station:x:y:num_ligne1,num_ligne2,...
                String[] parties = ligne.split(":");
                if (parties.length >= 2) {
                    String idStation = parties[0].trim();
                    String nomStation = parties[1].trim();
                    stations.put(idStation, nomStation);

                    // Ajouter la station au graphe (utilise l'ID comme nom de nœud)
                    if (!graphe.listeNoeuds().contains(idStation)) {
                        // On va utiliser une méthode pour ajouter des nœuds vides
                        graphe.ajouterNoeud(idStation);
                    }
                }
            } else if (lectureConnexions) {
                // Format: id_station_depart:id_station_arrivee:temps:num_ligne
                String[] parties = ligne.split(":");
                if (parties.length >= 4) {
                    String depart = parties[0].trim();
                    String arrivee = parties[1].trim();
                    double temps = Double.parseDouble(parties[2].trim());
                    String numLigne = parties[3].trim();

                    // Ajouter les deux arcs (connexion bidirectionnelle)
                    graphe.ajouterArc(depart, arrivee, temps, numLigne);
                    graphe.ajouterArc(arrivee, depart, temps, numLigne);
                }
            }
        }

        reader.close();
        return graphe;
    }

    /**
     * Méthode utilitaire pour afficher les informations du réseau chargé
     * @param graphe le graphe à analyser
     * @param stations la map des stations (id -> nom)
     */
    public static void afficherInfosReseau(Graphe graphe, Map<String, String> stations) {
        System.out.println("=== Informations du réseau ===");
        System.out.println("Nombre de stations: " + graphe.listeNoeuds().size());

        int nbConnexions = 0;
        for (String station : graphe.listeNoeuds()) {
            nbConnexions += graphe.suivants(station).size();
        }
        System.out.println("Nombre total de connexions: " + nbConnexions);

        // Afficher quelques stations d'exemple
        System.out.println("\nQuelques stations:");
        int count = 0;
        for (String id : graphe.listeNoeuds()) {
            if (count < 5 && stations.containsKey(id)) {
                System.out.println("  " + id + ": " + stations.get(id));
                count++;
            }
        }
    }

    /**
     * Méthode pour tester le chargement d'un réseau
     */
    public static void main(String[] args) {
        try {
            // Remplacez "metro.txt" par le nom de votre fichier
            String fichier = "metro.txt";
            Graphe reseau = lire(fichier);

            System.out.println("Réseau chargé avec succès!");
            System.out.println("Graphe: ");
            System.out.println(reseau.toString());

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}