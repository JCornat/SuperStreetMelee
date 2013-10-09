Version 1 :
Utilisation de la
Affichage d’un rectangle, correspondant au joueur 1, qui correspondra à une boîte englobante, pour gérer dans le futur, les collisions (aussi appelée AABB [Axis Aligned Bounding Box])
Le rectangle avance tout seul

Version 2 :
Contrôle du rectangle avec des touches (ZQSD, Q et D pour se déplacer sur les côtés, Z et S pour sauter et s’accroupir)
Gestion de la gravité, avec détection du sol et de plates-formes

Version 2.5 :
Création d’un niveau avec plates-formes, pour tester les collisions avec le décor.

Version 3 :
Possibilité de placer un coup, avec comme modèle un point.
Ce point permettra de déterminer si le coup a touché le joueur adverse.

Version  4 :
Création d’un deuxième personnage joueur 

Version 5 : 
Mise en place des sprites. Utilisation de la fonction « KeyColor » pour déterminer les couleurs à afficher ou non.
Adaptation des collisions sur le modèle avec la création de SAABB, contenues dans l’AABB, pour une gestion plus précise. L’utilisation d’un masque pourrait être aussi une solution.

Version 6 :
