> ### ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo.

**Modulos:**
- Dibujos (Grilla, Escher):

    Estos modulos son la puesta en uso de la implementacion realizada, ya que utiliza las herramientas proporcionadas por el resto de modulos para mostrar un dibujo especifico en pantalla. 

- Dibujo (Implementacion)
    
    En este modulo, se define la sintaxis de nuestro lenguaje, en el proporcionamos el tipo principal para hacer dibujos, y funciones básicas para modificarlos o configurarlos de distintas formas

- Interp
    
    Se encarga de la interpretación semantica del lenguaje definido por el modulo Dibujo. Es decir, hace una traduccion entre lo que especificamos anteriormente y su interpretacion geometrica correspondiente.

- Pred

    Por ultimo, este modulo recibe dibujos sin interpretar, y evalua sobre alguna propiedad a los dibujos basicos que lo constituyen. 

> ### ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez es un parámetro del tipo?

Las figuras basicas no se incluyen en la definicion del lenguaje para permitir una mayor flexibilidad entre la sintaxis y la semantica. Esta forma de implementar los modulos otorga al usuario libertad creativa sobre cómo se comporta el tipo.

> ### ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?

La principal ventaja de esta practica es la privacidad sobre el comportamiento interno del tipo, es decir creamos una capa de abstraccion.