# 🧵 Concurrencia en Python (Threading)

Conjunto de prácticas centradas en **programación concurrente en Python** con el módulo `threading`, explorando distintos mecanismos de sincronización: `Lock`, `Semaphore`, `Barrier`, `Timer` y comunicación entre procesos mediante `sockets`.

![Python](https://img.shields.io/badge/Python-3776AB?style=flat&logo=python&logoColor=white)
![Threading](https://img.shields.io/badge/Concurrencia-Threading-orange)

**Autora:** Alexandra Raluca Savu · 2º DAM

## 📁 Contenido

| Archivo / carpeta | Práctica | Mecanismo de sincronización |
|---|---|---|
| `Prestamos_Biblioteca.py` | Simulación de una biblioteca | `Lock` |
| `Caja_supermercado.py` | Simulación de un supermercado | `Semaphore`, `Barrier`, `Timer`, `Lock` |
| `Cliente_Servidor/` | Comunicación cliente-servidor | `socket` + `threading` |

---

## 📚 1. Simulación de una biblioteca (`Lock`)

Simula un sistema de préstamos y devoluciones de libros con **10 ejemplares disponibles**, atendidos por múltiples hilos concurrentes.

- **`Biblioteca`**: clase base con el nombre de la entidad.
- **`ClientePrestamo`**: hereda de `Biblioteca`; cada instancia corre en su propio hilo y solicita un libro prestado si hay stock disponible.
- **`ClienteDevolucion`**: hereda de `Biblioteca`; devuelve un libro si hay ejemplares prestados (stock por debajo de 10).

**Funcionamiento:**
- Las variables globales `libros_disponibles` y `usuarios_prestamo` se protegen con un `threading.Lock()` para evitar condiciones de carrera al modificarlas desde varios hilos a la vez.
- El programa principal lanza **15 hilos**, cada uno decidiendo aleatoriamente si representa un préstamo o una devolución, y espera a que todos terminen (`join()`) antes de mostrar el resultado final.
- Ambas clases gestionan errores con `try/except` (por ejemplo, intentar prestar sin stock o devolver sin préstamos activos).

## 🛒 2. Simulación de un supermercado (`Semaphore`, `Barrier`, `Timer`)

Simula un supermercado con un número limitado de cajas y un sistema de promociones que se activan y desactivan de forma periódica.

- **`threading.Semaphore(5)`**: limita a **5 cajas simultáneas**; cada cliente debe adquirir el semáforo antes de ser atendido y liberarlo al terminar.
- **`threading.Lock()`**: protege la variable global `promocion` frente a accesos concurrentes.
- **`threading.Timer`**: alterna automáticamente el estado de la promoción — `promo_activa()` la activa y programa su desactivación a los 5 segundos; `promo_inactiva()` la desactiva y programa su reactivación a los 10 segundos.
- **`threading.Barrier(15)`**: sincroniza a los 15 clientes para asegurar que todos han sido atendidos antes de considerar cerrado el supermercado.

**Funcionamiento:** el programa lanza un hilo daemon para gestionar el ciclo de promociones y **15 hilos** que representan clientes; cada cliente espera su turno en caja (semáforo), comprueba si hay promoción activa, simula un tiempo de atención aleatorio (1–3 s) y libera la caja al terminar.

## 🌐 3. Cliente-Servidor (`socket` + `threading`)

Implementación de un sistema **cliente-servidor con sockets TCP**, donde el servidor atiende múltiples clientes de forma concurrente, cada uno en su propio hilo.

### Servidor (`servidor.py`)
- Crea un socket TCP (`AF_INET`, `SOCK_STREAM`), lo vincula a `127.0.0.1:4500` y escucha hasta 5 conexiones en cola (`listen(5)`).
- Por cada conexión aceptada, lanza un hilo `hilo_Servidor` que atiende a ese cliente de forma independiente: recibe mensajes en bucle, responde con una confirmación, y cierra la conexión cuando el cliente deja de enviar datos.
- Usa un `Lock` para sincronizar la salida por consola entre los distintos hilos de clientes.

### Cliente (`cliente.py`)
- Cada cliente se modela como una clase `hilo_Cliente(threading.Thread)` que se conecta al servidor y le envía **3 mensajes**, esperando la respuesta de cada uno.
- El programa principal simula **5 clientes concurrentes**, cada uno en su propio hilo, con una pequeña espera aleatoria (1–3 s) entre mensajes.
- También usa un `Lock` para evitar que la salida por consola de distintos hilos se entremezcle.

**Funcionamiento conjunto:** al ejecutar primero el servidor y después el cliente, se establece una comunicación concurrente en la que el servidor gestiona simultáneamente varias conversaciones independientes, una por cliente, demostrando el modelo *thread-per-connection*.

---

## 🎓 Valor didáctico conjunto

Estas tres prácticas cubren, de forma progresiva, los mecanismos fundamentales de concurrencia en Python:

- **`Lock`** — exclusión mutua para proteger recursos compartidos (biblioteca, supermercado, consola).
- **`Semaphore`** — limitación del número de hilos que acceden a un recurso simultáneamente (cajas del supermercado).
- **`Barrier`** — sincronización de un grupo de hilos hasta que todos alcanzan un mismo punto (cierre del supermercado).
- **`Timer`** — ejecución diferida y periódica de tareas (activación/desactivación de promociones).
- **`socket` + `threading`** — comunicación en red entre procesos, con un servidor capaz de atender múltiples clientes de forma concurrente.
