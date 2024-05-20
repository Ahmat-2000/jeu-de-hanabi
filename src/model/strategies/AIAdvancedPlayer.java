package model.strategies;

import model.Card;
import model.CardColor;
import model.Game;
import model.Hand;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

/**
 * AIAdvancedPlayer représente un joueur IA avancé avec des capacités de jeu améliorées.
 */
public class AIAdvancedPlayer extends AIMalinPlayer 
{
    /**
     * Une map pour stocker les ensembles de positions de cartes à jouer, organisées par couleur.
     */
    private Map<CardColor, Set<Integer>> cardsToPlayByColor;

    /**
     * Une map pour stocker les ensembles de positions de cartes à jouer, organisées par valeur.
     */
    private Map<Integer, Set<Integer>> cardsToPlayByValue;

    /**
     * Un ensemble pour stocker les positions des cartes partiellement connues.
     */
    Set<Integer> partiallyKnownCards = new HashSet<>();

    /**
     * Un ensemble pour stocker les positions des cartes complètement connues.
     */
    Set<Integer> completelyKnownCards = new HashSet<>();

    /**
     * CategorieCarte représente les catégories possibles des cartes.
     */
    public enum CategorieCarte
    {
        JOUABLE,
        INUTILE,
        PRECIEUSE
    }  

    /**
     * Constructeur pour AIAdvancedPlayer.
     *
     * @param name Nom du joueur.
     * @param hand Main du joueur.
     */
    public AIAdvancedPlayer(String name, Hand hand) 
    {
        super(name, hand);
        this.cardsToPlayByColor = new HashMap<>(); 
        this.cardsToPlayByValue = new HashMap<>(); 

        // Initialise les ensembles pour les cartes à jouer par valeur
        for (int i = 0; i < 5; i++) 
        {
            this.cardsToPlayByValue.put(i + 1, new HashSet<>());
        }
    
        // Initialise les ensembles pour les cartes à jouer par couleur
        for (CardColor color : CardColor.values()) 
        {
            this.cardsToPlayByColor.put(color, new HashSet<>());
        }
    }

    /**
     * Choisit l'action à effectuer par l'IA pendant son tour.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void chooseAction(Game game) 
    {
        this.displayRemainingCardsCount(game);
        this.displayCardPossibilities(game, getHand());
        
        System.out.println("IA " + this.getName() + " analyse les indices reçus");
        if (this.shouldPlayCard(game)) 
        {
            System.out.println("IA " + this.getName() + " joue une carte");
            this.playCard(game);
        }
        else if (this.shouldDiscardCard(game))
        {
            System.out.println("IA " + this.getName() + " défausse une carte");
            this.discardCard(game);
        } 
        else 
        {
            System.out.println("IA " + this.getName() + " donne un indice");
            this.giveHint(game);
        }
    }

    /**
     * Obtient le nombre restant d'une carte spécifique dans le jeu.
     *
     * @param card La carte dont on veut connaître le nombre restant.
     * @param game L'état actuel du jeu.
     * @return Le nombre de cartes restantes.
     */
    private int getRemainingCount(Card card, Game game) {
        int totalCount = Card.getTotalCount(card.getValue(), card.getColor()); 
        int discardedCount = game.getDiscardedCards().stream().filter(c -> c.equals(card)).toArray().length;
        int playedCount = game.getPlayedCards().stream().filter(c -> c.equals(card)).toArray().length;

        int otherPlayersCount = 0;
        for (Player player : game.getPlayers()) {
            if (player != this) {
                otherPlayersCount += player.getHand().getCards().stream().filter(c -> c.equals(card)).toArray().length;
            }
        }
        
        return totalCount - (discardedCount + playedCount + otherPlayersCount);
    }

