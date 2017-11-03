# Koebe-Lib Development Environment

The purpose of this library is to support the creation of geometric constructions in the inversive
geometry of circles on the 2-sphere. The design of the code base was heavily influenced by Sherif Ghali's [Introduction to Geometric Computing](http://www.springer.com/us/book/9781848001145). This is an excellent reference for anyone interesting in coding a geometric library and gives a good overview of the various design decisions involved. Ghali also gives plenty of companion C++ code, which we referred to for coding several of the operations on various geometric objects and the structure of the `geometry.primitives` package is in line with Ghali's suggestion for how to structure such libraries. 

# App 

The code base also has one application (well, there are a few dummy ones that I need to remove/clean up) that is a first version of what I hope will eventually become a [GeoGebra](http://geogebra.org)-like application for inversive geometric constructions. That is the `SphericalSketch` found in the `sketches` package. This application loads a little bare-bones Python IDE and 3D view that allows a user to create and visualize geometric constructions on the fly. Eventually we will post some sample scripts somewhere in this repository. 

# Codebase 

This is a _very_ alpha-level release. I'm just getting started with this code base, and have some ideas for applications to build on top of it, but I decided to go ahead and release it, because some others are starting to use the code base and thus there needs to be a public repository.  

The codebase is written in Kotlin and is developed in JetBrain’s IDEA IntelliJ. 

Here is a set-up guide for getting a development environment up and running. 

The codebase has some weird dependencies. Specifically you will need an installation
of [the Processing IDE](http://processing.org) from which you will extract Processing's 
`core.jar` and the appropriate JOGL jar files for your environment. You will also need 
several other 3rd party libraries: CirclePack, Jython, and RSyntaxTextArea as detailed below. 

I have also included an ArcBall class that was converted to Kotlin and lightly modified. The 
original can be found [here](https://github.com/RandomEtc/processing-arcball/).  


# TODO

* A big todo is to set up some sort of build environment that works cross platform and deals correctly with the appropriate JOGL libraries for each platform. I don't have time to figure this out, but if someone sees this and is interested, please help. 
* The setup guide below is written entirely from the Mac OS X point of view. I could certainly use help in revising it for other platforms. 

# Setting up the dev-environment. 

## Step 1: New IntelliJ Project

1. Create a new project. 
2. Select “Kotlin” in the left pane and “Kotlin (JVM)” and click “Next”. 
3. Name the project whatever you want, like “Koebe-Lib” and click “Finish.”

## Step 2: Obtain source code from the git. 

1. In a Terminal window, navigate to the Kotlin project you just created. 
2. From the top-level project directory run the following git command: 
```
git clone https://github.com/johncbowers/KoebeLib.git src/
```

## Step 3: Processing Library
_Mac OS X_: (Note: this assumes you have Processing.app in /Applications)

1. Open Project Structure dialog (Command+;)
2. Click "Modules"
3. Click on the Dependencies Tab
4. Click "+" and select "Jars"
5. Navigate to /Applications/Processing.app/Contents/Java/core/library and select core.jar, jogl-all.jar, gluegen-rt.jar, and the jogl-all-natives-YOURSYSTEM.jar and gluegen-rt-natives-YOURSYSTEM.jar
6. Click the "Export" checkbox. 
7. Click "Apply" and "OK". 

## Step 4: CirclePack Library
1. Download CirclePack-J2.8.jar from [here](http://www.math.utk.edu/%7Ekens/CirclePack/downloads/).
2. Rename it CirclePack-J2.8.zip
3. Unzip it. 
4. Extract the `cpcore.jar` file
5. Add the `cpcore.jar` file to your IntelliJ project settings just like the Processing Jars above. 

## Step 5: Jython Library
1. Download the Jython standalone jar from [here](http://www.jython.org/downloads.html). As of this writing we use Jython 2.7.0. 
2. Add the Jar file to your project settings as above. 

## Step 6: RSyntaxTextArea
1. Git clone the repository from [here](https://github.com/bobbylight/RSyntaxTextArea).
2. Run `chmod +x gradlew` then run `./gradlew build` to build it
3. Add the `rsynctextarea-X.X.X-XYZ.jar` from `RSyntaxTextArea/build/libs` to your project settings as above. 

## Step 7: Create a Runtime Configuration. 
1. Right click on the the SphericalSketch.kt file.
2. Select the Run option.

#koebe-lib