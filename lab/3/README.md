# Laboratorio 3: Informe grupal

## Integrantes

- [Pedro Salas Piñero ](pedrosalaspinero@mi.unc.edu.ar)
- [Juan Pablo Ludueña Zakka ](juan.luduena.161@mi.unc.edu.ar)
- [Ebner Lautaro](lautaro.ebner@mi.unc.edu.ar)

## Implementaciones previas

Para ver cual de las implementaciones usabamos, nos fijamos en cómo deberiamos modificar cada codigo para poder implementar la nueva funcionalidad y para esto, necesitabamos saber dónde debiamos tocar. 

Es por esto que nos enfocamos en el metodo `main` del FeedReader. Vimos, que cada uno lo habia hecho con una estrategia distinta. En el codigo de Lautaro Ebner, vimos que habia un paso, en el que ya se tenian paralelizados los Articulos. Esto nos llevo a decidir que su codigo seria el que mas se prestaba a la nueva funcionalidad.

Como no estabamos muy seguros de esta decision, en paralelo, fuimos probando con los otros dos codigos, pero finalmente confirmamos que era mas facil de implementar y el codigo quedaba más ordenado.

## Sobre la resolucion de la consigna

Para implementar la solucion, lo hicimos con una nueva opcion `-s` que puede ser usada tanto con la opcion `-ne` como sin esa opcion. De esta forma, se calculen las NamedEntity o no, podemos ordenar los articulos segun un termino de busqueda. Cabe aclarar que tomamos la decision de restringir el termino de busqueda a una sola palabra, que en el texto original se encuentra entre espacios en blanco, por lo que nuestra solucion no se asemeja a un buscador web en ese sentido.


Si se corre con la opcion de busqueda de palabra, por consola va a pedir un termino de busqueda. Este termino lo usaremos para contabilizar cuantas veces aparece en cada articulo (tanto titulo como cuerpo). Para hacer este calculo, cortamos el procesamiento de Feeds que Lautaro usa en su codigo en dos partes, dejando un `JavaRDD<Article>` intermedio. Con este RDD, hicimos un uso sencillo del esquema map-reduce para filtrar, contabilizar y finalmente ordenar los articulos segun el termino de busqueda.  

Para contabilizar las palabras encontramos que, al contrario de lo que sugeria la consigna, usar tanto metodos propios de java (por ejemplo `String::split`) o de spark (por ejemplo `sortBy`) es mejor que generar o usar una tabla de indice invertido. En este sentido tratamos de no reinventar la rueda.

