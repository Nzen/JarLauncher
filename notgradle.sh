#! /bin/sh

/usr/lib/jvm/java-10-openjdk-amd64/bin/javac \
	-d mods/ws.nzen.jarl \
	src/main/java/ws.nzen.jarl/module-info.java \
	src/main/java/ws/nzen/jarl/JarLauncher.java src/main/java/ws/nzen/jarl/Launcher.java \
	src/main/java/ws/nzen/jarl/model/ArgBundle.java src/main/java/ws/nzen/jarl/model/JarLocation.java src/main/java/ws/nzen/jarl/model/JarModel.java src/main/java/ws/nzen/jarl/model/SelfDescribes.java \
	src/main/java/ws/nzen/jarl/parser/ConfigParser.java src/main/java/ws/nzen/jarl/parser/ParserFactory.java src/main/java/ws/nzen/jarl/parser/XmlBasedParser.java \
	src/main/java/ws/nzen/jarl/ui/CliSelection.java src/main/java/ws/nzen/jarl/ui/SelectionUi.java

/usr/lib/jvm/java-10-openjdk-amd64/bin/jar --create --file=mlib/ws.nzen.jarl.JarLauncher@2.0.jar --module-version=1.0 --main-class=ws.nzen.jarl.JarLauncher -C mods/ws.nzen.jarl .
