# The Playlists Generator
![alt text](https://i.imgur.com/UX9EP4P.png)


## The Application
This application is a music playlists generation system.  The user is able to find and discover recommendations for new songs he might like based on songs he already enjoy or based on a few supplied preferences. The application will generate a music playlist based on the given song or based on the supplied preferences. In order to use the application the user must first register an account using the application's registration form. After registration, the user will be able to sign in into his account in the main page. Once signed in, the user can generate new playlsits. The playlist generation algorithm comes in two flavors:

**Song based generation:** the user can choose his favorite song and the application will generate a playlist of songs which best matches the supplied song audio features (e.g., tempo, loudness, etc). 

**Preferences based generation:** the user can choose if he wants happy or sad songs, the desired tag for the songs (e.g., rock, pop, jazz, etc)  and the application will find him the song in the database which best matches his preferences.

For each generated playlist, the user can save it for later revisiting. Additionally, the user can see some interesting statistics details about each of the saved playlists: artists distribution, decades distribution, genres distribution, tempo progression, loudness progression and artists familiarity.
