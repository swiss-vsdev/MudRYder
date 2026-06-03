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

#table-of-contents(depth: 1)

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
Nous avons eu l'idée de nous lancer dans la création de ce jeu en s'inspirant de Line Rider, un jeu de style bac à sable dans le quel un Rider glisse le long d'une ligne dessinée par le joueur. Ce type de jeu est très différent de la majortié. Il n'y a pas de réel objectif, mais il en va de la créativité de chacun. De plus chacun est libre de partager ses créations librement au sein de la communauté. 
= Choix d'implémentation