    /**
     * Affiche le nombre de cartes restantes pour chaque type de carte.
     *
     * @param game L'état actuel du jeu.
     */
    public void displayRemainingCardsCount(Game game) {
        System.err.println("Dictionnaire des cartes restantes");
        System.out.println("+---------------------------+");
        System.out.println("| Type de Carte| Restant    |");
        System.out.println("+---------------------------+");
        
        // Trie les types de cartes et affiche le nombre restant pour chaque type
        for (CardColor color : CardColor.values()) {
            for (int value = Card.getLowestValue(); value <= Card.getHighestValue(); value++) {
                Card cardType = new Card(value, color);
                int remainingCount = this.getRemainingCount(cardType, game);
                System.out.printf("| %-12s | %-10d |\n", cardType, remainingCount);
            }
        }
        System.out.println("+---------------------------+");
    }

    /**
     * Met à jour les cartes connues (complètement et partiellement) dans la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    private void updateKnownCards(Game game) {
        Hand hand = this.getHand();
        completelyKnownCards.clear();
        partiallyKnownCards.clear();

        for (int i = 0; i < hand.getSize(); i++) {
            final int finalI = i; 
            final Card currentCard = hand.getCard(finalI); 

            boolean isColorKnown = cardsToPlayByColor.entrySet().stream()
                .anyMatch(entry -> entry.getValue().contains(finalI) && currentCard.getColor() == entry.getKey());
            boolean isValueKnown = cardsToPlayByValue.entrySet().stream()
                .anyMatch(entry -> entry.getValue().contains(finalI) && currentCard.getValue() == entry.getKey());

            if (isColorKnown && isValueKnown) {
                completelyKnownCards.add(finalI);
            } else if (isColorKnown || isValueKnown) {
                partiallyKnownCards.add(finalI);
            }
        }

        printKnownCardsStatus();
    }

    /**
     * Affiche le statut des cartes connues.
     */
    private void printKnownCardsStatus() {
        System.out.print("Partiellement connues:[ ");
        partiallyKnownCards.forEach(p -> System.out.print((p + 1) + " "));
        System.out.print("]\nComplètement connues:[ ");
        completelyKnownCards.forEach(p -> System.out.print((p + 1) + " "));
        System.out.println("]");
        System.err.println("--------------------------------------------------------Annalyse des indices--------------------------------------------------------------");
    }

    /**
     * Met à jour les possibilités de cartes et les statistiques basées sur les indices reçus.
     *
     * @param game L'état actuel du jeu.
     * @return Une carte avec les possibilités de cartes mises à jour.
     */
    private Map<Card, Set<Card>> updateCardPossibilitiesAndStats(Game game) 
    { 
        Map<Card, Set<Card>> possibilitiesMap = new HashMap<>(); 
        // Traite chaque indice reçu
        List<String> hints = game.getHints().getOrDefault(this, List.of());
        for (String hint : hints) 
        {   
            System.out.println("IA " + getName() + " analyse l'indice " + hint);
            if (hint.contains("couleur")) 
            {
                CardColor colorHint = CardColor.valueOf(hint.split("couleur ")[1].split(" en")[0]);
                String[] positions = hint.split(": ")[1].split(" ");
                for (String position : positions) {
                    int cardIndex = Integer.parseInt(position) - 1;
                    this.cardsToPlayByColor.computeIfAbsent(colorHint, k -> new HashSet<>()).add(cardIndex);
                }
                
            }
            else if (hint.contains("valeur")) 
            {
                int valueHint = Integer.parseInt(hint.split("valeur ")[1].split(" en")[0]);
                String[] positions = hint.split(": ")[1].split(" ");
                for (String position : positions) 
                {
                    int cardIndex = Integer.parseInt(position) - 1;
                    this.cardsToPlayByValue.computeIfAbsent(valueHint, k -> new HashSet<>()).add(cardIndex);
                }
            }
        }
        
        Hand hand = this.getHand();

        for (int i = 0; i < hand.getSize(); i++) {
            final int index = i;
            Card card = hand.getCard(i);

            Set<Card> possibilitiesForCard;
            possibilitiesForCard = generatePossibleCards(game, card);

            // Filtre les possibilités en fonction des indices de couleur et de valeur reçus pour cette position
            if (this.cardsToPlayByColor.containsKey(card.getColor())) {
                this.cardsToPlayByColor.forEach((value, positions) -> {
                    if (positions.contains(index)) {
                        possibilitiesForCard.removeIf(p -> p.getColor() != card.getColor());
                    }
                });
            }
            this.cardsToPlayByValue.forEach((value, positions) -> {
                if (positions.contains(index)) {
                    possibilitiesForCard.removeIf(p -> p.getValue() != value);
                }
            });

            card.setPossibleCards(possibilitiesForCard); // Met à jour les possibilités pour cette carte spécifique
            possibilitiesMap.put(card, possibilitiesForCard);
        }

        game.getHints().remove(this); // Nettoyer les indices après traitement
        return possibilitiesMap;
    }

