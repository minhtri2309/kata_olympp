# Résolvez la Guerre de Troie !

> Héros développeur, la guerre de Troie fait rage !
> Votre mission : déterminer l’issue de la bataille et dévoiler qui sortira victorieux. Votre code est votre arme pour analyser les événements, prédire l’issue finale et transmettre les résultats aux stratèges d’Olympp. Soyez astucieux et précis, l’avenir du siège repose sur vos compétences !

## Enoncé
Les grecs attaquent les troyens. \
Chaque clan a des armées. \
Nous voulons connaitre le résultat de la bataille.

La bataille oppose deux clans : 
- Le clan grec
- Le clan troyen

Chaque clan possède des armées : \
Chaque armée a un nom et est constituée de régiments de fantassins uniquement. \
Un régiment de fantassins est caractérisé par : 
- un nombre de fantassins
- un nombre de points de vie par fantassin
- un nombre de points d'attaque par fantassin
- un nombre de points de défense par fantassin

## Résolution
La bataille se déroule en plusieurs tours. \
A chaque tour, la 1ère armée d'un clan se bat contre la 1ère armée de l'autre clan. \
On calcule les dégats infligés simultanément à chaque armée. \
Pour calculer les dégats entre armée 1 et armée 2 : 
- Attaque armée 1 = Attaque de tous les fantassins de l'armée 1 à laquelle on soustrait la défense de tous les fantassins de l'armée 2. 
- Attaque armée 2 = Attaque de tous les fantassins de l'armée 2 à laquelle on soustrait la défense de tous les fantassins de l'armée 1.

Puis on retire, pour chaque armée, le nombre de fantassins tués par l'autre armée : \
Nombre de fantassins tués = dégats / points de vie par fantassin

Il y a égalité si aucun vainqueur ne peut être trouvé.

Une  armée est décimée lorsque son nombre de fantassins atteint 0. L'armée déciméé sera remplacée par la suivante pour le prochain tour. \
Si le clan n'a plus d'armées opérationnelles, il a perdu la bataille.

Chaque résolution de  bataille retourne un rapport de bataille qui contient :
- le statut de la bataille (gagnée, égalité)
- si un clan a gagné : le clan gagnant
- la composition des armées initiales de chaque clan avant début de résolution de la bataille
- historique de l'ensemble des tours de bataille (pour chaque tour, préciser les armées qui combattent, les dégats infligés sur chaque armée, le nombre de fantassins vivants dans chaque armée)

## Exemple du rapport d'une bataille entre grecs et troyens :

```json
{
  "winner": null,
  "status": "DRAW",
  "initialClans": [
    {
      "name": "Troy",
      "armies": [
        {
          "name": "army1",
          "foot_soldiers": {
            "nbUnits": 100,
            "attack": 100,
            "defense": 100,
            "health": 100
          },
          "armyAttack": 10000,
          "armyDefense": 10000
        },
        {
          "name": "army1_1",
          "foot_soldiers": {
            "nbUnits": 100,
            "attack": 1000,
            "defense": 100,
            "health": 100
          },
          "armyAttack": 100000,
          "armyDefense": 10000
        }
      ]
    },
    {
      "name": "Athens",
      "armies": [
        {
          "name": "army2_2",
          "foot_soldiers": {
            "nbUnits": 50,
            "attack": 50,
            "defense": 500,
            "health": 100
          },
          "armyAttack": 2500,
          "armyDefense": 25000
        }
      ]
    }
  ],
  "history": [
    {
      "nameArmy1": "army1",
      "nameArmy2": "army2_2",
      "damageArmy1": -7500,
      "damageArmy2": 5000,
      "nbRemainingSoldiersArmy1": 100,
      "nbRemainingSoldiersArmy2": 0
    },
    {
      "nameArmy1": "armee1",
      "nameArmy2": "armee2_2",
      "damageArmy1": -7500,
      "damageArmy2": -15000,
      "nbRemainingSoldiersArmy1": 100,
      "nbRemainingSoldiersArmy2": 50
    },
    {
      "nameArmy1": "armee1",
      "nameArmy2": "armee2_2",
      "damageArmy1": -7500,
      "damageArmy2": -15000,
      "nbRemainingSoldiersArmy1": 100,
      "nbRemainingSoldiersArmy2": 50
    }
  ]
}
```

## Autres points techniques

Les APIs permettent d'interagir avec le simulateur de bataille :
- ajouter des armées dans un clan
- supprimer des armées dans un clan
- lancer la simulation de bataille entre les deux clans

Ajouter une API permettant de retourner un clan avec le details de ses effectifs par armées ainsi que son statut suite a la derniere bataille engagée.

Une couche de persistance a été définie dans le projet pour retenir la composition des clans et les rapports de bataille. \
La persistence peut éventuellement s'effectuer dans une base de données H2 ou dans un cache de mémoire pour une persistance durant le temps de vie de l'application.


## A vous de jouer ! 
