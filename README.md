#CandyAI<sup>&trade;</sup>
**Project Status: Initial Development**

**Support Status: None available**

##Introduction
**Welcome to Facial Recognition by Sarhad Salam and Alex Bogdan**

The application is being developed by Sarhad Salam and Alex Bogdan. Presently we are using Java version "1.8.0_101" and opencv 3.10.

Note: You do not need the .idea folder or CandyAI.iml as that is just IntellijIDE generated folder for developer support (as both os us use intellij, global settings help).g

##Image Database
There are lots of image databases out there, you can use your own dataset. Or download the same one we are using. 

We are using LFW (Labelled Faces in the Wild) it's a dataset available from University of Massachusetts. It is freely available, and contains over 13000 images. I am only using about <500 images. Please use as much suits your purpose.
 The link to the website: http://vis-www.cs.umass.edu/lfw/.
 
 Navigate to the downloads section, and down all images as gzipped tar. Extract the files. (Tip: extract only say names beginning with A, will save you time, processing power, and misery.)
 

##Purpose of application
The entire projected started as something silly, a database of faces. You search faces and it acts a lot like google image search, gives you information.

Primary objective of the project was to just test out the fun stuff such as, recognizing age, gender, color, ethnicity of the subject, and emotional status. 
 
##Setup
To run this application all you'd need is opencv 3.10.  The source files are located in *src/* and resources are located in *src/resources/*.

When compiling, we will try to use relative paths but sometimes if its more convenient to use a static path in a test build, and the project fails to compile/run. Please check *src/processing/fixDatabaseImages" for any static directory that's been set.

###Step 1 (Setting images to the resources folder and naming convention):
1. There is a class named fixDatabaseImages, open it and set the directory in which the images are contained. It does not matter if its in subdirectories, or extensions are different.
2. Please note, the destination directory should be made pre-existing and is not handled at code level.
3. All the images should be placed inside *{project_root}/src/main/resource/image*.
4. Compile and run fixDatabaseImages, it may take a few seconds to complete (considering the size of your test images.)

***I recommend using less than 1000 files, more than that and things become slow (in my computer atleast).***

###Step 2 (Still in progress)
1. Coming soon.




###For more details check the javadoc by cloning this repo.
##Contact Us
If you for some reason, want to update, fix, or improve anything or for any other reason. You can mention us on GitHub, and one of us will soon get to you.

##Disclaimer Notice
This project comes with no guarantee, and is still in early stages.
 
CandyAI<sup>&trade;</sup>
