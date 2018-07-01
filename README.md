# Koebe-Lib Development Environment

The purpose of this library is to support the creation of geometric constructions in the inversive geometry of circles on the 2-sphere.
The design of the code base was heavily influenced by Sherif Ghali's [Introduction to Geometric Computing](http://www.springer.com/us/book/9781848001145).
This is an excellent reference for anyone interesting in coding a geometric library and gives a good overview of the various design decisions involved.
Ghali also gives plenty of companion C++ code, which we referred to for coding several of the operations on various geometric objects and the structure of the `geometry.primitives` package is in line with Ghali's suggestion for how to structure such libraries.

# App

The code base also has one application (well, there are a few dummy ones that I need to remove/clean up) that is a first version of what I hope will eventually become a [GeoGebra](http://geogebra.org)-like application for inversive geometric constructions.
That is the `SphericalSketch` found in the `sketches` package.
This application loads a little bare-bones Python IDE and 3D view that allows a user to create and visualize geometric constructions on the fly.
Eventually we will post some sample scripts somewhere in this repository.

# Codebase

This is a _very_ alpha-level release.
I'm just getting started with this code base, and have some ideas for applications to build on top of it, but I decided to go ahead and release it, because some others are starting to use the code base and thus there needs to be a public repository.

The codebase is written in Kotlin and is developed in JetBrain’s IntelliJ IDEA.  
There are some weird dependencies, but they are all managed with Gradle and do not need to be retreived manually.

- cpcore.jar 2.8 - Extracted from CirclePack [link](http://www.math.utk.edu/%7Ekens/CirclePack/downloads/)
- jython-standalone 2.7.0 - [link](http://www.jython.org/downloads.html)
- rsyntaxtextarea 2.6.1 - [link](https://github.com/bobbylight/RSyntaxTextArea)
- jogl-all-main 2.3.2 - need native libraries - [link](http://jogamp.org/wiki/index.php/Release_2.3.2)
- gluegen-rt-main 2.3.2 - need native libraries - [link](http://jogamp.org/wiki/index.php/Release_2.3.2)
- processing-core 3.3.6 - [link](https://processing.org/download/)
- antlr4 4.7.1 - [link](https://www.antlr.org/download.html)

I have also included an ArcBall class that was converted to Kotlin and lightly modified. The
original can be found [here](https://github.com/RandomEtc/processing-arcball/).

# The dev-environment.

Building and run configuration is all done through Gradle.
For use with IntelliJ IDEA the project should be imported as a Gradle project and built/run using the Gradle tasks.
Debugging through IntelliJ IDEA works as normal for Gradle tasks.
Tasks can be run with a local installation of Gradle, or using the included Gradle wrapper.
To use the wrapper substitute `./gradlew` for `gradle` in the examples below.

### Configuration of run tasks
Configuration options are available in `settings.gradle`  
Run configurations for new main classes can be created there.

## Gradle tasks
Clone the repo. All Gradle tasks are run from the root of the repository.  
View available tasks with the `gradle tasks` command.  
The main tasks to use are described below.

### Compile Kotlin
Run `gradle compileKotlin`  
This compiles the source code with no additional actions taken.  
This can be useful when just checking for compiler errors, but is generally not needed.

### Generate ANTLR Grammar Sources
Run `gradle generateGrammarSource`  
This will run ANTLR to generate source files for grammars in the `src/main/antlr/main` directory.  
The files will be placed in `build/generated-src/antlr/main` if needed for reference. 
This task is automatically run when compiling to make the ANTLR source available to the build.

### Run the application
There are several run configurations available of the form `gradle run<class>`  
Each builds the application and runs their respective main class.

### Build a JAR for distribution
Run `gradle jar`  
A standalone executable 'fat' jar will be built in `build/lib/koebelib.jar`  
This jar should be suitable for distribution as it includes all dependencies.
It can be run with `java -jar build/lib/koebelib.jar`  
The main class is specified in `settings.gradle` or with the `-Pmain=<class>` argument.  

### Cleanup the build directory
Run `gradle clean`  
The build directory will be deleted.

## Using IntelliJ IDEA

1. Clone the repo.
2. Select import project in IntelliJ.
3. Select the `build.gradle` file from the cloned repo.
4. Configure import options.
    - Check 'Use auto-import'.
    - Uncheck 'Create separate module per source set'
    - Select 'Use default gradle wrapper' or 'Use local gradle distribution'
5. Open the Gradle tool window.
    - View > Tool Windows > Gradle
6. Select a run configuration
    - Tasks > koebelib > run\<class\>

**_Disclaimer_**  
Building the application and creating run configurations with IntelliJ's native tools may work.  
It is, however, not supported and is not guaranteed to work in all cases.

#koebe-lib

