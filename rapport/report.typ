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
  cover-image-caption: [Computer Architecture],
  
  logo: image("figs/isc_logo.svg"),
  
  code-theme: "bluloco-light", // See directory themes/ for available themes
)

//// If using acronyms
#import "@preview/acrostiche:0.6.0": *

// Let's get started folks!

#table-of-contents(depth: 2)

= Introduction
Ce projet a été effectué dans le cadre du cours de Programmation Orientée-Objets. Il s'agissait d'un projet plutôt libre. L'objectif attendu était de créer un jeu vidéo afin de mettre en oeuvre les concepts acquis tout au long de l'année.

= Moyens à disposition
Afin d'assurer le bon déroulement de ce projet, nous avons utilisé la libraire GDX2, une bibliothèque Java simple, sur laquelle nous nous sommes appuyés afin de gérer la partie graphique et physique.
Le projet à été entièrement écrit en Scala 2.13.18 à l'aide d'IntelliJ IDEA.

== Github
Ce projet étant commun, l'utilisation de Git nous a permis de collaborer de manière efficace et rapide. Nous avons utilisé Github pour sa simplicité d'intégration dans notre processus de développement. 

== GDX2D
La bibliothèque GDX2D est un projet open-source créée par divers enseignants de l'HES-SO Valais/Wallis. Le projet a été basé sur une bibliothèque déjà existante : libGDX.
 
= Inspiration
Nous avons eu l'idée de nous lancer dans la création de ce jeu en s'inspirant de Line Rider, un jeu de style bac à sable dans le quel un Rider glisse le long d'une ligne dessinée par le joueur. Ce type de jeu est très différent de la majortié. N'a pas de gagnant ou de perdant, mais il en va de la créativité de chacun. De plus chacun est libre de partager ses créations librement au sein de la communauté. 
= Choix d'implémentation

== Architecture générale
Le projet est structuré autour d'une classe principale `Game` qui étend `DesktopApplication` de la bibliothèque GDX2D. Cette classe gère la boucle de jeu principale et délègue les différentes fonctionnalités à des sous-systèmes spécialisés : `LineDrawMachine` et `FreeDrawMachine` pour le dessin, `MenuModesMachine` pour la gestion des modes, `MudryMachine` pour le personnage, et `MusicPlayer` pour l'audio.

== Gestion des modes de jeu
Nous avons implémenté une machine à états simple via la classe `MenuModesMachine`. Celle-ci gère les modes (`lines`, `free`, `play`, `eraser`, `mop`, `save`, `load`) ainsi que des toggles internes pour le mode de dessin (`physic`/`decoration`) et la musique (`music`/`musicmute`). Le mode courant est stocké sous forme de chaîne de caractères et transmis aux différentes machines de dessin, qui adaptent leur comportement en conséquence. Par exemple, en mode `play`, le dessin est désactivé et la caméra suit automatiquement le personnage.

== Deux systèmes de dessin distincts
Nous avons fait le choix de séparer le dessin de lignes segmentées (`LineDrawMachine`) et le dessin de lignes libres (`FreeDrawMachine`). La première fonctionne par clic, glisser et relâcher pour créer un segment rectiligne entre deux points, tandis que la seconde génère des traits continus en ajoutant des segments à chaque frame de glissement. Cette séparation s'explique par la différence fondamentale d'interaction utilisateur et de structure de données : `LineArray` pour les segments fixes, `FreeArray` (tableau de tableaux) pour les traits libres.

== Hiérarchie des lignes
Nous avons défini un trait `Line` comme interface commune à toutes les lignes, imposant les propriétés `p1x`, `p1y`, `p2x`, `p2y` et les méthodes `draw` et `destroy`. Deux implémentations concrètes en `case class` en découlent :
- `PhysicLine` étend `PhysicsStaticLine` (corps physique Box2D) et mixe le trait `Line`
- `DecoLine` implémente uniquement le trait `Line` sans composante physique

L'utilisation de `case class` nous permet de bénéficier de l'immutabilité des coordonnées, du pattern matching, et de l'égalité structurelle. L'héritage multiple de `PhysicLine` (classe physique + trait) a nécessité l'utilisation de `super[PhysicsStaticLine].destroy()` pour lever l'ambigüité.

== Gestion de la caméra
La caméra fonctionne selon deux modes. En mode édition (dessin), l'utilisateur peut se déplacer librement dans le monde via un glisser-déposer au clic droit, ce qui décale les coordonnées de la caméra (`camX`, `camY`). En mode `play`, la caméra suit automatiquement la position du personnage (`playerMachine.posX`, `playerMachine.posY`). Les coordonnées de clic souris sont converties en coordonnées monde via la formule `x + (camX - 960)` pour l'axe X, ce qui permet d'interagir correctement avec les éléments même après un déplacement de la vue.

== Optimisation du rendu
Afin de maintenir des performances élevées, seules les lignes situées dans un rayon de 2000 pixels autour de la caméra sont dessinées. Cela nous as permis de réduire l'impact sur la performance plus le nombre de lignes augemente.

== Système de sauvegarde et chargement
Le format de sauvegarde choisi est le CSV, simple à lire et à écrire. Chaque ligne est enregistrée au format `TypeLigne,x1,y1,x2,y2` dans le dossier `./saves/`. Le nom de fichier est généré automatiquement à partir de la date et de l'heure (`save_JJJHHMMSS.csv`). Au chargement, le fichier est parsé et chaque ligne est reconstruite en `PhysicLine` ou `DecoLine` selon son type. Ce format texte présente l'avantage d'être lisible par un humain et facile à déboguer.

== Détection de collision pour l'effacement
La classe `Calculator` implémente un algorithme de distance point-segment basé sur le produit vectoriel. Cette méthode permet de déterminer si un clic est suffisamment proche d'une ligne (tolérance de 8 pixels) pour l'effacer. L'outil "gomme" parcourt toutes les lignes et supprime celles qui se trouvent dans le rayon du curseur, tandis que l'outil "serpillière" (`mop`) efface l'intégralité du dessin après confirmation de l'utilisateur via la fenêtre `AreYouSureWindow`.

== Gestion audio
Le `MusicPlayer` utilise l'API `javax.sound.sampled.AudioSystem` pour lire des fichiers WAV. Deux pistes distinctes sont utilisées : une pour le mode édition (`edit.wav`, en boucle) et une pour le mode jeu (`play.wav`, lecture unique). Le son peut être coupé via un bouton dans le menu, ce qui stoppe et ferme le clip audio pour libérer les ressources.

= Code

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
