# ClinicaApp - Sistema de Gestión de Turnos Médicos

Este proyecto es una aplicación de consola desarrollada en Java bajo el paradigma de Programación Orientada a Objetos (POO). Permite la gestión integral de pacientes, médicos y turnos de una clínica, garantizando la persistencia de los datos mediante archivos CSV.

## 👥 Información del Equipo
*   **Nombre del Equipo:** Desarrollo Independiente - Zully Tamayo
*   **Integrante:** Zully Tamayo

## 🚀 Instrucciones de Ejecución

Sigue estos pasos para correr el proyecto en **IntelliJ IDEA**:

### 1. Requisitos Previos
*   Tener instalado el **Java Development Kit (JDK)** versión 17 o superior.
*   Tener instalado **IntelliJ IDEA** (Community o Ultimate).

### 2. Apertura del Proyecto
1.  Descarga o clona este repositorio.
2.  Abre IntelliJ IDEA.
3.  Selecciona **File > Open** y busca la carpeta raíz del proyecto (`clinicaapp`).
4.  Asegúrate de que IntelliJ reconozca la carpeta `src` como la raíz del código fuente (debe aparecer en azul).

### 3. Configuración del SDK
1.  Ve a **File > Project Structure** (o presiona `Ctrl+Alt+Shift+S`).
2.  En la sección **Project**, asegúrate de que el **SDK** esté configurado (ej: Java 17).
3.  Haz clic en **OK**.

### 4. Ejecución
1.  Localiza el archivo `Main.java` en el paquete `co.generation.clinica`.
2.  Haz clic derecho sobre el archivo y selecciona **Run 'Main.main()'**.
3.  Interactúa con el sistema mediante el menú numérico en la consola.

---

## 🛠️ Funcionalidades Principales
*   **Gestión de Pacientes y Médicos:** Registro con validaciones (Regex y duplicados).
*   **Sistema de Turnos:** Asignación de citas evitando conflictos de agenda del médico.
*   **Persistencia CSV:** Los datos se guardan automáticamente en la carpeta `datos/` al salir del programa (Opción 0).
*   **Reporte HTML (Plus):** El sistema permite generar un reporte visual profesional en formato HTML para visualizar todos los turnos registrados.

## 📂 Estructura del Proyecto
*   `co.generation.clinica.model`: Entidades (Paciente, Medico, Turno) y Enums.
*   `co.generation.clinica.interfaces`: Contratos `Registrable` y `Consultable`.
*   `co.generation.clinica.service`: Lógica de negocio (`ClinicaService`).
*   `co.generation.clinica.datos`: Manejo de archivos (`DatosCSV`).
*   `Main.java`: Punto de entrada y menú de usuario.

---
*Hackathon - Generation Colombia*