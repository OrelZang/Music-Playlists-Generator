# The Playlists Generator
![alt text](https://i.imgur.com/UX9EP4P.png)


## Table of Contents 
- [The Application](#the-application)  
- [The Data](#the-data)  
- [The Algorithms](#the-algorithms)
- [Implementation](#implementation)
- [screenshots](#screenshots)


## The Application
This application is a music playlists generation system.  The user is able to find and discover recommendations for new songs he might like based on songs he already enjoy or based on a few supplied preferences. The application will generate a music playlist based on the given song or based on the supplied preferences. In order to use the application the user must first register an account using the application's registration form. After registration, the user will be able to sign in into his account in the main page. Once signed in, the user can generate new playlsits. The playlist generation algorithm comes in two flavors:

- **Song based generation:** the user can choose his favorite song and the application will generate a playlist of songs which best matches the supplied song audio features (e.g., tempo, loudness, etc). 

- **Preferences based generation:** the user can choose if he wants happy or sad songs, the desired tag for the songs (e.g., rock, pop, jazz, etc)  and the application will find him the song in the database which best matches his preferences.

For each generated playlist, the user can save it for later revisiting. Additionally, the user can see some interesting statistics details about each of the saved playlists: artists distribution, decades distribution, genres distribution, tempo progression, loudness progression and artists familiarity.


## The Data
In order to develop a music playlists generation system we leveraged the "Million Song Dataset", which is a freely-available collection of audio features and metadata for a million contemporary popular music tracks. The core of the dataset is the feature analysis and metadata for one million songs, provided by The Echo Nest. Each song in the dataset contains many audio features such as the song's loudness, energy, key, tempo and much more. We developed an algorithm which ranks songs' similarity based on these features and then use this ranking in order to generate a playlist based on a given song or based on preferences. We determined, based on our basic music theory knowledge, which of the many features supplied by the Million Song Dataset will be the most beneficial for our algorithm and narrowed down the dataset to only these features.

The dataset files come in HDF5 format, which cannot be easily migrated into mysql databases. For this reason, we have implemented a python script which reads these files using the supplied wrappers by the "Million Song Dataset" and saves the required data in txt files. Then, using the LOAD TABLE SQL statement we were able to load these txt files into our database.


## The Algorithms
![alt text](https://i.imgur.com/zreviT0.png)

Algorithm 1 shows the algorithm of the song based playlist generator. It takes as input a reference song and the number of songs required to be in the generated playlist. First, the algorithm gets a candidate songs list based on the given song. Specifically, using a SQL query statement we get a list of artists which are considered similar to the artist of the reference song, we get these artists' songs and keep only those songs which have the same mode and time signature as the reference song. Then, for each of these candidate songs we calculate the "similarity score". This score is based on the tempo distance and loudness distance between the reference song and the current song. This score is also effected by the familiarity of the current song as we want to push songs with more familiar artists to the top. Then, we sort in decrease order the candidate songs list based on the similarity score. Finally, we iterate on the sorted candidate songs list and add the first k songs in it (based on the numberOfSongsInPlaylist parameter) to the generated playlist and return this playlist in the end.

![alt text](https://i.imgur.com/HMX11m8.png)
Algorithm 2 shows the algorithm of the preferences based playlist generator. It takes as input the required genre, the required happiness value (which ranges from -100 as the saddest to 100 as the happiest) and the number of songs required to be in the generated playlist. Overall this algorithm is similar to the previous one except for these changes: 1) The candidate songs are the ones which has the required genre tag associated with them.  2) The candidate songs list is sorted by the happiness score which is calculated proportionally to the song's tempo and loudness. 3) Instead of taking the first k songs in the candidate songs list we are taking the k songs which are roughly the %requiredHappiness of the candidate songs list.


## Implementation
The application is implemented in the Java programming language using the MVC (Model-View-Controller) architectural design pattern. JavaFX was used as the platform for the GUI building. 

In our implementation we have used the following packages:
- **[JDBC](http://www.oracle.com/technetwork/java/javase/jdbc/index.html)** - JDBC was used as the API for accessing the database in the application.
- **[JFoenix](http://www.jfoenix.com/)** - JFoenix is an open source Java library, that implements Google Material Design using Java components. This was used for our GUI elements.
- **[FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx)** - FontAwesomeFX is an open source Java library, which gives you scalable vector icons that can be customized. This was used for our GUI design.


## Screenshots
![alt text](https://i.imgur.com/xMdamaR.png)
![alt text](https://i.imgur.com/xBPQJIO.png)
![alt text](https://i.imgur.com/VNzB3Tg.png)
![alt text](https://i.imgur.com/8iaBW7M.png)
![alt text](https://i.imgur.com/RctMgG9.png)
![alt text](https://i.imgur.com/OiUYion.png)
![alt text](https://i.imgur.com/RCb6amT.png)
![alt text](https://i.imgur.com/njYDOQu.png)
![alt text](https://i.imgur.com/c3ftKsr.png)
![alt text](https://i.imgur.com/nUEBNYp.png)
