### Integrantes
- Pedro Salas Piñero (pedrosalaspinero@mi.unc.edu.ar)
- Juan Pablo Ludueña Zakka (juan.luduena.161@mi.unc.edu.ar)
- Ebner Lautaro (lautaro.ebner@mi.unc.edu.ar)


###  Decisiones de diseño:
Para el proyecto nos mantuvimos apegados a la consigna hasta el ultimo apartado (4). Luego hicimos lo que pudimos con la guia que nos dieron. Lo que entendimos y luego implementamos es lo siguiente: Se busca utilizar los objetos (NamedEntity) para almacenar y procesar datos. Luego con las interfaces dadas y nuestras computamos la frecuencia que cada categoria de NamedEntity tiene en toda la Subcription. 
Aparte de esto, decidimos implementar ambos Parser (rss y reddit).





TEMAS
- deportes:
  - fútbol
  - fórmula 1
  - otros?
- politica:
  - nacional
  - internacional
  - otros?

- otros


CATEGORIAS de NE
- persona: 
  - id
  - apellido 
    - forma canónica 
    - origen
  - nombre 
    - forma canónica
    - origen
- Pais:
  - Nombre
    - forma canónica
    - origen
  - poblacion
  - lengua oficial
- Compania: (Organizacion)
  - forma canonica
  - cantidad de empleados
  - tipo
- otros


$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

CLASES A IMPLEMENTAR

- Palabra
 - formaCanonica
 - Origen

- Theme
  - type (deporte o politica)

- PersonaDeporte  - Messi
  - id <Integer>
  - apellido <Palabra>
  - nombre <Palabra>
  - tema <Deporte>
  
- PersonaPolitica trump
  - id <Integer>
  - apellido <Palabra>
  - nombre <Palabra>
  - tema <Politica>

- CompaniaDeporte adidas
  - forma canonica
  - cantidad de empleados
  - tema <Deporte>

- CompaniaPolitica twitter
  - forma canonica
  - cantidad de empleados
  - tipo
  - tema <Politica>

- PaisDeporte scaloneta
  - nombre
  - poblacion
  - lengua oficial
  - tema <Deporte>


- PaisPolitica argentina
  - nombre
  - poblacion
  - lengua oficial
  - tema <Politica>