    /**
     * Génère les cartes possibles pour une carte dans la main du joueur.
     *
     * @param game L'état actuel du jeu.
     * @param cardInHand La carte dans la main du joueur.
     * @return Un ensemble de cartes possibles.
     */
    private Set<Card> generatePossibleCards(Game game, Card cardInHand) 
    {
        Set<Card> PossibleCards = new HashSet<>();
    
        // Compte les cartes par valeur et couleur dans la main des coéquipiers 
        Map<Card, Integer> cardVisibilityCount = new HashMap<>();
        for (Player player : game.getPlayers()) 
        {
            if (player != this) 
            { 
                player.getHand().getCards().forEach(card -> cardVisibilityCount.merge(card, 1, Integer::sum));
            }
        }
        // Comptage de cartes jouées et défaussées aussi
        game.getDiscardedCards().forEach(card -> cardVisibilityCount.merge(card, 1, Integer::sum));
        game.getPlayedCards().forEach(card -> cardVisibilityCount.merge(card, 1, Integer::sum));

        // Parcourt chaque couleur et valeur possible pour une carte
        for (CardColor color : CardColor.values()) 
        {
            for (int value = Card.getLowestValue(); value <= Card.getHighestValue(); value++) 
            {
                Card potentialCard = new Card(value, color);
                int visibleCount = cardVisibilityCount.getOrDefault(potentialCard, 0);

                // Règles pour exclure des cartes
                boolean excludeCard = false;
                if (value == 5 && visibleCount > 0) 
                { 
                    excludeCard = true;
                } 
                else if (value == 1 && visibleCount >= 3) 
                { 
                    excludeCard = true;
                }
                else if (value >= 2 && value <= 4 && visibleCount >= 2) 
                { 
                    excludeCard = true;
                }
                if (!excludeCard && game.getCardCount(potentialCard) > 0) 
                {
                    PossibleCards.add(potentialCard);
                }
            }
        }  
        return PossibleCards;
    }

    /**
     * Affiche les possibilités de cartes pour les cartes dans la main du joueur.
     *
     * @param game L'état actuel du jeu.
     * @param hand La main du joueur.
     */
    private void displayCardPossibilities(Game game, Hand hand) 
    {
        Map<Card, Set<Card>> cardPossibilitiesMap = updateCardPossibilitiesAndStats(game);

        System.out.println("+------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| Index | Catégorisation        |F-Jouable |F-Inutile | Précieuse | Possibilités Précieuses                        |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------+");

        for (int i = 0; i < hand.getSize(); i++) {
            final int index = i;
            Card cardInHand = hand.getCard(index);
            Set<Card> possibilitiesForCard = cardPossibilitiesMap.get(cardInHand);

            boolean isDefinitelyPlayable = possibilitiesForCard.stream().allMatch(card -> isForcementJouable(card, game));
            boolean isDefinitelyUseless = possibilitiesForCard.stream().allMatch(card -> isForcementInutile(card, game));
            boolean isPossiblyPrecious = possibilitiesForCard.stream().anyMatch(card -> isPeutEtrePrecieuse(card, game));

            String categorization = (isDefinitelyPlayable ? "Forcément jouable" :
                                    isDefinitelyUseless ? "Forcément inutile" :
                                    isPossiblyPrecious ? "Peut-être précieuse" : "");

            String jouable = isDefinitelyPlayable ? "Oui" : "Non";
            String inutile = isDefinitelyUseless ? "Oui" : "Non";
            String precieuse = isPossiblyPrecious ? "Peut-être" : "Non";

            String preciousPossibilities = possibilitiesForCard.stream()
                .filter(card -> isPeutEtrePrecieuse(card, game))
                .map(Card::toString)
                .collect(Collectors.joining(", "));

            System.out.printf("| %5d | %-21s | %-8s | %-8s | %-9s | %-45s |\n",
                            index + 1, categorization, jouable, inutile, precieuse, preciousPossibilities);
        }

        System.out.println("+------------------------------------------------------------------------------------------------------------------+");

        for (int i = 0; i < hand.getSize(); i++) {
            final int index = i;
            Card cardInHand = hand.getCard(index);
            Set<Card> possibilitiesForCard = cardPossibilitiesMap.get(cardInHand);
            System.out.println("Possibilités pour la carte " + (index + 1) + ": " + possibilitiesForCard.size() + " => " + possibilitiesForCard);
        }
        updateKnownCards(game);
    }

