JFLAGS = -g 
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Main.java systeme_fichier/*.java fileparser/*.java commandes/*.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class systeme_fichier/*.class fileparser/*.class commandes/*.class

