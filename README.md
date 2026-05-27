# MudRYder
The best game ever created

By Aurélien Santi and Gabriel Zeizer extends coffee-s

### Goal
The goal is to recreate a game similar to "Line Rider" fully in scala

## Dependencies
- Scala 2.13.18
- Java 25
- gdx2D

### TO DO :
- [x] Create the basic structure
- [x] Use classes?
- [x] Create the drawing system ->
- [x] Create (reuse) the physics system
- [x] Create (implement) the camera system sticky to the Mudry
- [x] Create (implement) the camera movement system when drawing
- [x] Find (create) a Line Rider Mudry Asset
- [x] Add an eraser
- [x] Add a "restart all over" button
- [x] Add a "decoration" drawer without physics
- [ ] Button to move the camera back to center
- [ ] Make the sled follow the angle of the slope underneath it
- [ ] Allow saving the map to a file
- [ ] Allow loading a map from a file
- [ ] Add an "accelerator" drawer to speed up the Mudry
- [ ] Make the sledding smoother during angled collisions (not sure if this is even possible without completely rewriting the physics engine)

Suggestions :
- Split Mudry into two separate assets: a sled that rotates according to the movement, and Mudry himself, who stays upright on top of it.
 
 ### Note about commits
Coded using "CodeTogether Cloud", so some personal commits are actually the product of joint work on the same Mac