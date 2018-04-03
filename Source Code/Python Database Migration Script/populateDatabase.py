"""
Tutorial for the Million Song Dataset

by Thierry Bertin-Mahieux (2011) Columbia University
   tb2332@columbia.edu
   Copyright 2011 T. Bertin-Mahieux, All Rights Reserved

This tutorial will walk you through a quick experiment
using the Million Song Dataset (MSD). We will actually be working
on the 10K songs subset for speed issues, but the code should
transpose seamlessly.

In this tutorial, we do simple metadata analysis. We look at
which artist has the most songs by iterating over the whole
dataset and using an SQLite database.

You need to have the MSD code downloaded from GITHUB.
See the MSD website for details:
http://labrosa.ee.columbia.edu/millionsong/

If you have any questions regarding the dataset or this tutorial,
please first take a look at the website. Send us an email
if you haven't found the answer.

Note: this tutorial is developed using Python 2.6
      on an Ubuntu machine. PDF created using 'pyreport'.
"""

# usual imports
import os
import sys
import time
import glob
import datetime
import sqlite3


# path to the Million Song Dataset subset (uncompressed)
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msdPath='C:\Users\Orel\Desktop\Orel\Database Workshop Project\Database\MillionSongSubset'
msdDataPath=os.path.join(msdPath,'data2')
msd_subset_addf_path=os.path.join(msdPath,'AdditionalFiles')
assert os.path.isdir(msdPath),'wrong path' # sanity check
# path to the Million Song Dataset code
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msd_code_path='/'
assert os.path.isdir(msd_code_path),'wrong path' # sanity check
# we add some paths to python so we can import MSD code
# Ubuntu: you can change the environment variable PYTHONPATH
# in your .bashrc file so you do not have to type these lines
sys.path.append( os.path.join(msd_code_path,'PythonSrc') )

# we add some paths to python so we can import MSD code

songsTableFile = open('songs_table.txt','w')
artistsTableFile = open('artists_table.txt','w')
artistsTagsTableFile = open('artists_tags_table.txt','w')
similarArtistsTableFile  = open('similar_artists_table.txt','w')
tagsTableFile = open('tags_table.txt','w')

maximumArtistNameLen=0
maximumArtistTagLen=0
maximumSongNameLen=0
maximumAlbumNameLen=0

# imports specific to the MSD
import hdf5_getters as GETTERS
# imports specific to the MSD


# the following function simply gives us a nice string for
# a time lag in seconds
def strtimedelta(starttime,stoptime):
    return str(datetime.timedelta(seconds=stoptime-starttime))

# we define this very useful function to iterate the files
def apply_to_all_files(basedir,func=lambda x: x,ext='.h5'):
    """
    From a base directory, go through all subdirectories,
    find all files with the given extension, apply the
    given function 'func' to all of them.
    If no 'func' is passed, we do nothing except counting.
    INPUT
       basedir  - base directory of the dataset
       func     - function to apply to all filenames
       ext      - extension, .h5 by default
    RETURN
       number of files
    """
    cnt = 0
    # iterate over all files in all subdirectories
    counter=0
    for root, dirs, files in os.walk(basedir):
        files = glob.glob(os.path.join(root,'*'+ext))
        # count files
        cnt += len(files)
        # apply function to all files
        for f in files :
            func(f)
            counter+=1
            # if counter == 1000:
            #     return

        # if counter == 1000:
        #     return
    return cnt

# we can now easily count the number of files in the dataset
print 'number of song files:',apply_to_all_files(msdDataPath)

# let's now get all artist names in a set(). One nice property:
# if we enter many times the same artist, only one will be kept.
artistsIDsSet = set()
artistsNamesSet=set()
similarArtistsPairsList=set()
tagsSet=set()
allowedTagsSet={"acid rock","bluegrass","blues","classical","country","disco","dubstep","electronic","folk","funk","hard rock","heavy metal",
                "hip hop","hip-hop","hiphop","jazz","latin","latin pop","metal","pop","punk","r b","rap","reggae","rnb","rock","rock and roll",
                "rock n roll","rock roll","salsa","soul","techno"}

# we define the function to apply to all files

