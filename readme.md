
## Jar Launcher ##

Allows user to select a jar and arguments. *saxBased branch* Reading jarlauncher options from an xml file.

## Motivation ##

We use a number of jars at work with differing arguments. I don't want so many shortcuts on my desktop.

## License ##

see License.md for license information

### FastArg format ###

(Provisional until I handle a format with higher information density, so I can leave descriptions.) Each line is treated as a data atom. Each prefix determines treatment:

* <v> jvm location
* <j> jar path
* <a> arguments for jar
