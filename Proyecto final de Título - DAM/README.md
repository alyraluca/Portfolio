# 🛒 PriceOn

**Trabajo Fin de Título** — Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM), 2º Curso
**Autora:** Savu, Raluca Alexandra · **Tutor:** Oliver Cano, Miguel

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=firebase&logoColor=black)
![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)

## 📋 Descripción

PriceOn es una aplicación Android que permite comparar precios de productos entre distintos supermercados, pensada para ayudar a los usuarios a tomar decisiones de compra más informadas en un contexto de encarecimiento de los productos básicos.

El proyecto nace con un enfoque **ético, colaborativo y sin ánimo de lucro**: es de código abierto, no incluye publicidad ni comisiones ocultas, y depende de la colaboración de la comunidad para mantener los precios actualizados.

## ✨ Funcionalidades principales

- 🔍 **Búsqueda de productos** por nombre o mediante **escaneo de código de barras** (CameraX + ML Kit).
- 💰 **Comparación de precios** entre supermercados, mostrando siempre el precio más barato disponible.
- 📊 **Evolución de precios** en el tiempo mediante gráficas interactivas (MPAndroidChart).
- ⭐ **Lista de favoritos** e **historial de búsqueda** por usuario.
- 📍 **Supermercados cercanos**, calculados a partir de la ubicación del usuario.
- 🔐 **Sistema de roles**: usuario invitado, usuario registrado, moderador (puede actualizar precios) y administrador (puede añadir nuevos productos).
- 🔑 **Autenticación** de usuarios con Firebase Authentication.

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| **Java** | Lenguaje principal de desarrollo |
| **Android Studio** | Entorno de desarrollo |
| **XML** | Definición declarativa de las interfaces |
| **Firebase Authentication** | Registro y login de usuarios |
| **Cloud Firestore** | Base de datos NoSQL en la nube |
| **ML Kit Barcode Scanning** | Escaneo de códigos de barras en el dispositivo |
| **CameraX** | Integración de la cámara |
| **MPAndroidChart** | Gráficas de evolución de precios |
| **Glide** | Carga y cacheo eficiente de imágenes |
| **GitHub** | Control de versiones y colaboración |

## 🏗️ Arquitectura

La aplicación sigue el modelo clásico de Android basado en **Activities** como controladores de pantalla, apoyado en:

- **UI declarativa (XML) + Activities**, cada pantalla enlaza su layout y gestiona su lógica en `onCreate()`.
- **Navegación mediante Intents**, con `BottomNavigationView` para las secciones principales (inicio, escáner, favoritos) y una barra superior para perfil y añadir producto.
- **Patrón Adaptador** (`RecyclerView` + `Adapter` + `ViewHolder`) para listas de productos, favoritos e historial.
- **Acceso asíncrono a datos** mediante callbacks (`addOnSuccessListener` / `addOnFailureListener`) con Firebase Firestore.
- **Backend serverless en Firebase**: Authentication para usuarios y Firestore como base de datos principal, organizada en colecciones (`products`, `users`, `supermarkets`, `brands`, `productTypes`, `productSupermarket`) y subcolecciones (`favouriteProducts`, `searchHistory`, `locations`, `priceUpdate`).

## 👥 Roles de usuario

| Rol | Permisos |
|---|---|
| **Invitado** | Buscar productos por nombre o código de barras |
| **Usuario registrado** | Todo lo anterior + favoritos, historial y supermercados cercanos |
| **Moderador** | Todo lo anterior + actualizar precios de productos |
| **Administrador** | Todo lo anterior + añadir nuevos productos a la base de datos |


## 📈 Estado del proyecto

Los objetivos planteados se han cumplido en su totalidad: búsqueda por nombre y código de barras, comparación y evolución de precios, gestión de usuarios/favoritos/historial, y un modelo colaborativo y open-source publicado en GitHub.

### 🔮 Posibles mejoras futuras

- Migrar el almacenamiento de imágenes a Firebase Cloud Storage.
- Panel de administración para gestión dinámica de roles y solicitudes de moderación.
- Flujo de revisión previa antes de aplicar una actualización de precio.
- Integración con fuentes de datos externas (APIs o scraping) para automatizar la obtención de precios.

## ▶️ Cómo ejecutarlo

1. Clona el repositorio.
2. Ábrelo con Android Studio.
3. Conecta tu propio proyecto de Firebase (Authentication + Firestore) y añade el archivo `google-services.json` correspondiente.
4. Compila y ejecuta en un emulador o dispositivo físico (Android 7.0 / API 24 o superior).

---

## Licencia

![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)

Este proyecto está bajo la Licencia MIT.

### Resumen de la Licencia MIT

Permiso es concedido, libre de cargos, a cualquier persona que obtenga una copia
de este software y de la documentación asociada, para usarlo sin restricción,
incluyendo sin limitación los derechos de usar, copiar, modificar, fusionar,
publicar, distribuir, sublicenciar y/o vender copias del software, y para permitir
a las personas a las que se les proporcione el software que lo hagan, sujeto a
las siguientes condiciones:

> La obligación de incluir el aviso de copyright y esta
> declaración de permiso en todas las copias o partes sustanciales del software.

EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA
O IMPLÍCITA, INCLUYENDO PERO NO LIMITADO A GARANTÍAS DE COMERCIABILIDAD,
IDONEIDAD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO LOS
AUTORES O TITULARES DE LOS DERECHOS DE AUTOR SERÁN RESPONSABLES DE NINGUNA
RECLAMACIÓN, DAÑO O OTRA RESPONSABILIDAD, YA SEA EN UNA ACCIÓN DE CONTRATO,
AGRAVIO U OTRA FORMA, QUE SURJA DE, FUERA DE O EN CONEXIÓN CON EL SOFTWARE
O EL USO U OTROS TRATOS EN EL SOFTWARE.