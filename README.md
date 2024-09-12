## Résolvez la Guerre de Troie !

Les grecs attaquent les troyens.\
Chaque clan a des armées.\
Nous voulons connaitre le résultat de la bataille.

La bataille oppose deux clans : 
- Le clan grec
- Le clan troyen

Chaque clan possède des armées :\
Chaque armée a un nom et est constituée de régiments, uniquement Fantassins pour le moment.\
Un régiment de fantassins est caractérisé par : 
- un nombre de fantassins
- un nombre de points de vie par fantassin
- un nombre de points d'attaque par fantassin
- un nombre de points de défense par fantassin

## Résolution
La bataille se déroule en plusieurs tours.\
A chaque tour, la 1ère armée d'un clan se bat contre la 1ère armée de l'autre clan.\
On calcule les dégats infligés réciproquement à chaque armée. \
Pour calculer les dégats entre armée 1 et armée 2 :\
- Attaque armée 1 = Attaque de tous les fantassins de l'armée 1 à laquelle on soustrait la défense de tous les fantassins de l'armée 2.\
- Attaque armée 2 = Attaque de tous les fantassins de l'armée 2 à laquelle on soustrait la défense de tous les fantassins de l'armée 1.

Puis on retire, pour chaque armée, le nombre de fantassins tués par l'autre armée :\
Nombre de fantassins tués = dégats / points de vie par fantassin

Il y a égalité si aucun vainqueur ne peut être trouvé.

Une  armée est supprimée lorsque son nombre de fantassins atteint 0. L'armée détruite sera remplacée par la suivante pour le prochain tour. \
Si le clan n'a plus d'armée, il a perdu la bataille.

Chaque résolution de  bataille retourne un rapport de bataille qui contient :
- le statut de la bataille (gagnée, perdue, égalité)
- si un clan a gagné : le clan gagnant
- la composition des armées initiales de chaque clan
- historique des tours de bataille (les armées en jeu au cours du tour, les dégats infligés sur chaque armée, le nombre de fantassins vivants dans chaque armée)

## Exemple de rapport :

```json
{
  "winner": null,
  "status": "DRAW",
  "initialsClans": [
    {
      "name": "troyes",
      "army": [
        {
          "name": "armee1",
          "fantassins": {
            "nombresPersonnes": 100,
            "attaque": 100,
            "defense": 100,
            "vie": 100
          },
          "armyAttaque": 10000,
          "armyDefense": 10000
        },
        {
          "name": "armee1_1",
          "fantassins": {
            "nombresPersonnes": 100,
            "attaque": 1000,
            "defense": 100,
            "vie": 100
          },
          "armyAttaque": 100000,
          "armyDefense": 10000
        }
      ]
    },
    {
      "name": "grecques",
      "army": [
        {
          "name": "armee2_2",
          "fantassins": {
            "nombresPersonnes": 50,
            "attaque": 50,
            "defense": 500,
            "vie": 100
          },
          "armyAttaque": 2500,
          "armyDefense": 25000
        }
      ]
    }
  ],
  "histories": [
    {
      "armyName1": "armee1",
      "armyName2": "armee2",
      "degatArmy1": -7500,
      "degatArmy2": 5000,
      "nbFantassins1": 100,
      "nbFantassins2": 0
    },
    {
      "armyName1": "armee1",
      "armyName2": "armee2_2",
      "degatArmy1": -7500,
      "degatArmy2": -15000,
      "nbFantassinsVivant1": 100,
      "nbFantassinsVivant2": 50
    },
    {
      "armyName1": "armee1",
      "armyName2": "armee2_2",
      "degatArmy1": -7500,
      "degatArmy2": -15000,
      "nbFantassinsVivant1": 100,
      "nbFantassinsVivant2": 50
    }
  ]
}
```

## Autres points techniques

Les APIs permettent d'interagir avec le simulateur de bataille :
- ajouter des armées dans un clan
- supprimer des armées dans un clan
- lancer la simulation de bataille entre les deux clans

Une couche de persistance a été définie dans le projet pour retenir la composition des clans et les rapports de bataille. \
La persistence peut s'effectuer dans une base de données H2 ou dans un cache de mémoire pour une persistance durant le temps de vie de l'application.


## A vous de jouer ! 
