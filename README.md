# The Playlists Generator
![alt text](https://i.imgur.com/UX9EP4P.png)

##### Table of Contents 
[Headers](#headers)  
[Emphasis](#emphasis)  


## The Application
This application is a music playlists generation system.  The user is able to find and discover recommendations for new songs he might like based on songs he already enjoy or based on a few supplied preferences. The application will generate a music playlist based on the given song or based on the supplied preferences. In order to use the application the user must first register an account using the application's registration form. After registration, the user will be able to sign in into his account in the main page. Once signed in, the user can generate new playlsits. The playlist generation algorithm comes in two flavors:

**Song based generation:** the user can choose his favorite song and the application will generate a playlist of songs which best matches the supplied song audio features (e.g., tempo, loudness, etc). 

**Preferences based generation:** the user can choose if he wants happy or sad songs, the desired tag for the songs (e.g., rock, pop, jazz, etc)  and the application will find him the song in the database which best matches his preferences.

For each generated playlist, the user can save it for later revisiting. Additionally, the user can see some interesting statistics details about each of the saved playlists: artists distribution, decades distribution, genres distribution, tempo progression, loudness progression and artists familiarity.


## The Data
In order to develop a music playlists generation system we leveraged the "Million Song Dataset", which is a freely-available collection of audio features and metadata for a million contemporary popular music tracks. The core of the dataset is the feature analysis and metadata for one million songs, provided by The Echo Nest. Each song in the dataset contains many audio features such as the song's loudness, energy, key, tempo and much more. We developed an algorithm which ranks songs' similarity based on these features and then use this ranking in order to generate a playlist based on a given song or based on preferences. We determined, based on our basic music theory knowledge, which of the many features supplied by the Million Song Dataset will be the most beneficial for our algorithm and narrowed down the dataset to only these features.

The dataset files come in HDF5 format, which cannot be easily migrated into mysql databases. For this reason, we have implemented a python script which reads these files using the supplied wrappers by the "Million Song Dataset" and saves the required data in txt files. Then, using the LOAD TABLE SQL statement we were able to load these txt files into our database.


## The Algorithms
