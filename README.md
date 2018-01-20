# Koebe-Lib Development Environment

The purpose of this library is to support the creation of geometric constructions in the inversive
geometry of circles on the 2-sphere. The design of the code base was heavily influenced by Sherif Ghali's [Introduction to Geometric Computing](http://www.springer.com/us/book/9781848001145). This is an excellent reference for anyone interesting in coding a geometric library and gives a good overview of the various design decisions involved. Ghali also gives plenty of companion C++ code, which we referred to for coding several of the operations on various geometric objects and the structure of the `geometry.primitives` package is in line with Ghali's suggestion for how to structure such libraries. 

# App 

The code base also has one application (well, there are a few dummy ones that I need to remove/clean up) that is a first version of what I hope will eventually become a [GeoGebra](http://geogebra.org)-like application for inversive geometric constructions. That is the `SphericalSketch` found in the `sketches` package. This application loads a little bare-bones Python IDE and 3D view that allows a user to create and visualize geometric constructions on the fly. Eventually we will post some sample scripts somewhere in this repository. 

# Codebase 

This is a _very_ alpha-level release. I'm just getting started with this code base, and have some ideas for applications to build on top of it, but I decided to go ahead and release it, because some others are starting to use the code base and thus there needs to be a public repository.  

The codebase is written in Kotlin and is developed in JetBrain’s IDEA IntelliJ. 

Here is a set-up guide for getting a development environment up and running. 

The codebase has some weird dependencies.  
They are all managed with Gradle and do not need to be retreived manually.

- cpcore.jar 2.8 - Extracted from CirclePack [link](http://www.math.utk.edu/%7Ekens/CirclePack/downloads/)
- jython-standalone 2.7.0 - [link](http://www.jython.org/downloads.html)
- rsyntaxtextarea 2.6.1 - [link](https://github.com/bobbylight/RSyntaxTextArea)
- jogl-all-main 2.3.2 - need native libraries - [link](http://jogamp.org/wiki/index.php/Release_2.3.2)
- gluegen-rt-main 2.3.2 - need native libraries - [link](http://jogamp.org/wiki/index.php/Release_2.3.2)
- processing-core 3.3.6 - [link](https://processing.org/download/)

I have also included an ArcBall class that was converted to Kotlin and lightly modified. The 
original can be found [here](https://github.com/RandomEtc/processing-arcball/).  

# Setting up the dev-environment.

Dependency gathering and building is done with Gradle.  
A local installation can be used, or a Gradle wrapper is available in the bin directory.  
To use the wrapper substitute `../bin/gradlew` for `gradle` in the steps below.

## Method 1: IntelliJ Project

The goal of this method is to easily retrieve all dependency JARs and register them as dependencies in the IntelliJ project.
Building and Running is all done through IntelliJ's systems.

### Step 1: Download dependencies
1. Clone the repo
2. In the `lib` directory run `gradle all`
3. This will download all dependencies to `<project-path>/lib/jar`

### Step 2: Create a new IntelliJ Project
1. Create a new project
2. Select 'Kotlin' in the left pane and 'Kotlin (JVM)' and click 'Next'
3. Name the project whatever you want, like 'Koebe-Lib'
4. Change the project location to the cloned repo and click 'Finish'

### Step 3: Add dependencies to project
1. Open Project Structure dialog (Command+; or Ctrl+Shift+Alt+S)
2. Click 'Modules' and open the Dependencies tab
3. Click '+' and select 'JARs or directories...'
4. Select the `<project-path>/lib/jar` folder
5. Click the 'Export' checkbox and click 'Apply' and 'OK'

### Step 7: Create a runtime configuration.
1. Right click on the the `src/sketches/SphericalSketch.kt` file
2. Select the Run option

## Method 2: Standalone Gradle build

The goal of this method is to allow for building and running without using IntelliJ.

### Configuration
All standalone building is done in the builder directory.
Configuration options are available in `settings.gradle`

### Option 1: Build a JAR
1. From the `builder` directory run `gradle jar`
2. A standalone executable JAR will be built in `build/lib/koebelib.jar`
3. This can be run with `java -jar build/lib/koebelib.jar`

### Option 2: Package a Distribution
1. From the `builder` directory run `gradle build`
2. A tarball and a zip file distribution will be built in `build/distibutions/koebelib.tar|zip`

### Option 3: Install a Distribution
1. From the `builder` directory run `gradle install`
2. A distribution will be installed in `build/install/koebelib`
3. This can be run with the start script at `build/install/koebelib/bin/koebelib`

### Option 4: Just compile (cannot be run)
1. From the `builder` directory run `gradle compileKotlin`

#### Notes:
Because of how Gradle handles dependencies we cannot run the program from a gradle build
that has not been bundled in some form (ie options 1-3 above).
Attempting to use `gradle run` or other methods may result in missing native libraries.

#koebe-lib
