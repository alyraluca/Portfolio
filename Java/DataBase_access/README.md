# ♟️ Database Access con JavaBeans (DBChessGames)

Conjunto de prácticas centradas en el acceso a datos con **Java + JDBC + MySQL**, usando el patrón **JavaBeans** para modelar entidades de dominio que reaccionan a cambios de estado mediante eventos (`PropertyChangeListener`).

Ambas prácticas comparten la misma base de datos, `DBChessGames`, un sistema de gestión de jugadores, torneos, partidas y solicitudes de aplazamiento.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-JavaBeans-blue)

## 📁 Contenido de la carpeta

| Carpeta | Descripción |
|---|---|
| [`Task04JavaBeans/`](Task04JavaBeans) | Define el modelo de datos: los beans de dominio, el acceso a la base de datos y la lógica de eventos. |
| [`Task04JavaBeansDBTest/`](Task04JavaBeansDBTest) | Programa de prueba que dispara los eventos sobre los beans anteriores y valida que la integración con la base de datos funciona correctamente. |

## 🗄️ Modelo de datos (`DBChessGames`)

| Tabla | Descripción |
|---|---|
| **Player** | Jugadores del torneo (`playerID`, `fullname`, `has_match`, `has_deferral`) |
| **Tournament** | Torneos (`code`, `name`) |
| **Game** | Partidas (`gameID`, `code`, `playerID`, `matchdate`, `result`: `PENDING` / `WON` / `LOST` / `DRAWS`) |
| **Deferral** | Solicitudes de aplazamiento (`deferralID`, `code`, `playerID`, `defdate`, `result`: `REQUESTED` / `GRANTED` / `REJECTED`) |
| **Message** | Mensajes de auditoría y trazabilidad de eventos |

## 🧩 1. `Task04JavaBeans` — Modelo y acceso a datos

Define las clases de negocio como JavaBeans, cada una con una responsabilidad concreta:

- **`DBBean`** — fachada de acceso a datos: gestiona la conexión JDBC (`com.mysql.cj.jdbc.Driver`) y ejecuta las consultas SQL mediante `PreparedStatement` (inserciones, actualizaciones y consultas aleatorias de jugadores/torneos).
- **`PlayerBean`** — representa a un jugador; usa `PropertyChangeSupport` para notificar cambios en propiedades clave como la fecha del próximo partido o aplazamiento.
- **`Tournament`** — entidad estructural que representa un torneo, relacionada con jugadores, partidas y aplazamientos.
- **`GameBean`** — escucha los cambios de fecha de partido del jugador: si hay fecha, inserta una partida `PENDING`; si es `null`, cierra la partida como `DRAWS` y limpia el estado del jugador.
- **`DeferralBean`** — análogo a `GameBean` pero para aplazamientos: inserta una solicitud `REQUESTED` o la marca como `REJECTED` según la fecha.
- **`MessageBean`** — registra en la tabla `Message` un log de cada evento relevante (jugador, torneo y acción ejecutada).

**Patrón:** cada bean actúa como observador de `PlayerBean`, de forma que un cambio de estado en el jugador dispara automáticamente inserciones/actualizaciones en la base de datos, sin acoplar la lógica de negocio directamente al SQL.

## 🧪 2. `Task04JavaBeansDBTest` — Pruebas de integración

Contiene el programa `TestPlayer.java`, que ejercita el flujo completo de eventos:

1. Conecta con la base de datos vía `DBBean`.
2. Obtiene un jugador y un torneo aleatorios.
3. Registra `GameBean`, `DeferralBean` y `MessageBean` como listeners de `PlayerBean`.
4. Dispara cambios de propiedad (fecha de partido, fecha de aplazamiento, y su puesta a `null`) para simular: programar partido → solicitar aplazamiento → finalizar partido → cancelar aplazamiento.

Esto valida que el patrón **Observer** implementado con JavaBeans funciona de extremo a extremo: un `setter` en el objeto origen provoca reacciones encadenadas en varios objetos y, finalmente, escrituras reales en MySQL.

## ⚙️ Flujo de eventos

```
PlayerBean.setLdtNextMatchDate(fecha)
        │
        ▼
firePropertyChange("ldtNextMatchDate", ...)
        │
        ├──► GameBean     → inserta/actualiza partida en "Game"
        ├──► MessageBean  → registra el evento en "Message"
        └──► PlayerBean   → actualiza has_match en "Player"
```

El mismo esquema se aplica a `ldtNextDeferralDate` con `DeferralBean`.

## ✅ Fortalezas

- Separación clara de responsabilidades (modelo, acceso a datos, eventos, auditoría).
- Uso de `PreparedStatement` para evitar SQL injection.
- Buen ejemplo práctico del patrón **Observer** aplicado con la API de JavaBeans (`PropertyChangeListener`).
- Esquema de base de datos bien normalizado, con claves foráneas y valores controlados por dominio.

## 🔧 Puntos de mejora identificados

- Cierre incompleto de recursos JDBC (`ResultSet`, `Statement`) en algunos métodos, con riesgo de fuga de conexiones.
- Generación de IDs con `Math.random()` en lugar de autoincrement o UUID, sin garantía de unicidad.
- Comprobaciones de propiedad algo redundantes en `MessageBean` y `GameBean`.
- Sin pool de conexiones: cada operación abre una conexión nueva, lo que limita la concurrencia.
- Validación de datos limitada (fechas, resultados, consistencia jugador/torneo/partida).

Como posibles ampliaciones: resultados de partida generados dinámicamente, historial y estadísticas de victorias/derrotas por jugador, y mejoras en la validación y generación de identificadores.

## 🎓 Valor didáctico

Estas dos prácticas, en conjunto, cubren de forma progresiva:

- Conexión Java–MySQL mediante JDBC.
- Modelado de entidades como JavaBeans (propiedades, getters/setters).
- Programación orientada a eventos con `PropertyChangeSupport` / `PropertyChangeListener`.
- El patrón de diseño **Observer** en un contexto real de persistencia.
- Separación entre lógica de negocio y acceso a datos.

---

📄 Documentación detallada de cada práctica: [`Task04JavaBeans/README.md`](Task04JavaBeans/README.md) · [`Task04JavaBeansDBTest/README.md`](Task04JavaBeansDBTest/README.md)