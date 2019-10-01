**README**

This is the repository for the main "smart" elements of my Google maps clone, which were entirely composed by me. This is an academic project that was apart of UC Berkeley's 61B. Most of the frontend elements were developed by UC Berkeley while I provided methods that figure out what data to display to the browser.

Uploaded files are one's I created from scratch.
In addition to the files posted, I implemented methods that were responsible for rastering images and determining which data to server the front-end.

[Map App](http://mapsproj.herokuapp.com) (Using free service)

***Functionality I provided***
 - Map rastering: Given coordinates of a region and a window size, determine the correct images and resolutions to serve up.
 - Routing: Given start and end longitute and latitudes, determine the street directions for the trip. 
 - Autocomplete: Given a string, return all locations that match that string and substring. 
 
 **Directions of Use**
  - In order to display a route between two points simply double click on the start location and then double click again on your 
    destination
  - To search for places begin typing a name. Names must start with capital letters and after two letters are typed the        autocomplete mechanism will show you all places beginning with those letters
