Overview : 

This is an image processing program, that allows for someone to upload an image and apply a series
of operations to the image and then save it as a new image at a specific location in the computer.
It also allows for a text file containing commands to be uploaded and read from.

Operations Allowed:

- Applying an control.commands.filters.Sepia filter to an image.
- Applying an Greyscale filter to an image.
- Isolating the red-component in an image.
- Isolating the green-component in an image.
- Isolating the blue-component in an image.
- Representing an image using its luma component.
- Representing an image using its average color value.
- Representing an image using its maximum color value.
- Brightening or darkening an image by its increment.
- Loading an image to the model from a specific location.
- Saving an image at a specific location.
- Flipping an image on its vertical axis.
- Flipping an image on its horizontal axis.

Model :

The model contains a camera roll which is a Hashmap of images mapped to 
a string representing their names. Images can be added, removed or retrieved from the model.
Returns a boolean statement if the model contains a certain image.

Controller :

Allows for a model, appendable, and a readable to be added to the controller.
If not the default is System.out, System.in, and an empty model.
Maps each command to a string.
The main program uses a scanner to accept commands and then executes until the program is ended.
The controller also writes messages to the screen such as the homescreen message.

Command :
Is a command that can be applied to an image. It has an apply method that takes in a model and an 
string array of arguments. It creates a new image after the arguments have been applied.

model.Image :
Represents an model.Image with a certain height, with, and RGB data represented as a 3d array of RGB 
values at x,y positions. Allows for height and width as well as data at a certain position to be
 retrieved. Allows for all the data at once to be set or retrieved. Allows for data to be loaded 
into the image from a certain file location. It also allows for the name of an image to be set and 
retrieved.



Provided PPM Images :
Obtained from https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html 
which is from a professor's website at Florida State and are distributed under the GNU LGPL license.

Provided PNG Images:
Obtained from Pixabay.com which provides copyright free png images that don't require attribution.

Provided JPEG Images:
Obtained from Pexels.com which provides copyright free jpeg images that don't require attribution.

Provided BPM Images:
Obtained from Unsplash.com which provides copyright free jpeg images that don't require attribution.
The images were then converted to bmp on cloudconvert.com.

The following are acceptable commands that can be inputted into the command line for this program : 

load C:\Users\user\IdeaProjects\group\Assignment4\PPMFiles\Snail.ppm Snail

red-component Snail RedSnail

vertical-flip RedSnail FlippedRedSnail

brighten 10 Snail BrightenedSnail

sepia BrightenedSnail SepiaBrightenedSnail

save C:\Users\user\IdeaProjects\group\Assignment4\res\SepiaBrightenedSnail.ppm SepiaBrightenedSnail

save C:\Users\user\IdeaProjects\group\Assignment4\res\FlippedRedSnail.ppm FlippedRedSnail

Q