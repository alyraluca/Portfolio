# El Poble Dormit

## 1. Descripción general

Esta actividad consiste en la simulación de un pueblo ficticio llamado “Poble Dormit”, donde conviven distintas especies: humanos, lobos y vampiros. El objetivo del programa es modelar un ecosistema con reglas de reproducción, envejecimiento, combate y supervivencia.

La práctica se centra en aplicar conceptos de programación orientada a objetos, como:

- herencia
- interfaces
- polimorfismo
- abstracción
- encapsulación
- gestión de listas y colecciones
- simulación de eventos por años

El proyecto se desarrolla en Java y su punto de entrada es la clase principal [src/ElPobleDormit.java](src/ElPobleDormit.java).

---

## 2. Estructura de archivos

Los archivos principales son:

- [src/ElPobleDormit.java](src/ElPobleDormit.java): clase principal con la simulación y el menú del juego.
- [src/Ciutada.java](src/Ciutada.java): clase base abstracta para todos los habitantes del pueblo.
- [src/Huma.java](src/Huma.java): representación de un humano.
- [src/Llop.java](src/Llop.java): representación de un lobo.
- [src/Vampir.java](src/Vampir.java): representación de un vampiro.
- [src/Batalla.java](src/Batalla.java): interfaz que define el comportamiento de combate.
- [src/Vulnerable.java](src/Vulnerable.java): interfaz con las vulnerabilidades de cada tipo.
- [src/CicleVital.java](src/CicleVital.java): interfaz que define reproducción y envejecimiento.

---

## 3. Objetivo del juego

La simulación pretende recrear cómo cambia la población del pueblo con el paso del tiempo. Cada año se produce una serie de eventos:

1. los habitantes envejecen
2. se produce reproducción en algunos casos
3. se eligen oponentes aleatorios
4. se resuelven combates
5. se eliminan personajes muertos o convertidos
6. se comprueba si el pueblo ha quedado dominado por un solo tipo

La simulación finaliza cuando todos los habitantes del pueblo pertenecen al mismo tipo.

---

## 4. Análisis de clases

### 4.1 Clase principal: ElPobleDormit

Esta es la clase más importante del proyecto, ya que controla todo el flujo del programa.

Su función principal es:

- generar una población aleatoria
- mostrar un menú interactivo
- censar a los ciudadanos
- avanzar un año en la simulación
- elegir oponentes al azar
- gestionar combates y reproducción
- detectar cuando la población queda homogenizada

En el método main se inicializa la población y se entra en un bucle para que el usuario pueda escoger entre:

- consultar censo
- avanzar un año
- salir del programa

Esto hace que la aplicación sea interactiva y permita observar el comportamiento del pueblo a lo largo del tiempo.

### 4.2 Clase abstracta: Ciutada

La clase Ciutada representa la base común de todos los ciudadanos. Es abstracta, por lo que no puede instanciarse directamente.

Tiene atributos compartidos como:

- nombre
- población total

También contiene métodos comunes para:

- obtener y modificar el nombre
- consultar la población actual
- censar la población
- mostrar estadísticas por tipo
- definir la operación morir, que cada subclase implementa a su manera

La idea de esta clase es encapsular la lógica común y dejar que cada especie defina su comportamiento específico.

### 4.3 Interfaz Batalla

La interfaz Batalla define el método:

- combat(Ciutada oponente)

Esto permite que cualquier tipo de ciudadano pueda participar en un combate, respetando una firma común. Es un ejemplo claro de polimorfismo: distintos tipos pueden reaccionar de forma distinta al mismo método.

### 4.4 Interfaz Vulnerable

La interfaz Vulnerable representa la vulnerabilidad de cada especie. Define constantes:

- VAMPIR
- HUMA
- LLOP

Además, obliga a implementar el método getVulnerable().

Esto hace que cada tipo de ciudadano pueda declarar su vulnerabilidad sin depender de una estructura rígida.

### 4.5 Interfaz CicleVital

La interfaz CicleVital contiene los métodos:

- reproduir(ArrayList<Ciutada> ciutadans)
- envellir(ArrayList<Ciutada> ciutadans)

Estas dos operaciones son esenciales en la simulación:

- reproducción: añade nuevos miembros a la población
- envejecimiento: reduce la vida y puede provocar la muerte por vejez

Es una buena solución para separar el comportamiento biológico de la lógica general del pueblo.

---

## 5. Análisis por tipo de personaje

### 5.1 Humano

La clase Huma extiende Ciutada e implementa Vulnerable, Batalla y CicleVital.

Características principales:

- es vulnerable a los vampiros
- puede reproducirse
- envejece con el tiempo
- si se enfrenta a un vampiro, pierde
- si se enfrenta a un lobo, gana y elimina al lobo