    /**
     * Vérifie si une carte est forcément jouable.
     *
     * @param card La carte à vérifier.
     * @param game L'état actuel du jeu.
     * @return Vrai si la carte est jouable, faux sinon.
     */
    private boolean isForcementJouable(Card card, Game game) 
    {
        if (game.isPlayable(card)) 
        {
            return true;
        }

        return false;
    }

    /**
     * Vérifie si une carte est forcément inutile.
     *
     * @param card La carte à vérifier.
     * @param game L'état actuel du jeu.
     * @return Vrai si la carte est inutile, faux sinon.
     */
    private boolean isForcementInutile(Card card, Game game) 
    {
        int index = card.getColor().ordinal();
        List<Card> pile = game.getbord().getFireworks().get(index);

        // Vérifie si la carte est inférieure ou égale à la dernière carte 
        if (!pile.isEmpty() && card.getValue() <= pile.get(pile.size() - 1).getValue()) 
        {
            return true;
        }

        // Vérifie si la carte a déjà été jouée
        if (game.getPlayedCards().contains(card)) 
        {
            return true;
        }

        // Condition de secours
        if (pile.isEmpty() && card.getValue() > 2 && card.getValue() != 5) 
        {
            return true;
        }

        return false;
    }

    /**
     * Vérifie si une carte est potentiellement précieuse.
     *
     * @param card La carte à vérifier.
     * @param game L'état actuel du jeu.
     * @return Vrai si la carte est précieuse, faux sinon.
     */
    private boolean isPeutEtrePrecieuse(Card card, Game game) 
    {
        boolean isPotentiallyLastUndiscarded = !game.getDiscardedCards().contains(card);
        List<Card> pile = game.getbord().getFireworks().get(card.getColor().ordinal());

        if (pile.isEmpty() && card.getValue() == 1) { 
            return isPotentiallyLastUndiscarded;
        }
    
        // Si la pile a été commencée, vérifiez si la carte est nécessaire pour continuer la pile
        if (!pile.isEmpty()) {
            Card topCard = pile.get(pile.size() - 1);
            boolean isNecessaryForPile = card.getValue() == topCard.getValue() + 1;
            return isPotentiallyLastUndiscarded && isNecessaryForPile;
        }
        return false;
    }

    /**
     * Détermine si l'IA devrait jouer une carte.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si une carte doit être jouée, faux sinon.
     */
    @Override
    public boolean shouldPlayCard(Game game) 
    {
        // Vérifie les cartes complètement connues pour une carte jouable
        for (Integer index : completelyKnownCards) {
            if (game.isPlayable(this.getHand().getCard(index))) {
                return true;
            }
        }
        // Si aucune carte complètement connue n'est jouable, vérifie les cartes partiellement connues
        for (Integer index : partiallyKnownCards) {
            if (game.isPlayable(this.getHand().getCard(index))) {
                return true;
            }
        }
        // Si aucune des cartes connue n'est jouable, retourne false
        return false;
    }

