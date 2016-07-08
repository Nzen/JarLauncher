
## Jar Launcher ##

Allows user to select a jar and arguments. Able to build the selection from either a fastArg file or xml that conforms to config.dtd .

### Motivation ###

We use a number of jars at work with differing arguments. I don't want so many shortcuts on my desktop. Hence, I'd rather press 2 and 5. Plus, this was an opportunity to learn one of java's xml parsing api`s. A little later I intend to use it to learn how to make a javafx gui.

### License ###

see License.md for license information. Based off of Fair License

## Usage ##

(Build a jar from the repo.) Copy the jar somewhere. Create either an xml or fastArg file that identifies paths to your jre, the jars to launch, and some options to send when launching them. (Or edit either config file.) Launching the jar with no arguments will look for config.xml in the working directory. JarLauncher accepts a path to either type of file, should you want it elsewhere.

### Xml format summary ###

The real grammer is in config.dtd .

Basically, jl_options, the root element, expects some locations and maybe an argBundle. At least one location should be where your jre lives. The others can provide relative or absolute paths and an optional description. An argBundle has (potentially) a summary, some flags, and a needsIo element. Including the latter prompts JarLauncher to redirect io of the new process to its output.

### FastArg format ###

Lightweight initial format. Each line is treated as a data atom. Each prefix determines treatment:

* &lt;v&gt; jvm location
* &lt;j&gt; jar path
* &lt;a&gt; arguments for jar

I assemble it in a naive fashion, so this format can no longer communicate that it needs to output the chosen jar's stream. I consider it deprecated.










