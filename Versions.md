Version 1 - Thème : Base
Utilisation de la librairie Swing.
Affichage d’un rectangle, correspondant au joueur 1, qui correspondra à une boîte englobante, pour gérer dans le futur, les collisions (aussi appelée AABB [Axis Aligned Bounding Box]).
Le rectangle avance tout seul.
Difficulté prévue : Aucune en particulière.

Version 1.5 - Thème : Détection de touches
Contrôle du rectangle avec des touches (ZQSD, Q et D pour se déplacer sur les côtés, Z et S pour sauter et s’accroupir).
Difficulté : Gérer l'appui de plusieurs touches en même temps.

Version 2 - Thème : Collisions
Gestion de la gravité, avec détection du sol et de plates-formes.
Création d’un niveau avec plates-formes, pour tester les collisions avec le décor.
Difficulté : Gérer les collisions ! Tâche sûrement hardue...

Version 3 - Thème : Coups
Déterminer un sens au joueur : s'il est tourné vers la gauche, le coup ira dans le même sens. Et inversement.
Possibilité de placer un coup, avec comme modèle un point : ce point permettra de déterminer facilement si le coup a touché le joueur adverse.
Difficultée : Peut-être le fait de gérer le sens du joueur.

Version 4 - Thème : Vies
Création d’un deuxième personnage joueur.
Ajout de barre de dégâts en % et de vies.
Difficulté : Aucune en perspective

Version 5 - Thème : Gameplay
Mise en place d'un gameplay : Le joueur commence avec un certain nombre de vies, et 0% de dégâts
Si le nombre de vies tombe à 0, le personnage a perdu. 
Si le personnage est expédié hors de l'écran, le joueur se voit enlever une vie.
Ajout de la fonction temps, qui permettra d'arrêter le jeu si la partie commence à durer.
Difficulté : Gérer les forces qui feront s'envoler le joueur en fonction des dégâts qu'il subit.

Version 6 - Thème : Graphisme puis Codage 
Mise en place des sprites. Utilisation de la fonction « KeyColor » pour déterminer les couleurs à afficher ou non.
Adaptation des collisions sur le modèle avec la création de SAABB, contenues dans l’AABB, pour une gestion plus précise. L’utilisation d’un masque pourrait être aussi une solution.
Difficulté : Gérer les collisions avec les SAABB, et surtout, gérer les différentes SAABB en fonction des sprites !

Version 7 - Thème : Sons
Mise en place de sons : Musique en arrière plan, et bruitages (Coups reçus, saut, début)
Difficulté : Nécessité d'utiliser sûrement des Threads pour gérer ce pan.

Version 8 - Thème : Diversités de personnages
Ajout d'un personnage avec ses propres caractéristiques.
Ajout d'une attaque spéciale unique à chaque personnage.
Difficulté : Pas forcément de grosse diffculté ici.

Version 9 - Thème : IA
Ajout d'une IA.
Augmenter le nombre de joueurs à 4 sur le terrain.
Difficulté : Gérer l'IA !



Idées : Générer monde aléatoirement

A faire : 
- Zoom
- Sous-type de Décor : 
	- Décor traversable depuis le bas et le côté (Plate-forme simple) et peut-être déplaçable