    /**
     * Joue une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void playCard(Game game) 
    {
        Integer cardToPlayIndex = null;
        // Priorise les cartes complètement connues
        for (Integer index : completelyKnownCards) {
            if (game.isPlayable(this.getHand().getCard(index))) 
            {
                cardToPlayIndex = index;
                break;
            }
        }
        // Si aucune carte complètement connue jouable on vérifie les cartes partiellement connues
        if (cardToPlayIndex == null) 
        {
            for (Integer index : partiallyKnownCards) 
            {
                if (game.isPlayable(this.getHand().getCard(index)))
                {
                    cardToPlayIndex = index;
                    break;
                }
            }
        }
      
        // Jouer la carte identifiée
        if (cardToPlayIndex != null) 
        {
            System.out.println("IA " + getName() + " joue la carte à la position " + (cardToPlayIndex + 1));
            ArrayList<Card> h = new ArrayList<>(this.getHand().getCards());
            game.playCard(cardToPlayIndex);
            Card c = this.getHand().getCard(getHandSize() - 1);
            h.set(cardToPlayIndex, c);
            this.getHand().setHand(h);
            // Retirer l'indice des ensembles connus
            completelyKnownCards.remove(cardToPlayIndex);
            partiallyKnownCards.remove(cardToPlayIndex);
            final Integer index = cardToPlayIndex;
            this.cardsToPlayByColor.forEach((value, positions) -> {
                if (positions.contains(index)) {
                    positions.remove(index);
                }
            });
            this.cardsToPlayByValue.forEach((value, positions) -> {
                if (positions.contains(index)) {
                    positions.remove(index);
                }
            });
        }
    }

    /**
     * Détermine si l'IA devrait défausser une carte.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si une carte doit être défaussée, faux sinon.
     */
    @Override
    protected boolean shouldDiscardCard(Game game) 
    {
        return !partiallyKnownCards.isEmpty() || !completelyKnownCards.isEmpty() || choosePlayerToGiveHint(game, false) == null; 
    }

    /**
     * Défausse une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void discardCard(Game game) 
    {
        Integer cardToDiscardIndex = null;
        for (Integer index : completelyKnownCards) 
        {
            if (isForcementInutile(this.getHand().getCard(index), game)) {
                cardToDiscardIndex = index;
                break;
            }
        }
        if (cardToDiscardIndex == null) 
        {
            for (Integer index : partiallyKnownCards) {
                if (isForcementInutile(this.getHand().getCard(index), game)) {
                    cardToDiscardIndex = index;
                    break;
                }
            }
        }
        // Si aucune carte inutile n'est identifiée, défausser une carte qui n'est pas précieuse
        if (cardToDiscardIndex == null) {
            for (int i = 0; i < this.getHand().getSize(); i++) {
                if (!this.isPeutEtrePrecieuse(this.getHand().getCard(i), game)) {
                    cardToDiscardIndex = i;
                    break;
                }
            }
        }

        // Si toutes les cartes sont précieuses, défaussez simplement la première carte
        if (cardToDiscardIndex == null) {
            cardToDiscardIndex = 0;  
        }

        if (cardToDiscardIndex != null) {
            System.out.println("IA " + getName() + " défausse la carte à la position " + (cardToDiscardIndex + 1));
            ArrayList<Card> h = new ArrayList<>(this.getHand().getCards());
            game.discardCard(cardToDiscardIndex);
            Card c = this.getHand().getCard(getHandSize() - 1);
            h.set(cardToDiscardIndex, c);
            this.getHand().setHand(h);
            completelyKnownCards.remove(cardToDiscardIndex);
            partiallyKnownCards.remove(cardToDiscardIndex);
            final Integer index = cardToDiscardIndex;
            this.cardsToPlayByColor.forEach((value, positions) -> {
                if (positions.contains(index)) {
                    positions.remove(index);
                }
            });
            this.cardsToPlayByValue.forEach((value, positions) -> {
                if (positions.contains(index)) {
                    positions.remove(index);
                }
            });
        }
    }
}
