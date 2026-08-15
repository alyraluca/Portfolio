# JavaBeans

## 1. Objetivo de la práctica

La aplicación de esta carpeta tiene como objetivo demostrar el uso de JavaBeans para modelar entidades de dominio y relacionarlas con una base de datos MySQL mediante JDBC. La idea principal es que cada clase de negocio actúa como un bean con atributos, getters/setters y lógica de eventos mediante `PropertyChangeListener`.

La práctica se centra en una base de datos llamada `DBChessGames` y en el manejo de información relacionada con jugadores, torneos, partidos y solicitudes de aplazamiento. Todo ello se apoya en la estructura de tablas y en la lógica de negocio implementada en los beans.

---

## 2. Arquitectura general

La estructura del proyecto está organizada en la carpeta `src/Beans`, donde cada clase cumple una función concreta:

- `DBBean.java`: gestiona la conexión con la base de datos y ejecuta las consultas SQL.
- `PlayerBean.java`: representa a un jugador y notifica cambios de estado.
- `Tournament.java`: representa un torneo.
- `GameBean.java`: representa una partida y reacciona a cambios de fecha de partido.
- `DeferralBean.java`: representa una solicitud de aplazamiento.
- `MessageBean.java`: registra mensajes de auditoría o eventos del sistema.
- `DBChessGames.sql`: script de creación de la base de datos y datos iniciales.

La aplicación sigue un patrón de diseño basado en JavaBeans + acceso a datos. Las entidades no son meramente DTOs; además reaccionan a cambios de propiedad mediante eventos del API de Java Beans.

---

## 3. Modelo de datos

El esquema SQL define varias tablas relacionadas:

### `Player`
- `playerID` (clave primaria)
- `fullname`
- `has_match`
- `has_deferral`

### `Tournament`
- `code` (clave primaria)
- `name`

### `Game`
- `gameID` (clave primaria)
- `code` (FK a Tournament)
- `playerID` (FK a Player)
- `matchdate`
- `result`

Valores posibles:
- `PENDING`
- `WON`
- `LOST`
- `DRAWS`

### `Deferral`
- `deferralID` (clave primaria)
- `code` (FK a Tournament)
- `playerID` (FK a Player)
- `defdate`
- `result`

Valores posibles:
- `REQUESTED`
- `GRANTED`
- `REJECTED`

### `Message`
- `messageID` (autoincremental)
- `playerID` (FK a Player)
- `description`

La base de datos está preparada para registrar tanto el estado de un jugador como sus partidos y posibles aplazamientos. Se incorpora además un sistema de mensajes para dejar trazabilidad de los eventos más relevantes.

---

## 4. Análisis de cada clase

### 4.1 `DBBean`

Es la pieza central de acceso a datos. En el constructor crea la conexión con MySQL usando JDBC:

- driver: `com.mysql.cj.jdbc.Driver`
- URL: `jdbc:mysql://localhost:3306/DBChessGames`
- usuario: `mavenuser`
- password: `ada0486`

Además, ofrece los métodos más importantes:

- `getRandomPlayer()`: obtiene un jugador aleatorio y también asigna un torneo aleatorio.
- `getRandomTournament()`: obtiene un torneo aleatorio.
- `updatePlayerDeferralStatus(...)`: cambia el estado `has_deferral` del jugador.
- `updatePlayerGameStatus(...)`: cambia el estado `has_match` del jugador.
- `insertDeferral(...)`: comprueba si existe un partido pendiente y, si es así, inserta una solicitud de aplazamiento.
- `insertGame(...)`: crea un registro en la tabla `Game`.
- `insertMessage(...)`: inserta un mensaje asociado al jugador.
- `updateDeferralVerdict(...)`: actualiza el resultado del aplazamiento.
- `updateGameResult(...)`: actualiza el resultado de una partida.

Este bean hace de fachada de acceso a datos y encapsula la lógica de persistencia. Es una buena idea para separar la capa de negocio de la capa de acceso a datos.

