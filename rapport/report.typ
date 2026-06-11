#import "@preview/isc-hei-report:0.6.0" : *

#let doc_language = "fr" // Valid values are en, fr

#show: project.with(
  title: "Projet final - MudRYder",
  authors: ("Gabriel Zeizer, Aurélien Santi"),  
  date: datetime.today(), // or datetime.today()
  language: doc_language, // Please change the value above if required
  
  course-name: "101.2 Prog. orientée-objets",
  course-supervisor: "Dr Pierre-André Mudry",
  semester: "Semestre de printemps",
  academic-year: "2025-2026",
  
  cover-image: image("figs/mudry2.png"),
  cover-image-height: 8cm,
  cover-image-caption: [MudRYder - Personnage],
  
  logo: image("figs/isc_logo.svg"),
  
  code-theme: "bluloco-light", // See directory themes/ for available themes
)

//// If using acronyms
#import "@preview/acrostiche:0.6.0": *

// Let's get started folks!

#table-of-contents(depth: 2)

= Introduction

Ce projet a été effectué dans le cadre du cours de Programmation Orientée-Objets. Il s'agissait d'un projet plutôt libre dont l'objectif était de créer un jeu vidéo afin de mettre en oeuvre les concepts acquis tout au long de l'année.

Nous avons choisi de recréer le jeu *Line Rider*, un classique du genre "bac à sable" où le joueur dessine des pistes qu'un personnage parcourt ensuite par gravité.

#figure(image("figs/splash.png", width: 90%), caption: [Écran de démarrage du jeu])

= Moyens à disposition
Afin d'assurer le bon déroulement de ce projet, nous avons utilisé la bibliothèque GDX2, une bibliothèque Java simple, sur laquelle nous nous sommes appuyé afin de gérer la partie graphique et physique.
Le projet a été entièrement écrit en Scala 2.13.18 à l'aide d'IntelliJ IDEA.

#figure(
  grid(
    columns: (1fr, 1fr),
    figure(image("figs/scalaLogo.svg", width: 40%), caption: [Logo de Scala]),
    figure(image("figs/IntelliJLogo.png", width: 40%), caption: [Logo de IntelliJ IDEA]),
  )
)

== GitHub
Ce projet étant commun, l'utilisation de Git nous a permis de collaborer de manière efficace et rapide. Nous avons utilisé GitHub pour sa simplicité d'intégration dans notre processus de développement. 

Le projet est disponible à l'adresse suivante : https://github.com/swiss-vsdev/MudRYder

== GDX2D
La bibliothèque GDX2D est un projet open-source créée par divers enseignants de l'HES-SO Valais/Wallis. Le projet a été basé sur une bibliothèque déjà existante : libGDX #cite(<libgdx>).
 
= Inspiration
Nous avons eu l'idée de nous lancer dans la création de ce jeu en nous inspirant de Line Rider #cite(<linerider>), un jeu de style bac à sable dans lequel un rider glisse le long d'une ligne dessinée par le joueur. Ce type de jeu est très différent de la majorité : il n'a pas de gagnant ou de perdant, mais il en va de la créativité de chacun.

#figure(image("figs/LineRiderOriginal.png", width: 75%), caption: [Line Rider, le jeu original])
= Choix d'implémentation

== Architecture générale
Le projet est structuré autour d'une classe principale `Game` qui étend `DesktopApplication` de la bibliothèque GDX2D. Cette classe gère la boucle de jeu principale et délègue les différentes fonctionnalités à des sous-systèmes spécialisés : `LineDrawMachine` et `FreeDrawMachine` pour le dessin, `MenuModesMachine` pour la gestion des modes, `MudryMachine` pour le personnage, et `MusicPlayer` pour l'audio.

#figure(image("figs/uml.png", width: 83%), caption: [Diagramme de classes UML du projet])

