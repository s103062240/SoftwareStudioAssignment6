Software Studio Assignment 6
============================

Design
------
A Java Program to show relationships of characters in Star Wars eposide 1~7

Library used: [Ani][1], [controlP5][2], [Minim][3], [Processing][4]

Running Example:
![Running Example](http://i.imgur.com/pPOdEoA.png)

Explanation
-----------
+ Small circle on the left of the windows are the characters in Star Wars.
+ Whenever a character is added to the network, it will show all relationships between other characters in network.
+ When two characters interactive in movie, there is a line to link them together.
+ More thick line, more times of interactions.

Operation
---------
+ You can press `Left Arrow Key` or `Right Arrow Key` to switch between episodes.
+ When you move your mouse to a character(small circles on the left), a popup message with name will show.
+ Drag a character into the circle to add it into network.
+ Drag a character out of the circle to remove it from network.
+ Press button `Add All` to add all characters to the network.
+ Press button `Clean All` to clean all characters from the network.

Visualization
-------------
+ When a character move into the network circle, its stroke weight increase.
+ The width of each link depends on times characters interactive in movie.
+ Use trigonometric functions to calculate character's position in network.
+ A nice song to enjoy(?).

[1]:http://benedikt-gross.de/libraries/Ani
[2]:http://www.sojamo.de/libraries/controlP5
[3]:http://code.compartmental.net/minim/index.html
[4]:http://processing.org