En la simulación, el humano se comporta como una especie con capacidad de crecimiento y supervivencia, pero con vulnerabilidad clara ante vampiros.

### 5.2 Lobo

La clase Llop también hereda de Ciutada e implementa las mismas interfaces.

Características principales:

- es vulnerable a los humanos
- se reproduce rápidamente
- envejece más deprisa que los humanos
- puede ganar a los vampiros
- pierde frente a los humanos

Los lobos tienen un ciclo vital más agresivo y su reproducción puede provocar un aumento importante de la población.

### 5.3 Vampiro

La clase Vampir extiende Ciutada e implementa Vulnerable y Batalla.

Características principales:

- es vulnerable a los lobos
- puede convertir humanos en vampiros
- puede ganar a los humanos
- se elimina frente a los lobos

El vampiro es el personaje más dominante en términos de transformación de otros ciudadanos, lo que le da un componente estratégico importante dentro del juego.

---

## 6. Sistema de reproducción y envejecimiento

La simulación aprovecha las interfaces de ciclo vital para controlar la evolución de cada especie.

### Reproducción

- los humanos producen como máximo 1 hijo
- los lobos pueden tener varios cachorros en cada ciclo
- según el proyecto, hay un límite máximo de población que impide crecer indefinidamente

### Envejecimiento

- cada especie tiene una manera distinta de envejecer
- si la vida llega a cero, se llama al método morir
- esto elimina al personaje de la lista del pueblo y ajusta la población

Este diseño permite modelar el ciclo de vida mediante lógica propia de cada especie, sin acoplar demasiado el código.

---

## 7. Sistema de combates

Los combates son una parte clave del proyecto. El programa selecciona dos ciudadanos aleatoriamente y compara su tipo. Dependiendo de las reglas:

- humano vs vampiro: humano pierde
- humano vs lobo: humano gana
- lobo vs vampiro: lobo gana
- lobo vs humano: humano gana / lobo pierde
- vampiro vs humano: vampiro gana y convierte al humano
- vampiro vs lobo: lobo gana

Cada clase implementa la lógica de combat() diferenciada. Esto demuestra el uso efectivo del polimorfismo en Java.

---

## 8. Relevancia de la práctica

Este proyecto es muy útil para aprender a:

- diseñar jerarquías de clases
- crear interfaces para comportamientos comunes
- implementar herencia con especialización por subclase
- trabajar con listas dinámicas
- aplicar lógica de simulación
- manejar condiciones de fin de juego

También permite practicar un enfoque orientado a modelos que se comportan de manera distinta según su tipo, algo muy habitual en programación profesional.

---

## 9. Fortalezas del proyecto

Entre lo más positivo del proyecto destacan:

- buena separación entre clase base y subclases
- uso claro de interfaces para comportamientos comunes
- lógica de simulación fácil de seguir
- fácil expansión para añadir nuevas especies o reglas
- desarrollo con buena comprensión del concepto de herencia

---

## 10. Puntos mejorables

Aunque la práctica está bien planteada, se pueden observar varios aspectos que podrían mejorarse:

- la gestión de población global es algo compleja y en algunos puntos se recalcula manualmente
- algunos métodos dependen de contadores estáticos, lo que puede complicar el mantenimiento
- el menú de consola es funcional, pero la lógica de simulación podría organizarse mejor en métodos más específicos
- la depuración es algo complicada cuando se mezclan reproducción, envejecimiento y combates en el mismo ciclo

Aun así, como ejercicio educativo cumple muy bien su objetivo.

---

## 11. Conclusión

La actividad del “Poble Dormit” es una práctica muy interesante para comprender la programación orientada a objetos en Java. Se trabajan conceptos esenciales como la herencia, las interfaces, la abstracción y el polimorfismo, aplicados a una simulación viva y dinámica.

La estructura del proyecto es clara y muestra una buena separación entre:

- la lógica común del pueblo
- las reglas de cada especie
- la simulación del flujo del juego

En resumen, es una actividad muy útil para consolidar conocimientos de Java y para entender cómo modelar sistemas complejos con clases y comportamientos diferenciados.

---

## 12. Archivos clave

- [src/ElPobleDormit.java](src/ElPobleDormit.java)
- [src/Ciutada.java](src/Ciutada.java)
- [src/Huma.java](src/Huma.java)
- [src/Llop.java](src/Llop.java)
- [src/Vampir.java](src/Vampir.java)
- [src/CicleVital.java](src/CicleVital.java)
- [src/Batalla.java](src/Batalla.java)
- [src/Vulnerable.java](src/Vulnerable.java)