== Gestion des modes de jeu
Nous avons implémenté une machine à états simple via la classe `MenuModesMachine`. Celle-ci gère les modes (`lines`, `free`, `play`, `eraser`, `mop`, `save`, `load`) ainsi que des toggles internes pour le mode de dessin (`physic`/`decoration`) et la musique (`music`/`musicmute`). Le mode courant est stocké sous forme de chaîne de caractères et transmis aux différentes machines de dessin, qui adaptent leur comportement en conséquence. Par exemple, en mode `play`, le dessin est désactivé et la caméra suit automatiquement le personnage.

#figure(image("figs/GameModes.png", width: 70%), caption: [Menu des modes de jeu])

== Deux systèmes de dessin distincts
Nous avons fait le choix de séparer le dessin de lignes segmentées (`LineDrawMachine`) et le dessin de lignes libres (`FreeDrawMachine`). La première fonctionne par clic, glisser et relâcher pour créer un segment rectiligne entre deux points, tandis que la seconde génère des traits continus en ajoutant des segments à chaque frame de glissement. Cette séparation s'explique par la différence fondamentale d'interaction utilisateur et de structure de données : `LineArray` pour les segments fixes, `FreeArray` (tableau de tableaux) pour les traits libres.

#figure(image("figs/InGame.png", width: 90%), caption: [Mudry en pleine course sur les pistes])

#pagebreak()

== Hiérarchie des lignes
Nous avons défini un trait `Line` comme interface commune à toutes les lignes, imposant les propriétés `p1x`, `p1y`, `p2x`, `p2y` et les méthodes `draw` et `destroy`. Deux implémentations concrètes en `case class` en découlent :
- `PhysicLine` étend `PhysicsStaticLine` (corps physique Box2D #cite(<box2d>)) et mixe le trait `Line`
- `DecoLine` implémente uniquement le trait `Line` sans composante physique

L'utilisation de `case class` nous permet de bénéficier de l'immutabilité des coordonnées, du pattern matching, et de l'égalité structurelle. L'héritage multiple de `PhysicLine` (classe physique + trait) a nécessité l'utilisation de `super[PhysicsStaticLine].destroy()` pour lever l'ambigüité.

#figure(image("figs/Physic-DecoLines.png", width: 90%), caption: [Lignes physiques (noir) et décoratives (bleu)])

== Gestion de la caméra
La caméra fonctionne selon deux modes. En mode édition (dessin), l'utilisateur peut se déplacer librement dans le monde via un glisser-déposer au clic droit, ce qui décale les coordonnées de la caméra (`camX`, `camY`). En mode `play`, la caméra suit automatiquement la position du personnage (`playerMachine.posX`, `playerMachine.posY`). Les coordonnées de clic souris sont converties en coordonnées monde via la formule `x + (camX - 960)` pour l'axe X, ce qui permet d'interagir correctement avec les éléments même après un déplacement de la vue.

== Optimisation du rendu
Afin de maintenir des performances élevées, seules les lignes situées dans un rayon de 2000 pixels autour de la caméra sont dessinées. Cela nous a permis de réduire l'impact sur la performance plus le nombre de lignes augmente.

#pagebreak()

== Système de sauvegarde et chargement
Le format de sauvegarde choisi est le CSV, simple à lire et à écrire. Chaque ligne est enregistrée au format `TypeLigne,x1,y1,x2,y2` dans le dossier `./saves/`. Le nom de fichier est généré automatiquement à partir de la date et de l'heure (`save_JJJHHMMSS.csv`). Au chargement, le fichier est parsé et chaque ligne est reconstruite en `PhysicLine` ou `DecoLine` selon son type. Ce format texte présente l'avantage d'être lisible par un humain et facile à déboguer.

Voici un exemple de fichier de sauvegarde :

#figure(code()[
  #raw("PhysicLine,100.0,200.0,500.0,200.0
DecoLine,300.0,400.0,600.0,400.0
PhysicLine,500.0,200.0,800.0,600.0", lang: "csv")
], caption: "Exemple de fichier de sauvegarde au format CSV")

#figure(image("figs/SavingFile.png", width: 50%), caption: [Notification de sauvegarde])

#figure(image("figs/LoadWindow.png", width: 50%), caption: [Fenêtre de chargement])

