# neasaa-utils
This project has following utilities 
- DateUtils
- FileUtils
- StringUtils
- ClassPathUtils
- BaseConfig

## Steps to build and publish the jar
1. Set the GITHUB_TOKEN environment variable. 
   * GITHUB_TOKEN can be generated by login to github -> setting -> Developers Setting -> Personal access tokens -> Generate new token
   *	Set GITHUB_TOKEN environment variable as 
   >	export TOKEN=31234234adef2323423423423423423
1. Build jar
	> ./gradlew clean jar -Pversion=<version>
1. Build and publish
	> ./gradlew clean jar publish -Pversion=<version>



## Consuming this package in other project as maven dependency
Define the repositories as follows:
``` 
repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.pkg.github.com/vijaygarry/neasaa-utils")
		credentials {
			username = "githubUserName"
			password = "githubtoken. Do not checking token in github."
		}
    }
}
```