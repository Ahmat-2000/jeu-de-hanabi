package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.observer.AbstractListenableModel;

/**
 * La classe PlayerHint représente les indices donnés aux joueurs dans le jeu.
 */
public class PlayerHint extends AbstractListenableModel {
    /** 
     * Tous les indices du jeu 
     */
    private final Map<Player, List<String>> hints;
    /** 
     * Une instance du jeu 
     */
    private Game game;

    /**
     * Constructeur pour créer un PlayerHint avec un jeu spécifié.
     *
     * @param game Le jeu.
     */
    public PlayerHint(Game game) {
        super();
        this.game = game;
        this.hints = new HashMap<>();
        this.prepareBord();
    }

    /**
     * Prépare les piles de bord pour un nouveau jeu.
     */
    private void prepareBord() {
        for (Player p : game.getPlayers()) {
            this.hints.put(p, new ArrayList<>());
        }
    }

    /**
     * Ajoute un indice pour un joueur.
     *
     * @param player Le joueur recevant l'indice.
     * @param hint L'indice donné.
     */
    public void addHint(Player player, String hint) {
        this.hints.compute(player, (checkedPlayer, associatedHints) -> {
            if (associatedHints == null) {
                associatedHints = new ArrayList<>();
            }
            associatedHints.add(hint);
            return associatedHints;
        });
        super.fireChange();
    }

    /**
     * Obtient les indices pour un joueur spécifié.
     *
     * @param p Le joueur.
     * @return La liste des indices pour le joueur.
     */
    public List<String> getHints(Player p) {
        return hints.get(p);
    }

    /**
     * Obtient les indices pour tous les joueurs.
     *
     * @return Une map des indices par joueur.
     */
    public Map<Player, List<String>> getHints() {
        return hints;
    }
}