Chaque ligne contient : TypeLigne, x1, y1, x2, y2. Le type PhysicLine crée une ligne avec collision physique, DecoLine une ligne purement décorative (bleue, sans physique).

== Détection de collision pour l'effacement
La classe `Calculator` implémente un algorithme de distance point-segment basé sur le produit vectoriel. Cette méthode permet de déterminer si un clic est suffisamment proche d'une ligne (tolérance de 8 pixels) pour l'effacer. L'outil "gomme" parcourt toutes les lignes et supprime celles qui se trouvent dans le rayon du curseur, tandis que l'outil "serpillière" (`mop`) efface l'intégralité du dessin après confirmation de l'utilisateur via la fenêtre `AreYouSureWindow`.

== Gestion audio
Le `MusicPlayer` utilise l'API `javax.sound.sampled.AudioSystem` pour lire des fichiers WAV. Deux pistes distinctes sont utilisées : une pour le mode édition (`edit.wav`, en boucle) et une pour le mode jeu (`play.wav`, lecture unique). Le son peut être coupé via un bouton dans le menu, ce qui stoppe et ferme le clip audio pour libérer les ressources.

= Problèmes rencontrés et solutions

== Surcharge CPU
Au fur et à mesure que le nombre de lignes augmentait, le jeu commençait à ralentir considérablement. Le problème venait du fait que toutes les lignes étaient dessinées à chaque frame, même celles situées loin de la caméra. Nous avons résolu ce problème en implémentant un système où seules les lignes situées dans un rayon de 2000 pixels autour de la caméra sont dessinées. Cela a considérablement amélioré les performances.

== Crash avec des tableaux de lignes vides
Un crash survenait lorsque le joueur effaçait toutes les lignes puis tentait d'en dessiner une nouvelle. Le moteur physique ne gérait pas correctement un monde vide. Nous avons contourné le problème en insérant une ligne factice très loin de l'écran (coordonnées -10000, -10000) dès que le tableau est vide (_ArrayEmptyFix_).

== Gestion des angles du personnage
Initialement, le personnage ne suivait pas correctement l'inclinaison des pentes. Nous avons modifié la méthode `collision()` de `MudryMachine` pour lire l'angle du segment contacté et appliquer une correction au-delà de ±120 degrés, stabilisant ainsi le comportement.

== Optimisation des calculs vectoriels
L'outil gomme utilisait des calculs de distance point-segment coûteux. Nous avons optimisé l'algorithme en ajoutant une vérification préalable des limites avant le calcul du produit vectoriel.
Nous avons également essayé de réduire le nombre de cycles CPU nécessaires en modifiant certains calculs. En remplaçant un `math.pow(x,2)` par simplement `x * x` de cette manière, nous avons encore pu réduire l'impact des calculs.

= Fonctionnalités

== Ce qui fonctionne
- Dessin de lignes (segmentées et libres, physiques et décoratives)
- Physique complète (gravité, collisions avec le personnage)
- Caméra avec deux modes (suivi automatique en jeu, libre en édition)
- Outils d'effacement (gomme individuelle et serpillière avec confirmation)
- Sauvegarde et chargement de pistes au format CSV
- Écran de démarrage animé, musique d'ambiance, HUD (FPS, coordonnées)

== Limites connues
- La musique peut recommencer lors de certains évènements, par exemple lors de l'exploration des fichiers enregistrés.
- La fenêtre de chargement suppose que le dossier `saves/` contient au moins un fichier valide
- Si un fichier est généré / ajouté au dossier `saves/` après avoir lancé le jeu, il n'apparaîtra pas.
- Le personnage ne suit pas parfaitement l'angle de la pente


#pagebreak()

= Améliorations et auto-critique

== Propositions d'améliorations
- Ajouter un outil "accélérateur" pour propulser Mudry (prévu dans la roadmap initiale)
- Rendre le déplacement sur les angles plus fluide
- Unifier les deux systèmes de dessin (`LineDrawMachine` et `FreeDrawMachine`) pour réduire la duplication de code
- Ajouter une gestion d'erreur (fichiers, index, dossier vide)
- Remplacer le correctif _ArrayEmptyFix_ par une solution plus propre
- Améliorer la structure du code pour le rendre plus simple à consulter et modifier