### 4.2 `PlayerBean`

`PlayerBean` representa a un jugador del torneo. Tiene atributos como:

- `stFullname`
- `iPlayerID`
- `objTournament`
- `bHasAMatch`
- `bHasADeferral`
- `ldtNextMatchDate`
- `ldtNextDeferralDate`

Además, tiene un `PropertyChangeSupport` que permite notificar cambios en sus propiedades, especialmente en las fechas de partido y aplazamiento.

Los setters invocan a `firePropertyChange(...)`, por ejemplo:

- `setLdtNextMatchDate(...)`
- `setLdtNextDeferralDate(...)`

Esto es muy relevante porque permite que otros beans reaccionen automáticamente a cambios en el estado del jugador.

### 4.3 `Tournament`

La clase `Tournament` es más simple. Representa un torneo con:

- `stCode`
- `stName`

Aunque implemente `PropertyChangeListener`, no tiene una lógica de negocio compleja en `propertyChange()`. Tiene un papel principalmente estructural: sirve como objeto relacionado con `Player`, `Game` y `Deferral`.

### 4.4 `GameBean`

`GameBean` representa una partida. Tiene un jugador y un torneo asociados, así como fecha y resultado.

La parte más interesante es esta:

- Implementa `PropertyChangeListener`.
- Reacciona cuando el jugador cambia su `ldtNextMatchDate`.
- Si la fecha no es nula, inserta un partido en la tabla `Game` con estado `PENDING` y actualiza `has_match = true`.
- Si la fecha es nula, asume que la partida se completa y actualiza el resultado a `DRAWS`, además de limpiar el estado del jugador.

Esto demuestra una integración muy clara entre la capa de modelo y la capa persistente: un cambio en el estado del objeto provoca efectos secundarios en la base de datos.

### 4.5 `DeferralBean`

Está diseñado de forma muy similar a `GameBean`, pero para aplazamientos.

Su lógica es:

- Si `ldtNextDeferralDate` tiene valor, se interpreta como solicitud de aplazamiento.
- Se inserta un registro en `Deferral` con estado `REQUESTED`.
- Se actualiza `has_deferral = true` del jugador.
- Si la fecha es nula, se considera que la solicitud se rechaza y se actualiza la fila en `Deferral` con resultado `REJECTED`.

Esto hace que `DeferralBean` funcione como una entidad reactiva a cambios de fecha, muy útil para un flujo de negocio basado en eventos.

### 4.6 `MessageBean`

`MessageBean` se encarga de guardar mensajes de evento. También escucha cambios de propiedades del jugador.

Cuando se actualiza la fecha de partido o de aplazamiento, `MessageBean` genera un texto de log que:

- identifica al jugador,
- identifica al torneo,
- describe la acción ejecutada,
- e inserta el mensaje en la tabla `Message`.

Esta clase tiene un carácter de auditoría y trazabilidad. No es el núcleo funcional, pero aporta claridad y depuración del sistema.

---

## 5. Flujo de funcionamiento

El flujo habitual de la práctica sería algo así:

1. Se crea un `PlayerBean` con sus datos básicos.
2. Se establece una fecha para el próximo partido o aplazamiento.
3. El setter dispara un `PropertyChangeEvent`.
4. Los beans interesados (`GameBean`, `DeferralBean`, `MessageBean`) reciben el evento.
5. Cada uno decide si debe insertar o actualizar registros en la base de datos.
6. `DBBean` ejecuta las sentencias SQL con `PreparedStatement` para evitar SQL injection y mejorar seguridad.
7. El estado del jugador en la tabla `Player` se actualiza en consecuencia.

Este enfoque es típico del patrón de eventos de JavaBeans y demuestra cómo los objetos pueden reaccionar ante cambios de estado sin acoplar demasiado el código de negocio con la lógica SQL.

---

## 6. Fortalezas del proyecto

