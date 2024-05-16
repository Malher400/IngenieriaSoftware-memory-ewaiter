Grupo eWaiter | Proyecto Memory

# INSTRUCCIONES PARA EJECUTAR EL JUEGO MEMORY

1) Se debe importar el proyecto en Eclipse. El proyecto contiene las siguientes carpetas, que deben estar todas EN LA MISMA CARPETA DEL PROYECTO:

	- `src` ->  Incluye el código del programa, con los paquetes y las clases correspondientes
	- `icons` -> Incluye todas las imágenes de las que se hace uso en el programa
	- `lib` -> Incluye la librería json.jar, que se debe importar en el proyecto. Este paso se explica más adelante
	- `music` -> Incluye los archivos de música que emplea el juego
	- `resources` -> Incluye otros archivos de los que hace uso el programa
	- `test` -> Incluye los tests unitarios de JUnit
	- Además, se deben incluir los archivos `ARCADECLASSIC.TTF` y `PressStart2P-Regular.TTF`, utilizados para las fuentes de letra empleadas en el juego

2) Una vez importadas todos estos archivos en la carpeta del proyecto, se debe importar la librería `json.jar` para la correcta compilación y ejecución del programa.
   Para ello seleccionamos el proyecto con el click derecho y, a continuación, seleccionamos Build Path -> Add External Archives.
   Esto abrirá el explorador de archivos, donde habrá que seleccionar dentro de la carpeta `lib` del proyecto el archivo `json.jar`.

3) Completados los pasos 1 y 2, sólo queda ejecutar el programa. Para esto seleccionaremos el proyecto con el click derecho y seleccionamos Run As -> Java Application.
   Esto es suficiente para que se abra la ventana dando comienzo al juego Memory.
