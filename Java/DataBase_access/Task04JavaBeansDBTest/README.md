# JavaBeansDBTest

## 1. Objetivo de la práctica

Esta carpeta tiene como finalidad probar y validar la funcionalidad de los JavaBeans desarrollados en la práctica anterior, conectándolos con la base de datos `DBChessGames` y comprobando cómo reaccionan ante cambios de estado. La aplicación principal es [src/TestBeans/TestPlayer.java](src/TestBeans/TestPlayer.java), que actúa como programa de prueba para generar eventos de propiedades y observar la respuesta de los listeners.

La idea central es demostrar que un objeto origen (`PlayerBean`) puede notificar cambios a varios objetos observadores (`GameBean`, `DeferralBean`, `MessageBean`) mediante `PropertyChangeSupport` y `PropertyChangeListener`.

---

## 2. Función de la aplicación

El programa principal realiza lo siguiente:

1. crea una conexión con la base de datos mediante `DBBean`
2. obtiene un jugador aleatorio y un torneo aleatorio
3. crea los objetos listeners
4. asigna al jugador como fuente de eventos
5. registra los listeners en el objeto `PlayerBean`
6. dispara cambios en propiedades como:
   - fecha del próximo partido
   - fecha de la próxima solicitud de aplazamiento
   - finalización del partido
   - cancelación del aplazamiento

Estos cambios provocan que los listeners activen acciones sobre la base de datos.

---

## 3. Estructura del proyecto

La carpeta está organizada de forma muy sencilla:

- [src/TestBeans/TestPlayer.java](src/TestBeans/TestPlayer.java): programa principal de pruebas.
- [src/TestBeans/Assessment.txt](src/TestBeans/Assessment.txt): texto explicativo del trabajo realizado y dificultades encontradas.
- la aplicación reutiliza las clases de la práctica anterior, especialmente las del paquete `Beans`.

La relación entre las clases es la siguiente:

- `PlayerBean`: objeto fuente que emite los eventos.
- `GameBean`: escucha cambios de la fecha del partido.
- `DeferralBean`: escucha cambios de la fecha de aplazamiento.
- `MessageBean`: registra los eventos y genera mensajes de trazabilidad.
- `DBBean`: ejecuta las consultas SQL sobre la base de datos.

---

## 4. Cómo funciona el flujo de eventos

El punto clave de esta práctica es el patrón observer implementado con JavaBeans.

### 4.1 Objeto origen
En `PlayerBean`, cada setter dispara un evento de propiedad usando `firePropertyChange(...)`.

Por ejemplo:

- `setLdtNextMatchDate(...)`
- `setLdtNextDeferralDate(...)`

Cuando estos métodos se ejecutan, los listeners registrados reciben una notificación con:

- el nombre de la propiedad,
- el valor antiguo,
- el valor nuevo.

### 4.2 Listeners
Los objetos `GameBean`, `DeferralBean` y `MessageBean` implementan `PropertyChangeListener` y se registran en `PlayerBean` mediante:

- `objPlayer.addPropertyChangeListener(objMessage)`
- `objPlayer.addPropertyChangeListener(objGame)`
- `objPlayer.addPropertyChangeListener(objDeferral)`

Cada uno responde a un tipo de evento específico.

### 4.3 Resultado esperado
Cuando el jugador cambia la fecha del partido:

- `GameBean` inserta un partido en la tabla `Game` si la fecha es válida
- `MessageBean` registra el evento en la tabla `Message`
- `PlayerBean` actualiza el estado de `has_match`

Cuando el jugador cambia la fecha de aplazamiento:

- `DeferralBean` inserta una solicitud en la tabla `Deferral`
- `MessageBean` registra la solicitud
- `PlayerBean` actualiza el estado de `has_deferral`

Cuando la fecha se pone a `null`:

- se interpreta como finalización o rechazo del evento
- se actualiza el resultado del juego o del aplazamiento
- se limpia el estado del jugador

---

## 5. El programa principal: TestPlayer

El programa [src/TestBeans/TestPlayer.java](src/TestBeans/TestPlayer.java) ejecuta una secuencia muy clara:

```java
objPlayer.setLdtNextMatchDate(LocalDateTime.of(2024, 3, 8, 18, 0));
objPlayer.setLdtNextDeferralDate(LocalDateTime.of(2024, 3, 8, 18, 0));
objPlayer.setLdtNextMatchDate(null);
objPlayer.setLdtNextDeferralDate(null);
```