### a) Separación clara de responsabilidades
Cada clase tiene un papel concreto:

- modelo de entidad,
- acceso a la base de datos,
- lógica de eventos,
- trazabilidad.

### b) Uso de `PreparedStatement`
La aplicación usa parámetros SQL con `?`, lo que es más seguro y limpio que concatenar cadenas manualmente.

### c) Uso de JavaBeans
El uso de propiedades, eventos y listeners muestra un conocimiento importante del ecosistema Java, especialmente en aplicaciones que necesitan reaccionar a cambios de estado.

### d) Base de datos bien estructurada
El esquema de `DBChessGames.sql` está ordenado y define restricciones claves, foreign keys y validaciones de resultados.

---

## 7. Problemas o puntos mejorables

Aunque la práctica está bien planteada, hay varios detalles que podrían mejorarse:

### 1. Fuga de conexiones
En varios métodos se crea un nuevo `DBBean` y después se llama a `closeDBConnection()`. Sin embargo, no todos los recursos de `ResultSet`, `Statement` o `PreparedStatement` se cierran explícitamente. Esto puede provocar pérdidas de conexiones y problemas de rendimiento.

### 2. Uso de IDs aleatorios
Los métodos `generateRandomDeferralID()` y `generateRandomGameID()` generan números aleatorios con `Math.random()`, pero no se garantiza que sean únicos ni que se gestionen con una secuencia de base de datos. En una aplicación real, sería mejor usar autoincrement o UUIDs.

### 3. Lógica de eventos algo redundante
En `MessageBean` y `GameBean` hay comprobaciones repetidas para el mismo nombre de propiedad (`ldtNextMatchDate`, `ldtNextDeferralDate`). Esto dificulta la lectura y puede generar comportamientos difíciles de mantener.

### 4. `DBBean` no es completamente seguro en acceso concurrente
Como cada operación crea una nueva conexión, el código no gestiona bien varios usuarios concurrentes. La aplicación sería mucho más robusta si usase un pool de conexiones o un patrón DAO más estructurado.

### 5. Validación de datos limitada
No hay comprobaciones exhaustivas sobre valores nulos, fechas inválidas, resultados no permitidos o consistencia entre jugador, torneo y partido. Una aplicación de producción debería validar esos casos antes de persistir.

---

## 8. Valor didáctico de la práctica

La práctica es muy útil para aprender:

- cómo conectar Java con MySQL,
- cómo modelar datos en clases JavaBeans,
- cómo usar `PreparedStatement`,
- cómo mantener relaciones entre tablas,
- cómo reaccionar a cambios de estado mediante eventos,
- cómo separar lógica de negocio de acceso a datos.

La aplicación no es una solución de producción completa, pero sí funciona muy bien como ejercicio de consolidación de conceptos de programación Java orientada a objetos y acceso a bases de datos.

---

## 9. Conclusión

La carpeta `Task04JavaBeans` representa una práctica muy completa de manejo de datos en Java con MySQL. La combinación de clases JavaBeans, eventos `PropertyChange`, y conexión JDBC permite entender cómo un modelo de dominio puede interactuar directamente con una base de datos y responder dinámicamente a cambios de estado.

La aplicación demuestra buenas bases conceptuales, aunque también muestra varios puntos de mejora para llevarla a un nivel más robusto y profesional. En conjunto, es una práctica sólida para aprender el flujo completo de una aplicación Java con persistencia en base de datos.

---

## 10. Archivos clave

- [src/Beans/DBBean.java](src/Beans/DBBean.java)
- [src/Beans/PlayerBean.java](src/Beans/PlayerBean.java)
- [src/Beans/GameBean.java](src/Beans/GameBean.java)
- [src/Beans/DeferralBean.java](src/Beans/DeferralBean.java)
- [src/Beans/MessageBean.java](src/Beans/MessageBean.java)
- [src/Beans/DBChessGames.sql](src/Beans/DBChessGames.sql)

