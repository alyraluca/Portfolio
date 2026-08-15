# ☕ Java

Colección de prácticas de Java centradas en distintos pilares de la programación orientada a objetos: desde el acceso a datos con JDBC/JavaBeans hasta el modelado de jerarquías de clases mediante herencia, interfaces y polimorfismo.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)

## 📁 Proyectos

| Carpeta | Práctica | Conceptos clave |
|---|---|---|
| [`database_access/`](database_access) | **DBChessGames** — JavaBeans + JDBC + MySQL | JavaBeans, eventos (`PropertyChangeListener`), patrón Observer, acceso a datos, `PreparedStatement` |
| [`pueblo_dormido/`](pueblo_dormido) | **El Poble Dormit** — simulación de ecosistema | Herencia, interfaces, polimorfismo, abstracción, encapsulación, colecciones |

---

### 🗄️ `database_access` — JavaBeans + acceso a datos

Dos prácticas conectadas sobre una misma base de datos (`DBChessGames`, gestión de jugadores, torneos, partidas y aplazamientos):

- **Task04JavaBeans**: modela el dominio como JavaBeans (`PlayerBean`, `GameBean`, `DeferralBean`, `MessageBean`) que reaccionan a cambios de estado mediante eventos de propiedad, y usa `DBBean` como fachada de acceso a MySQL vía JDBC.
- **Task04JavaBeansDBTest**: programa de prueba (`TestPlayer`) que dispara esos eventos y valida el flujo completo — un cambio en el jugador provoca inserciones/actualizaciones encadenadas en la base de datos.

📄 Más detalle en [`database_access/README.md`](database_access/README.md)

### 🐺 `pueblo_dormido` — Simulación orientada a objetos

Simulación de un pueblo ficticio con tres especies (**humanos, lobos y vampiros**) que envejecen, se reproducen y combaten entre ellas año tras año hasta que una sola especie domina la población.

- **`Ciutada`** (clase abstracta) centraliza los atributos y comportamiento comunes a todos los habitantes.
- **`Huma`**, **`Llop`** y **`Vampir`** especializan ese comportamiento e implementan tres interfaces: `Batalla` (combate), `Vulnerable` (debilidades por especie) y `CicleVital` (reproducción y envejecimiento).
- Reglas de combate tipo "piedra-papel-tijera": el vampiro convierte humanos, el lobo vence al vampiro, el humano vence al lobo.
- **`ElPobleDormit`** es la clase principal: genera la población, ofrece un menú interactivo (censar, avanzar un año, salir) y controla el bucle de simulación hasta la homogeneización del pueblo.

📄 Más detalle en [`pueblo_dormido/README.md`](pueblo_dormido/README.md)

---

## 🎓 En conjunto

Ambas prácticas, aunque muy distintas en propósito, cubren de forma complementaria fundamentos sólidos de Java:

- **`database_access`** pone el foco en la persistencia, la programación orientada a eventos y la integración con una base de datos real.
- **`pueblo_dormido`** pone el foco en el diseño de jerarquías de clases, el polimorfismo y la modelización de comportamientos mediante interfaces.

Juntas forman una buena base de POO en Java: modelado de dominio, relaciones entre objetos, reacción a eventos y persistencia de datos.