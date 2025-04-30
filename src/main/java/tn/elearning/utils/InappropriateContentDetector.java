package tn.elearning.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InappropriateContentDetector {
    // Liste de mots inappropriés en français
    private static final List<String> INAPPROPRIATE_WORDS = Arrays.asList(
        // Insultes et gros mots
        "connard", "salope", "pute", "merde", "putain", "enculé", "nique", "fuck", "shit",
        "bitch", "asshole", "bastard", "dick", "pussy", "fils de pute", "ta mère",
        
        // Violence
        "tuer", "mort", "assassin", "meurtre", "viol", "agression", "battre", "frapper",
        
        // Drogues et substances illicites
        "drogue", "cocaïne", "héroïne", "cannabis", "joint", "shit", "crack", "ecstasy",
        
        // Contenu sexuel
        "sexe", "porn", "porno", "nude", "nudité", "sexy", "baise", "baiser", "cul",
        
        // Discours de haine
        "nazi", "raciste", "hitler", "juif", "arabe", "noir", "blanc", "islam", "terroriste",
        
        // Autres contenus inappropriés
        "suicide", "diable", "enfer", "satan", "démon"
    );
    
    // Liste de patterns pour détecter les tentatives de contournement
    private static final List<Pattern> EVASION_PATTERNS = Arrays.asList(
        // Mots avec chiffres (ex: m3rd3)
        Pattern.compile("\\b[a-z]+[0-9]+[a-z]*\\b"),
        
        // Mots avec caractères spéciaux (ex: m*erde)
        Pattern.compile("\\b[a-z]+[^a-z\\s]+[a-z]+\\b"),
        
        // Répétition de caractères (ex: mmmmerde)
        Pattern.compile("\\b([a-z])\\1{2,}[a-z]*\\b"),
        
        // Combinaison de lettres et chiffres (ex: m3rd3)
        Pattern.compile("\\b[a-z]+[0-9]+[a-z]+[0-9]+\\b"),
        
        // Espaces entre les lettres (ex: m e r d e)
        Pattern.compile("\\b[a-z](\\s[a-z]){3,}\\b"),
        
        // Points entre les lettres (ex: m.e.r.d.e)
        Pattern.compile("\\b[a-z](\\.[a-z]){3,}\\b")
    );

    public static boolean containsInappropriateContent(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        String normalizedText = text.toLowerCase()
            .replaceAll("[éèêë]", "e")
            .replaceAll("[àâä]", "a")
            .replaceAll("[îï]", "i")
            .replaceAll("[ôö]", "o")
            .replaceAll("[ùûü]", "u")
            .replaceAll("[ç]", "c")
            .trim();

        // Vérifier les mots inappropriés directs
        for (String word : INAPPROPRIATE_WORDS) {
            if (normalizedText.contains(word.toLowerCase())) {
                return true;
            }
        }

        // Vérifier les tentatives de contournement
        for (Pattern pattern : EVASION_PATTERNS) {
            if (pattern.matcher(normalizedText).find()) {
                return true;
            }
        }

        return false;
    }

    public static String getInappropriateContentMessage() {
        return "Votre commentaire contient des mots inappropriés. Veuillez utiliser un langage adapté pour les enfants.";
    }
} 