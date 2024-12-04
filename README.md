# Investigación Marina
Creada por Manuel Amaya Orozco

---

## **Idea del Proyecto**

Como idea principal, voy a hacer una API basándome en la biología marina, en la que los Usuarios registrados son capaces de documentar acerca de los tipos de Peces que han sido descubiertos y posteriormente generar resúmenes de sus Investigaciones marinas.

## **Justificación del Proyecto**

Considero que esta API puede ser de mucha utilidad a la hora de guardar documentación tanto de peces como de las investigaciones realizadas por los diferentes usuarios, es flexible y se podría usar para muchos otros tipos de temes y no solo la biología marina.

## **Tablas**

1. **Tabla Usuario**
    - Representa un usuario que podrá acceder y modificar las bases de datos dependiendo de su rol.
    - Propiedades:
        - `id` **(Tipo: Long)**: El ID del usuario correspondiente, autogenerado por la base de datos.
        - `nombre` **(Tipo: String)**: El nombre del usuario.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `password` **(Tipo: String)**: La contraseña del usuario, se guarda hasheada en la base de datos.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `roles` **(Tipo: String)**: El rol del usuario, puede ser “USER” o “ADMIN”.
            **RESTRICCIÓN:**
                - No puede estar vacío.
                - Debe ser "USER" o "ADMIN".

2. **Tabla Pez**
    - Representa un pez registrado en la base de datos, estos peces son descubiertos por algún usuario y pueden aparecer en las investigaciones.
    - Propiedades:
        - `idPez` **(Tipo: Long)**: El ID del pez correspondiente, autogenerado por la base de datos.
        - `idInvestigador` **(Tipo: Long)**: El ID del usuario que descubrió este pez.
        - `nombreComun` **(Tipo: String)**: El nombre común del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `nombreCientifico` **(Tipo: String)**: El nombre científico del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `especie` **(Tipo: String)**: La especie del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `dieta` **(Tipo: String)**: La dieta del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `nombreComun` **(Tipo: String)**: Una breve descripción del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `ejemplares` **(Tipo: Int)**: El número de ejemplares vistos en libertad.
            **RESTRICCIÓN:**
                - No puede estar vacío.
                - Debe ser mayor que 0.
        - `tamMax` **(Tipo: Double)**: El tamaño máximo registrado del pez.
            **RESTRICCIÓN:**
                - No puede estar vacío.
                - Debe ser mayor que 0.
        - `peligroExtincion` **(Tipo: Boolean)**: Si el pez está en peligro de extinción o no.
            **RESTRICCIÓN:**
                - No puede ser null.

3. **Tabla Investigación**
    - Representa una investigación llevada a cabo por uno de los usuarios, estas investigaciones normalmente contienen una lista con los diferentes peces que se han visto durante ella.
    - Propiedades:
        - `idInvestigacion` **(Tipo: Long)**: El ID de la investigación correspondiente, autogenerado por la base de datos.
        - `idInvestigador` **(Tipo: Long)**: El ID del usuario que ha generado esta investigación.
        - `peces` **(Tipo: List(Pez))**: Una lista de los peces vistos durante la investigación.
        - `titulo` **(Tipo: String)**: El título de la investigación.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `resumen` **(Tipo: String)**: Resumen de la investigación y lo ocurrido durante ella.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `lugar` **(Tipo: String)**: Lugar donde se llevó a cabo la investigación.
            **RESTRICCIÓN:**
                - No puede estar vacío.
        - `fecha` **(Tipo: LocalDate)**: Fecha de la investigación.
            **RESTRICCIÓN:**
                - No puede ser null.
        - `hora` **(Tipo: LocalTime)**: Hora de la investigación.
            **RESTRICCIÓN:**
                - No puede ser null.
