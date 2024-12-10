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
    - **GET** `{/usuarios/{idUser}}`: Endpoint utilizado para recibir la información de un usuario específico, proveyendo el ID del usuario en cuestión.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
      - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` cuyo ID de usuario coincidan con el ID del usuario a obtener pueden acceder a este recurso.
    - **GET** `{/usuarios/}`: Endpoint utilizado para recibir la información de todos los usuarios registrados en la base de datos.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
    - **POST** `{/usuarios/login}`: Endpoint utilizado para hacer login con un usuario ya registrado, introduciendo su nombre y contraseña, devuelve un token válido de su sesión generada.
      - *RUTA PÚBLICA*: Cualquier usuario puede acceder a este endpoint.
    - **POST** `{/usuarios/register}`: Endpoint utilizado para generar un usuario nuevo, proveyendo sus datos en JSON.
      - *RUTA PÚBLICA*: Cualquier usuario puede acceder a este endpoint.
    - **PUT** `{/usuarios/{idUser}}`: Endpoint utilizado para actualizar un usuario ya existente en la base de datos, proveyendo el ID del usuario a actualizar así como sus nuevos datos en formato JSON.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.
    - **DELETE** `{/usuarios/{idUser}}`: Endpoint utilizado para eliminar un usuario ya existente en la base de datos, proveyendo el ID del usuario a eliminar.
      - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente.

3. **Endpoints para Peces**
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

4. **Endpoints para Investigaciones**
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
     
## **Lógica de negocio**

1. **Tabla Usuario**

| Campo                   | Regla de Validación                                                       | Código HTTP  | Mensaje de Error                                    |
|-------------------------|---------------------------------------------------------------------------|--------------|-----------------------------------------------------|
| `id`                    | No puede estar vacío. (Excepto al hacer un POST o GETALL)                 | 400          | "El ID del usuario no puede estar vacío."           |
| `username`              | No puede estar vacío.                                                     | 400          | "El nombre del usuario no puede estar vacío."       |
| `password`              | No puede estar vacía.                                                     | 400          | "La contraseña del usuario no puede estar vacía."   |
| `roles`                 | Debe de ser “USER” o “ADMIN”.                                             | 400          | "El rol del usuario ha de ser USER o ADMIN."        |

2. **Tabla Pez**

| Campo                   | Regla de Validación                                                       | Código HTTP  | Mensaje de Error                                                                                                       |
|-------------------------|---------------------------------------------------------------------------|--------------|------------------------------------------------------------------------------------------------------------------------|
| `idPez`                 | No puede estar vacío. (Excepto al hacer un POST o GETALL).                | 400          | "El ID del pez no puede estar vacío."                                                                                  |
| `idInvestigador`        | No puede estar vacío./Ha de ser un ID válido.                             | 400/404      | "El ID del investigador no puede estar vacío."/"El ID introducido no coincide con ningún usuario de la base de datos." |
| `nombreComun`           | No puede estar vacío.                                                     | 400          | "El nombre común del pez no puede estar vacío"                                                                         |
| `nombreCientífico`      | No puede estar vacío.                                                     | 400          | "El nombre científico del pez no puede estar vacío."                                                                   |
| `especie`               | No puede estar vacía.                                                     | 400          | "La especie del pez no puede estar vacía."                                                                             |
| `dieta`                 | No puede estar vacía.                                                     | 400          | "La dieta del pez no puede estar vacía."                                                                               |
| `descripcion`           | No puede estar vacía.                                                     | 400          | "La descripción del pez no puede estar vacía."                                                                         |
| `ejemplares`            | Debe ser mayor que 0.                                                     | 400          | "El número de ejemplares del pez ha de ser mayor que 0."                                                               |
| `tamMax`                | Debe ser mayor que 0.                                                     | 400          | "El tamaño máximo del pez ha de ser mayor que 0."                                                                      |
| `peligroExtincion`      | No pueden ser null.                                                       | 400          | "El campo peligroExtincion no puede ser null."                                                                         |

3. **Tabla Investigacion**

| Campo                   | Regla de Validación                                                       | Código HTTP  | Mensaje de Error                                                                                                       |
|-------------------------|---------------------------------------------------------------------------|--------------|------------------------------------------------------------------------------------------------------------------------|
| `idInvestigacion`       | No puede estar vacío. (Excepto al hacer un POST o GETALL).                | 400          | "El ID de la investigación no puede estar vacío."                                                                      |
| `idInvestigador`        | No puede estar vacío./Ha de ser un ID válido.                             | 400/404      | "El ID del investigador no puede estar vacío."/"El ID introducido no coincide con ningún usuario de la base de datos." |
| `peces`                 | El pez debe de haber sido registrado previamente.                         | 404          | "No se ha encontrado uno de los peces dentro de la base de datos."                                                     |
| `titulo`                | No puede estar vacío.                                                     | 400          | "El título de la investigación no puede estar vacío."                                                                  |
| `resumen`               | No puede estar vacío.                                                     | 400          | "El resumen de la investigación no puede estar vacío."                                                                 |
| `lugar`                 | No puede estar vacío.                                                     | 400          | "El lugar de la investigación no puede estar vacío."                                                                   |
| `fecha`                 | No puede ser null.                                                        | 400          | "La fecha de la investigación no puede ser null."                                                                      |
| `hora`                  | No puede ser null.                                                        | 400          | "La hora de la investigación no puede ser null."                                                                       |

## **Códigos de Respuesta**

Operaciones exitosas:

- **201 Created**: Creación de recursos exitosos (POST excepto Login).
- **200 OK**: Consultas y actualizaciones exitosas (GET, PUT, Login POST)
- **204 No Content**: Eliminación de recursos exitosos (DELETE)

Operaciones fallidas:

- **400 Bad Request**: Errores de validación de la lógica de negocio.
- **404 Not Found**: Recursos inexistentes de la lógica de negocio.
- **500 Internal Server Error**: Cualquier otro error que ocurra dentro del servidor.


## **Restricciones de Seguridad**

1. **Usuarios:**
    - **GET** `{/usuarios/{idUser}}`: Solo los usuarios administradores tienen permitido ver todos los demás usuarios ya que son administradores, los usuarios normales solo tienen permitido ver su propia cuenta ya que si no se pondría en riesgo la información de los                                          demás usuarios.
    - **GET** `{/usuarios/}`: Solo los usuarios administradores tienen permitido ver todos los demás usuarios ya que son administradores, así no se pone en riesgo la información de los demás usuarios.
    - **POST** `{/usuarios/login}`: Cualquiera puede hacer login ya que es necesario para acceder a la aplicación.
    - **POST** `{/usuarios/register}`: Cualquiera puede registrarse ya que es necesario para acceder a la aplicación si no tienes una cuenta creada.
    - **PUT** `{/usuarios/{idUser}}`: Solo los usuarios administradores tienen permitido actualizar a otros usuarios, ya que si no estos podrían actualizar sus roles y volverse administradores sin permiso, por ello tendrán que pedir permiso a los administradores para                                         actualizar su información.
    - **DELETE** `{/usuarios/{idUser}}`: Solo los administradores tienen permitido eliminar otros usuarios ya que son administradores.

2. **Peces:**
    - **GET** `{/peces/{idPez}}`: Cualquier usuario autenticado tiene permitido ver los registros de los peces, ya que son necesarios para la creación de las investigaciones.
    - **GET** `{/peces/}`: Cualquier usuario autenticado tiene permitido ver los registros de los peces, ya que son necesarios para la creación de las investigaciones.
    - **POST** `{/peces/}`: Solo los administradores tienen permitido registrar nuevos peces, ya que tienen que verificar que el investigador correspondiente es en efecto el que haya descubierto esta nueva especie de pez, para así evitar falsificación de información.
    - **PUT** `{/peces/{idPez}}`: Solo los administradores tienen permitido actualizar la información de los peces ya que han de afirmar que la información obtenida es previamente verídica por parte del investigador.
    - **DELETE** `{/peces/{idPez}}`: Solo los administradores tienen permitido eliminar los registros de los peces, ya que solo ciertos motivos pueden ser válidos para eliminar alguno de estos registros.

3. **Investigaciones:**
    - **GET** `{/investigaciones/{idInvestigacion}}`: Los administradores pueden ver cualquiera de las investigaciones, mientras que los usuarios normales solo pueden acceder a las suyas propias por razones de seguridad y privacidad de la información.
    - **GET** `{/investigaciones/}`: Solo los administradores tienen permitido ver todas las investigaciones por razones de seguridad y privacidad de la información.
    - **POST** `{/investigaciones/}`: Cualquier usuario autenticado tiene permitido registrar una nueva investigación.
    - **PUT** `{/investigaciones/{idInvestigacion}}`: Los administradores tienen permitido actualizar cualquiera de las investigaciones, mientras que los usuarios normales solo pueden actualizar las suyas propias por razones de seguridad y privacidad de la información.
    - **DELETE** `{/investigaciones/{idInvestigacion}}`: Los administradores tienen permitido eliminar cualquiera de las investigaciones, mientras que los usuarios normales solo pueden eliminar las suyas propias para evitar sabotaje entre compañeros.