Esto provoca cuatro escenarios:

1. se programa un partido
2. se solicita un aplazamiento
3. se marca la finalización del partido
4. se marca el fin del aplazamiento

Este flujo permite verificar que los eventos se disparan correctamente y que las acciones en la base de datos se ejecutan según la lógica de negocio.

---

## 6. Relación con la base de datos

La práctica reutiliza el esquema de `DBChessGames`, donde se gestionan:

- jugadores
- torneos
- partidos
- aplazamientos
- mensajes de evento

En la práctica de test, el objetivo no es crear una nueva base de datos distinta, sino comprobar cómo los `JavaBeans` interactúan con la estructura ya definida.

Esto convierte la aplicación en un ejemplo muy claro de: modelo + eventos + acceso a datos.

---

## 7. Dificultades identificadas

El archivo [src/TestBeans/Assessment.txt](src/TestBeans/Assessment.txt) refleja varios problemas importantes que el autor tuvo durante la implementación:

### a) Problemas con la conexión a la base de datos
Se detectaban `null pointers` y fallos al querer conectar a MySQL. Esto es un problema típico cuando la conexión no está bien inicializada o cuando el objeto origen no está correctamente configurado.

### b) Problemas con el objeto fuente y los listeners
La mayor dificultad era asignar el `PlayerBean` como fuente de eventos y registrar los listeners de forma correcta. Si no se hace bien, los eventos no se disparan y la aplicación no actualiza la base de datos.

### c) Generación del identificador de aplazamiento
Se menciona que el `deferralID` tuvo que generarse aleatoriamente porque no se gestionaba bien con la base de datos.

Esto es una buena señal de que la parte más compleja no era la SQL, sino la integración entre los objetos y la base de datos.

---

## 8. Valor didáctico de la práctica

Esta carpeta es muy útil para aprender:

- cómo se usa JavaBeans en la práctica real
- cómo funcionan los eventos de propiedad en Java
- cómo un objeto puede actuar como fuente de eventos
- cómo varios listeners reaccionan a cambios del mismo objeto
- cómo integrar una capa de modelo con acceso a datos

Además, permite comprender el patrón de diseño Observer dentro del contexto de aplicaciones Java con persistencia.

---

## 9. Puntos de mejora

El texto de la evaluación también señala varias mejoras posibles:

- decidir aleatoriamente el resultado de una partida (`PENDING`, `WON`, `LOST`, `DRAWS`)
- mostrar historial de partidas del jugador
- incluir estadísticas de victorias y derrotas
- mejorar la gestión de identificadores para evitar duplicados
- reforzar la validación de fechas y estados

Estas mejoras harían la práctica más completa y más cercana a una aplicación real de gestión deportiva o de torneos.

---

## 10. Conclusión

La práctica `Task04JavaBeansDBTest` no es una aplicación independiente muy grande, pero sí es un ejemplo muy claro de cómo se integran los JavaBeans con la lógica de negocio y la base de datos. El verdadero valor está en la interacción entre objetos y eventos: un cambio en un jugador provoca una reacción en varios otros objetos, y esos objetos terminan escribiendo en la base de datos.

Es una práctica muy útil para entender el patrón de observadores, la programación orientada a eventos y la conexión entre modelo y persistencia en Java.

---

## 11. Archivos clave

- [src/TestBeans/TestPlayer.java](src/TestBeans/TestPlayer.java)
- [src/TestBeans/Assessment.txt](src/TestBeans/Assessment.txt)
- [../Task04JavaBeans/src/Beans/DBBean.java](../Task04JavaBeans/src/Beans/DBBean.java)
- [../Task04JavaBeans/src/Beans/PlayerBean.java](../Task04JavaBeans/src/Beans/PlayerBean.java)
- [../Task04JavaBeans/src/Beans/GameBean.java](../Task04JavaBeans/src/Beans/GameBean.java)
- [../Task04JavaBeans/src/Beans/DeferralBean.java](../Task04JavaBeans/src/Beans/DeferralBean.java)
- [../Task04JavaBeans/src/Beans/MessageBean.java](../Task04JavaBeans/src/Beans/MessageBean.java)
