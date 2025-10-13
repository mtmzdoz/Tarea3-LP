# Nombre del compilador
JAVAC = javac
JAVA = java

# Archivos fuente
SOURCES = Main.java Jugador.java NaveExploradora.java Zona.java ZonaVolcanica.java ZonaArrecife.java ZonaProfunda.java NaveEstrellada.java Oxigeno.java Item.java ItemTipo.java Vehiculo.java
# (agrega aquí todos los .java que uses)

# Compilación
CLASSES = $(SOURCES:.java=.class)

# Regla por defecto
all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<

run: all
	$(JAVA) Main

clean:
	rm -f *.class
