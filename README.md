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
        - `peces` **(Tipo: List\<Pez>\)**: Una lista de los peces vistos durante la investigación.
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

## **Endpoints**

1. **Endpoints para Usuarios**
    - **POST** `{/usuarios/login}`: Endpoint utilizado para hacer login con un usuario ya registrado, introduciendo su nombre y contraseña, devuelve un token válido de su sesión generada.
      - *RUTA PÚBLICA*: Cualquier usuario puede acceder a este endpoint.
    - **POST** `{/usuarios/register}`: Endpoint utilizado para generar un usuario nuevo, proveyendo sus datos en JSON.
      - *RUTA PÚBLICA*: Cualquier usuario puede acceder a este endpoint.

2. **Endpoints para Peces**
    - **GET** `{/peces/{idPez}}`: Endpoint utilizado para recibir la información de un pez específico, proveyendo el ID del pez en cuestión.
      - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso.
    - **GET** `{/peces/}`: Endpoint utilizado para recibir la información de todos los peces registrados en la base de datos.
      - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso.
    - **POST** `{/peces/}`: Endpoint utilizado para insertar un nuevo pez a la base de datos, proveyendo la información del pez adecuadamente en formato JSON.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
    - **PUT** `{/peces/{idPez}}`: Endpoint utilizado para actualizar un pez ya existente en la base de datos, proveyendo el ID del pez a actualizar así como sus nuevos datos en formato JSON.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
    - **DELETE** `{/peces/{idPez}}`: Endpoint utilizado para eliminar un pez ya existente en la base de datos, proveyendo el ID del pez a eliminar.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.

3. **Endpoints para Investigaciones**
    - **GET** `{/investigaciones/{idInvestigacion}}`: Endpoint utilizado para recibir la información de una investigación específica, proveyendo el ID de la investigación en cuestión.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
      - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` cuyo ID de usuario coincidan con el ID de usuario de la investigación pueden acceder a este recurso.
    - **GET** `{/investigaciones/}`: Endpoint utilizado para recibir la información de todas las investigaciones de la base de datos.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
    - **POST** `{/investigaciones/}`: Endpoint utilizado para insertar una nueva investigación a la base de datos, proveyendo la información de la investigación adecuadamente en formato JSON.
      - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso.
    - **PUT** `{/investigaciones/{idInvestigacion}}`: Endpoint utilizado para actualizar una investigación ya existente en la base de datos, proveyendo el ID de la investigación a actualizar así como sus nuevos datos en formato JSON.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
      - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` cuyo ID de usuario coincidan con el ID de usuario de la investigación pueden acceder a este recurso.
    - **DELETE** `{/investigaciones/{idInvestigacion}}`: Endpoint utilizado para eliminar una investigación ya existente en la base de datos, proveyendo el ID de la investigación a eliminar.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
      - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` cuyo ID de usuario coincidan con el ID de usuario de la investigación pueden acceder a este recurso.