== Auto-critique
Le projet s'est bien déroulé dans l'ensemble. L'utilisation de Git nous a permis de travailler en parallèle efficacement. Cependant, le découpage en classes aurait pu être mieux pensé en amont : la séparation en deux systèmes de dessin distincts a créé de la duplication et des incohérences, notamment pour la gomme et la sauvegarde. Nous avons passé beaucoup de temps à implémenter des fonctionnalités mais pas assez à restructurer le code et à l'optimiser. À l'avenir, il faudrait prioriser la robustesse du code avant les fonctionnalités secondaires.

= Conclusion

Ce projet nous a permis de mettre en pratique les concepts de programmation orientée-objet vus durant le semestre : héritage, polymorphisme, traits et les case classes. Malgré quelques limitations, le jeu est fonctionnel et atteint les objectifs fixés. Nous sommes satisfaits du résultat et cette expérience nous a appris l'importance d'une bonne architecture logicielle ainsi que d'une répartition claire des tâches au sein d'une équipe.

= Annexes

Le code source complet du projet est présenté ci-dessous.

== Point d'entrée
#figure(code()[
  #raw(read("../src/main.scala"), lang: "scala")
], caption: "Point d'entrée du programme - main.scala")

== Cœur du jeu
#figure(code()[
  #raw(read("../src/Game.scala"), lang: "scala")
], caption: "Classe principale du jeu - Game.scala")

#figure(code()[
  #raw(read("../src/MudryMachine.scala"), lang: "scala")
], caption: "Personnage physique du jeu - MudryMachine.scala")

#figure(code()[
  #raw(read("../src/MusicPlayer.scala"), lang: "scala")
], caption: "Gestion de la musique - MusicPlayer.scala")

== Systèmes de dessin
#figure(code()[
  #raw(read("../src/LineDrawMachine.scala"), lang: "scala")
], caption: "Dessin de lignes segmentées - LineDrawMachine.scala")

#figure(code()[
  #raw(read("../src/FreeDrawMachine.scala"), lang: "scala")
], caption: "Dessin de lignes libres - FreeDrawMachine.scala")

#figure(code()[
  #raw(read("../src/Calculator.scala"), lang: "scala")
], caption: "Calculs géométriques - Calculator.scala")

== Types de lignes
#figure(code()[
  #raw(read("../src/Line.scala"), lang: "scala")
], caption: "Trait de base pour les lignes - Line.scala")

#figure(code()[
  #raw(read("../src/PhysicLine.scala"), lang: "scala")
], caption: "Ligne physique - PhysicLine.scala")

#figure(code()[
  #raw(read("../src/DecoLine.scala"), lang: "scala")
], caption: "Ligne de décoration - DecoLine.scala")

== Fenêtres UI
#figure(code()[
  #raw(read("../src/SplashScreenWindow.scala"), lang: "scala")
], caption: "Écran de démarrage - SplashScreenWindow.scala")

#figure(code()[
  #raw(read("../src/LoadWindow.scala"), lang: "scala")
], caption: "Fenêtre de chargement - LoadWindow.scala")

#figure(code()[
  #raw(read("../src/SavingWindow.scala"), lang: "scala")
], caption: "Notification de sauvegarde - SavingWindow.scala")

#figure(code()[
  #raw(read("../src/AreYouSureWindow.scala"), lang: "scala")
], caption: "Fenêtre de confirmation - AreYouSureWindow.scala")

== Modes de jeu
#figure(code()[
  #raw(read("../src/MenuModesMachine.scala"), lang: "scala")
], caption: "Gestionnaire des modes du menu - MenuModesMachine.scala")

#figure(code()[
  #raw(read("../src/MenuModes.scala"), lang: "scala")
], caption: "Structure des modes du menu - MenuModes.scala")

== Types
#figure(code()[
  #raw(read("../src/typesLibrary.scala"), lang: "scala")
], caption: "Type alias pour les lignes - typesLibrary.scala")

#bibliography("bibliography.bib", style: "ieee", title: "Références")
