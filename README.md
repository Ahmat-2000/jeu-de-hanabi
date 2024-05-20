
# Hanabi Game Project

## Description

Ce projet implémente le jeu Hanabi en utilisant Java. Il comprend des versions graphiques et console du jeu, et utilise une architecture basée sur des modèles, des vues et des contrôleurs (MVC). Le projet peut être exécuté en utilisant Apache Ant ou directement à partir du fichier JAR généré.

## Structure du Projet

Le projet est organisé comme suit :

```
├── build
│   ├── content
│   │   └── images
│   ├── controller
│   ├── main
│   ├── model
│   │   ├── observer
│   │   └── strategies
│   └── view
├── dist
│   └── docs
│       └── api
│           ├── controller
│           ├── legal
│           ├── main
│           ├── model
│           │   ├── observer
│           │   └── strategies
│           ├── resources
│           ├── script-dir
│           └── view
└── src
    ├── content
    │   └── images
    ├── controller
    ├── main
    ├── model
    │   ├── observer
    │   └── strategies
    └── view
```

## Prérequis

- Java JDK 8 ou supérieur
- Apache Ant (facultatif, pour la compilation via Ant)

## Compilation et Exécution

### Utilisation d'Apache Ant

1. Assurez-vous qu'Apache Ant est installé et configuré sur votre machine.
2. Ouvrez un terminal et naviguez jusqu'à la racine du projet.
3. Exécutez la commande suivante pour compiler le projet :

   ```sh
   ant compile
   ```

4. Pour exécuter l'application, utilisez la commande suivante :

   ```sh
   ant run
   ```

### Utilisation du fichier JAR

1. Assurez-vous que le JDK est installé et configuré sur votre machine.
2. Compilez le projet en utilisant votre IDE ou en ligne de commande.
3. Naviguez jusqu'au dossier contenant le fichier JAR généré.
4. Exécutez la commande suivante pour lancer l'application :

   ```sh
   java -jar dist/Hanabi-0.1.jar
   ```

## Fonctionnalités

- **Interface Graphique** : Une interface utilisateur graphique pour jouer au jeu Hanabi.
- **Interface Console** : Une version console du jeu pour ceux qui préfèrent une interface en ligne de commande.
- **Joueurs IA** : Implémentation de joueurs IA avec différentes stratégies.
- **Documentation API** : Documentation complète de l'API générée dans le dossier `dist/docs/api`.
