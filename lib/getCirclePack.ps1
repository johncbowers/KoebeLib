Invoke-WebRequest -OutFile pack.zip http://www.math.utk.edu/%7Ekens/CirclePack/downloads/oldversions/CirclePack-J2.8.jar
Expand-Archive -DestinationPath . pack.zip
Remove-Item pack.zip
Remove-Item -Recurse META-INF
Remove-Item -Recurse runCirclePack
