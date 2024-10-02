## Résolvez la Guerre de Troie !

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
  &quot;winner&quot;: null,
  &quot;status&quot;: &quot;DRAW&quot;,
  &quot;initialClans&quot;: [
    {
      &quot;name&quot;: &quot;Troy&quot;,
      &quot;armies&quot;: [
        {
          &quot;name&quot;: &quot;army1&quot;,
          &quot;foot_soldiers&quot;: {
            &quot;nbUnits&quot;: 100,
            &quot;attack&quot;: 100,
            &quot;defense&quot;: 100,
            &quot;health&quot;: 100
          },
          &quot;armyAttack&quot;: 10000,
          &quot;armyDefense&quot;: 10000
        },
        {
          &quot;name&quot;: &quot;army1_1&quot;,
          &quot;foot_soldiers&quot;: {
            &quot;nbUnits&quot;: 100,
            &quot;attack&quot;: 1000,
            &quot;defense&quot;: 100,
            &quot;health&quot;: 100
          },
          &quot;armyAttack&quot;: 100000,
          &quot;armyDefense&quot;: 10000
        }
      ]
    },
    {
      &quot;name&quot;: &quot;Athens&quot;,
      &quot;armies&quot;: [
        {
          &quot;name&quot;: &quot;army2_2&quot;,
          &quot;foot_soldiers&quot;: {
            &quot;nbUnits&quot;: 50,
            &quot;attack&quot;: 50,
            &quot;defense&quot;: 500,
            &quot;health&quot;: 100
          },
          &quot;armyAttack&quot;: 2500,
          &quot;armyDefense&quot;: 25000
        }
      ]
    }
  ],
  &quot;history&quot;: [
    {
      &quot;nameArmy1&quot;: &quot;army1&quot;,
      &quot;nameArmy2&quot;: &quot;army2_2&quot;,
      &quot;damageArmy1&quot;: -7500,
      &quot;damageArmy2&quot;: 5000,
      &quot;nbRemainingSoldiersArmy1&quot;: 100,
      &quot;nbRemainingSoldiersArmy2&quot;: 0
    },
    {
      &quot;nameArmy1&quot;: &quot;armee1&quot;,
      &quot;nameArmy2&quot;: &quot;armee2_2&quot;,
      &quot;damageArmy1&quot;: -7500,
      &quot;damageArmy2&quot;: -15000,
      &quot;nbRemainingSoldiersArmy1&quot;: 100,
      &quot;nbRemainingSoldiersArmy2&quot;: 50
    },
    {
      &quot;nameArmy1&quot;: &quot;armee1&quot;,
      &quot;nameArmy2&quot;: &quot;armee2_2&quot;,
      &quot;damageArmy1&quot;: -7500,
      &quot;damageArmy2&quot;: -15000,
      &quot;nbRemainingSoldiersArmy1&quot;: 100,
      &quot;nbRemainingSoldiersArmy2&quot;: 50
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
