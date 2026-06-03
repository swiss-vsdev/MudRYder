# MudRYder
The best game ever created
By Aurélien Santi and Gabriel Zeizer extends coffee-s

## Goal
MudRYder is a remake of the iconic game *Line Rider* with a better rider.
Draw your track, hit play, and watch your very own Mudry ride.

### Key features:
🖊️ Draw your track freely

🏍️ Make your very own Mudry ride

🔁 Scrub & replay

🧪 Sandbox-first : no levels, no objectives, just freedom

## Dependencies
- Scala 2.13.18
- Java 25
- gdx2D

## Screenshots

![alt text](https://github.com/swiss-vsdev/MudRYder/blob/main/screenshots/splash.png?raw=true)
Game splashscreen

![alt text](https://github.com/swiss-vsdev/MudRYder/blob/main/screenshots/ride.png?raw=true)
Image of the game while it's playing

## Video

![Watch the video](https://github.com/swiss-vsdev/MudRYder/blob/main/screenshots/video.gif)
Game in action

## Roadmap
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
- [x] Button to move the camera back to center
- [x] Allow saving the map to a file
- [x] Allow loading a map from a file
- [ ] Add an "accelerator" drawer to speed up the Mudry
- [ ] Make the sledding smoother during angled collisions (not sure if this is even possible without completely rewriting the physics engine)
- [ ] Make the sled follow the angle of the slope underneath it

## Suggestions :
- Split Mudry into two separate assets: a sled that rotates according to the movement, and Mudry himself, who stays upright on top of it.
