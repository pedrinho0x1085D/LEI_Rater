# LEI_Rater
![Android Logo](http://i.imgur.com/WjV4vQY.png)

Difficulty Rater of Pedestrian Tracks, based on a Machine Learnig Approach; Supported by https://github.com/pedrinho0x1085D/WebService 

## Features:
* __Data obtaining module__ : In this module, users can register the difficulty sensed on a certain part of the path. It generates a CSV file, saved in an hidden folder for future synchronization with server. Data synchronization is not mandatory but recommended. On successful synchronization, the generated CSV files are erased.
<img src="http://i.imgur.com/GIGBpyL.png" width="256" height="400">
* __Local User Managment__ : It was preferred not to allocate additional centralized structures to manage the users. With this said, users are to be managed on each device. It's also possible to test the server connection status on the User Options Menu.

<img src="http://i.imgur.com/UYU1NJc.png" width="256" height="400">
<img src="http://i.imgur.com/c4ASKAZ.png" width="256" height="400">
<img src="http://i.imgur.com/PIc0LQG.png" width="256" height="400">
<img src="http://i.imgur.com/rG0ffKS.png" width="256" height="400">
* __Evaluation and Map__ : For this module to work is required a GPX file with the pints of a pedestrian track, the date, time and load expected for the practice. User parameters are automatically added to the query built and sent to the server. On a sucessful query result, it's obtained an object with a list complying of Starting Latitude, Starting Longitude, Ending Latitude, Ending Longitude, Difficulty. These objects will be used to draw the map of the track, marking the difficulty.

<img src="http://i.imgur.com/5hfyZ6q.png" width="256" height="400">
<img src="http://i.imgur.com/ESOlbaI.png" width="256" height="400">
