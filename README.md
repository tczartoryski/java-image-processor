# ImageProcessor
## Program overview
The ImageProcessor represents a fully-functioning image manipulation application written in Java. This application uses image processing techniques such as applying **convolution matrices** to blur and sharpen images, **bilinear interpolation** to downscale images, and **component analysis** of an images color channels.
This application also uses **MVC design pattern** and **SOLID design principles** in its structure.

## Features:
* Brightening and darkening images
* Applying gaussian blur and sharpening images
* Visualizing the red, green, blue, value, intensity, and luma of an image
* Flipping images horizontally and vertically
* Downscaling images
* Applying sepia and grayscale color transformations
* Histogram displaying the frequency of colors in an image.
* Handles JPG, PNG, BMP, and PPM formats

## Design
This application employs an MVC (Model , View, Controller) design pattern each within its own package.This application also uses SOLID design principles which are expanded upon below.

### Single Responsibility Principle (SRP)
Each class and interface in your design has a single responsibility
* Ex . PPMImageFormat: Handles reading and writing of PPM files.
* Ex. AbstractImageProcessorView: Provides common functionality for views.
* Ex. Operation: Defines a function object for image operations with each implementation representing a specific operation

### Open/Closed Principle (OCP)
Design is open for extension but closed for modification:
* Ex. New operations (e.g., sepia, greyscale, sharpen, blur) can be added to the operationDirectory Map without modifying the core logic of the controller.
* Ex. Abstract classes and interfaces (e.g., AbstractImageProcessorView, ImageProcessorGUIController) allow for new features to be added by extending these classes or implementing these interfaces.

### Liskov Substitution Principle (LSP)
Design ensures that subclasses or implementations can replace their parent class or	interface without altering the correctness of the program:
* Ex. PPMImageFormat can be substituted with any other implementation of ImageFormat (e.g., PNGImageFormat) without affecting the controller's behavior.
* Ex. Operations like BrightenOrDarken, FlipHorizontal, and FlipVertical all implement the Operation interface and can be used interchangeably without affecting the application's correctness.

### Interface Segregation Principle (ISP)
Design contains smaller, more specific interfaces:
* Ex. ImageProcessorView and ImageProcessorGUIView are separate interfaces for different types of views, ensuring that implementations only need to provide relevant methods.
* Ex. ImageProcessorViewModel and ImageProcessorModel differentiate between the methods needed for the view and the full set of methods in the model, ensuring that the view only depends on the methods it actually needs.

### Dependency Inversion Principle (DIP)
Design ensures that high-level modules do not depend on low-level modules but on abstractions:
* Ex. The controller depends on the ImageFormat interface, not on concrete implementations like PPMImageFormat. The controller also depends on the ImageProcessorView and ImageProcessorModel interfaces, not on their concrete implementations.
* Ex. The Utils interface and its implementation (UtilsImpl) abstract utility methods, so high-level components can use these utilities without depending on specific implementations.

