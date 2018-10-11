# Cyclone Prediction and Management

### Introduction

India is extremely vulnerable to natural hazards such as cyclones, earthquakes and floods. India's economy is vitally depenent on the occurences of these natural disasters; as such, their prevention is of utmost importance. Cyclones themselves, between 1980-2000, have affected 370 million people in India. Not only that, annually 308 cyclones are affecting the East Coast. India, being surrounded by vast oceans on the southern side, is highly susceptible to attacks by cyclones.

### What we hope to achieve through our Application

Our solution to mitigate the damage of this natural disaster is to build an application (preferably android) that can predict the path of the hurricane and warn the users of potential life threats before the hurricane hits.

The brief working of this application is described here. The input to the application shall be the current location of the user, specifically his latitude and longitude. This information is sent to our Machine Learning Model. We plan to use Azure Machine Learning Service for this task. This model would have been trained on the past data of cyclones and their effects, collected over several years. The features of this model may include the change in latitute and the longitude of various cyclones on a daily basis. The model would then output the predicted path of the hurricane. We would then relay this information from the cloud to our application. We would like to show the probability of the cyclone affecting the current location of the person. If appropriate training data is available, we could also predict the amount of rainfall that the cyclone would bring at the user's current location.The machine learning model used would be trained using a multi-layered neural network. Based on the feasibility of the training time, the complexity of the architecture would be decided.

The application would also contain offline information of safety measures to be taken during any natural disaster. This feature is highly useful, and will allow people in a panicked situation to gain easy access to safety measures to be taken. The application will also contain all the emergency numbers required for immediate help. 


### Possible Extensions to this Application

Here we briefly describe the possible extensions to this application. A user can dynamically add a marker on the map to indicate potential hazards or access to resources which would be tremendously beneficial during the time of any natural disaster. This feature would also allow for upvotes by other users showing the validity of the marker. Attributes of the marker include availability of one or many of the following: 'food', 'water', 'shelter', 'medicine', 'need immediate help', 'danger here'.

Another extension that can be added is to obtain the shortest path between a cyclone-hit location and the nearest relief center. This would be done by representing each landmark as the nodes in a graph, and the safe roads as edges. We would then apply a shortest path algorithm (one of Dijkstra’s, Bellman Ford or Floyd Warshall’s Algorithm) to obtain the shortest route.

These extensions are not guaranteed to be present in the final application, and will be achieved if time permits. 
