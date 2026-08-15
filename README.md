# 👋 Portfolio — Alexandra Raluca Savu

Repositorio con las prácticas, proyectos y trabajos desarrollados durante el Ciclo Formativo de Grado Superior en **Desarrollo de Aplicaciones Multiplataforma (DAM)** y en el **Curso de Especialización en Ciberseguridad en Entornos de las Tecnologías de la Información**. Incluye desde ejercicios de fundamentos (POO, concurrencia, acceso a datos) hasta el Trabajo Fin de Título.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=flat&logo=python&logoColor=white)
![C#](https://img.shields.io/badge/C%23-239120?style=flat&logo=c-sharp&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![Cybersecurity](https://img.shields.io/badge/Ciberseguridad-000000?style=flat&logo=hackthebox&logoColor=white)

## 📁 Estructura del repositorio

| Carpeta | Contenido |
|---|---|
| [`java/`](java) | Prácticas de Java: acceso a datos con JavaBeans/JDBC y una simulación orientada a objetos. |
| [`python/`](python) | Prácticas de concurrencia en Python (`threading`, sockets). |
| [`Buscaminas_C#_WPF/`](Buscaminas_C%23_WPF) | Buscaminas en C# / WPF. |
| [`Proyecto final de Título - DAM/`](Proyecto%20final%20de%20T%C3%ADtulo%20-%20DAM) | Trabajo Fin de Título: app Android de comparación de precios en supermercados. |
| [`ciberseguridad/`](ciberseguridad) | Trabajos de análisis forense, gestión de incidentes, normativa y seguridad en producción. |

---

## ☕ Java — `java/`

Colección de prácticas centradas en distintos pilares de la programación orientada a objetos: desde el acceso a datos con JDBC/JavaBeans hasta el modelado de jerarquías de clases mediante herencia, interfaces y polimorfismo.

| Carpeta | Práctica | Conceptos clave |
|---|---|---|
| [`database_access/`](java/database_access) | **DBChessGames** — JavaBeans + JDBC + MySQL | JavaBeans, eventos (`PropertyChangeListener`), patrón Observer, `PreparedStatement` |
| [`pueblo_dormido/`](java/pueblo_dormido) | **El Poble Dormit** — simulación de ecosistema | Herencia, interfaces, polimorfismo, abstracción, colecciones |

📄 Más detalle en [`java/README.md`](java/README.md)

## 🧵 Python — `python/`

Prácticas de **programación concurrente** con `threading`, explorando mecanismos de sincronización (`Lock`, `Semaphore`, `Barrier`, `Timer`) y comunicación en red mediante `sockets`.

| Práctica | Mecanismo |
|---|---|
| Simulación de una biblioteca | `Lock` |
| Simulación de un supermercado | `Semaphore`, `Barrier`, `Timer`, `Lock` |
| Cliente-Servidor con sockets TCP | `socket` + `threading` |

📄 Más detalle en [`python/README.md`](python/README.md)

## 💣 Buscaminas — `csharp/buscaminas/`

Implementación del clásico juego del Buscaminas en **C# con WPF**: tablero 9x9, 10 minas, apertura en cascada, marcado de minas, temporizador y pantallas de fin de partida.

📄 Más detalle en [`Buscaminas_C#_WPF/README.md`](Buscaminas_C%23_WPF/README.md)

## 🛒 PriceOn — `PriceOn/`

**Trabajo Fin de Título** (2º DAM): aplicación Android desarrollada en Java que permite comparar precios de productos entre supermercados, con búsqueda por nombre o código de barras, gráficas de evolución de precios, favoritos, historial y un sistema de roles (usuario, moderador, administrador). Backend en Firebase (Authentication + Firestore), proyecto de código abierto bajo licencia MIT.

📄 Más detalle en [`PriceOn/README.md`](Proyecto%20final%20de%20T%C3%ADtulo%20-%20DAM/README.md)

## 🔐 Ciberseguridad — `ciberseguridad/`

Trabajos orientados a la defensa y protección de sistemas, redes y datos, organizados en cuatro bloques:

| Bloque | Contenido |
|---|---|
| [`Análisis Forense Informático/`](ciberseguridad/Análisis%20Forense%20Informático) | Informes periciales sobre AWS Cloud, dispositivos móviles, IoT, Microsoft 365 y entornos Windows. |
| [`Gestión e Investigación de Incidentes de Ciberseguridad/`](ciberseguridad/Gestión%20e%20Investigación%20de%20Incidentes%20de%20Ciberseguridad) | OSINT, Maltego, escaneo, monitorización, HIDs/NIDs y playbooks de MITRE ATT&CK. |
| [`Normativa/`](ciberseguridad/Normativa) | ENS y CLARA, declaración de aplicabilidad con PILAR. |
| [`Puesta en Producción Segura/`](ciberseguridad/Puesta%20en%20Producción%20Segura) | Análisis de malware, inyección de código y pruebas de intrusión web con OWASP. |

📄 Más detalle en [`ciberseguridad/README.md`](ciberseguridad/README.md)

---

## 🎓 Sobre este repositorio

Este portfolio recoge un recorrido formativo completo: desde los fundamentos de la programación orientada a objetos y la concurrencia, pasando por el desarrollo de interfaces de escritorio y aplicaciones móviles, hasta la defensa y análisis de sistemas en el ámbito de la ciberseguridad. Cada carpeta incluye su propio README con el detalle técnico del proyecto correspondiente.