def writeSingleHDF5FileToTxtFile(songHDF5FileName):
    global maximumArtistNameLen
    global maximumArtistTagLen
    global maximumSongNameLen
    global maximumAlbumNameLen

    """
    This function does 3 simple things:
    - open the song file
    - get artist ID and put it
    - close the file
    """
    songHDF5File = GETTERS.open_h5_file_read(songHDF5FileName)

    songID= GETTERS.get_song_id(songHDF5File)
    songName = GETTERS.get_title(songHDF5File)
    artistID= GETTERS.get_artist_id(songHDF5File)
    songAlbum =GETTERS.get_release(songHDF5File)
    songYear=GETTERS.get_year(songHDF5File)
    songTempo=GETTERS.get_tempo(songHDF5File)
    songDanceability=GETTERS.get_danceability(songHDF5File)
    songDuration = GETTERS.get_duration(songHDF5File)
    songEnergy=GETTERS.get_energy(songHDF5File)
    songKey=GETTERS.get_key(songHDF5File)
    songLoudness=GETTERS.get_loudness(songHDF5File)
    songMode=GETTERS.get_mode(songHDF5File)
    songTimeSignature=GETTERS.get_time_signature(songHDF5File)


    songsTableFile.write(songID+"\t"+songName+"\t"+artistID+"\t"+songAlbum+"\t"+str(songYear)+"\t"+str(songTempo)+"\t"+str(songDanceability)+"\t"+str(songDuration)+"\t"+str(songEnergy)+"\t"+str(songKey)+"\t"+str(songLoudness)+"\t"+str(songMode)+"\t"+str(songTimeSignature)+"\t\n")



    artistName = GETTERS.get_artist_name(songHDF5File)
    artistFamiliarity = GETTERS.get_artist_familiarity(songHDF5File)
    artistTagsArray = GETTERS.get_artist_mbtags(songHDF5File)

    artistsTableFile.write(artistID+"\t"+artistName+"\t"+str(artistFamiliarity)+"\t\n")

    if len(songName) > maximumSongNameLen:
        maximumSongNameLen = len(songName)

    if len(songAlbum)>maximumAlbumNameLen:
        maximumAlbumNameLen=len(songAlbum)

    if len(artistName) > maximumArtistNameLen:
        maximumArtistNameLen = len(artistName)
    

    for artistTag in artistTagsArray:
        if artistTag in allowedTagsSet:

            artistsTagsTableFile.write(artistID + "\t"+artistTag+"\t\n")
            if artistTag not in tagsSet:
                tagsTableFile.write(artistTag+"\t\n")
                tagsSet.add(artistTag)
            if len(artistTag)>maximumArtistTagLen:
                maximumArtistTagLen=len(artistTag)


    similarArtists= GETTERS.get_similar_artists(songHDF5File)


    for similarArtist in similarArtists:
        similarArtistsPairsList.add((artistID,similarArtist))



    artistsIDsSet.add(artistID)
    artistsNamesSet.add(artistName)

    songHDF5File.close()





# let's apply the previous function to all files
# we'll also measure how long it takes
t1 = time.time()
apply_to_all_files(msdDataPath, func=writeSingleHDF5FileToTxtFile)
t2 = time.time()
print 'all artist names extracted in:', strtimedelta(t1, t2)


similarArtistsWithoutDuplicatesList=set(tuple(sorted(p)) for p in similarArtistsPairsList)
print len(similarArtistsPairsList)
print len(similarArtistsWithoutDuplicatesList)

for pair in similarArtistsWithoutDuplicatesList:
    if(pair[0] in artistsIDsSet and pair[1] in artistsIDsSet):
        similarArtistsTableFile.write(pair[0]+"\t"+pair[1]+"\t\n")


songsTableFile.close()
artistsTableFile.close()
artistsTagsTableFile.close()
similarArtistsTableFile.close()
tagsTableFile.close()

print len(artistsIDsSet)
print len(artistsNamesSet)

print "maximum artist name len: "+str(maximumArtistNameLen)
print "maximum artist tag len: "+str(maximumArtistTagLen)
print "maximum song len: "+str(maximumSongNameLen)
print "maximum album len: "+str(maximumAlbumNameLen)