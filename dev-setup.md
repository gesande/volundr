###### Dev setup with Eclipse
- install eclipse (https://www.eclipse.org/downloads/packages/release/2020-03/r)
- build eclipse settings: `./gradlew eclipse`
- import the projects to eclipse

###### Dev setup with IDEA
- install IDEA (obviously)
- import as Gradle project from git root

###### Formatting etc
- use `./gradle spotlessApply` for formatting source code

###### Do I have the license to commit?
- use `./licenseToCommit.sh` for making sure basic stuff is covered

###### Checking dependencies
- use `./dependencyCheck.sh` to performa OWASP dependency check
- use `./dependencyUpdates.sh` to see if any dependency has new versions

###### Publishing
- use `./gradlew distributionPackage` to prepare distribution(s)
- use `./publishToMavenLocal.sh` to publish local maven repository
