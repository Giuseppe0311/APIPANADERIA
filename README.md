
# Panaderia Api Service

Backend para la aplicación de panadería, construido con Spring Boot (Java). Gestiona el registro de empresas, productos y más. Consulta la documentación en Swagger para entender cómo interactuar con la API.💻



## Enlace al Frontend
Para utilizar este backend, asegúrate de tener el frontend correspondiente instalado. Puedes encontrar el proyecto de frontend 👉[aquí](https://github.com/Giuseppe0311/panaderiaV2)👈. Sigue las instrucciones de instalación y configuración en el README del frontend.
## Tecnologias Usadas
- Spring Boot (Java) 🍃
- MySQL 🐬
# Lecciones Aprendidas

En el transcurso de este proyecto, he adquirido valiosas lecciones que han ampliado mi conjunto de habilidades y mejorado mi comprensión de varios aspectos. A continuación, se detallan algunas de las lecciones más significativas:

- Uso de Cloudinary para Subir Imágenes a la Nube

Uno de los logros destacados durante este proyecto fue aprender a utilizar Cloudinary para la gestión eficiente y segura de imágenes en la nube. Esta habilidad es esencial para proyectos que requieren almacenamiento y manipulación de imágenes, y ahora soy capaz de integrar este servicio en aplicaciones web de manera efectiva.

- Mejora en el Manejo de los Datos

A lo largo del desarrollo, he fortalecido mis habilidades en la manipulación de datos. Esto incluye la capacidad de trabajar con diversas estructuras de datos, desde listas y diccionarios hasta la gestión de datos en bases de datos. Este conocimiento sólido en el manejo de datos es esencial para el desarrollo de software eficiente y robusto.

- Entendimiento Básico de Query Params

Durante este proyecto, he adquirido conocimientos fundamentales sobre el uso de query params. Comprender cómo trabajar con los parámetros de consulta en las solicitudes HTTP me permite personalizar las respuestas de las aplicaciones de manera dinámica. Este conocimiento resulta valioso al desarrollar aplicaciones web interactivas y personalizadas.

---

Estas lecciones aprendidas no solo han contribuido al éxito de este proyecto específico, sino que también han ampliado mi conjunto de habilidades como desarrollador. Estoy emocionado de aplicar estas lecciones en proyectos futuros y seguir aprendiendo y creciendo en mi carrera.




## Consideraciones

Antes de comenzar a interactuar con la API, ten en cuenta las siguientes consideraciones:

Registro: Para acceder a la API, primero debes registrarte en la plataforma. Utiliza el proceso de registro proporcionado en el frontend para crear tu cuenta.

Inicio de Sesión: Después de registrarte, inicia sesión en la plataforma. Esto te proporcionará un token de autenticación que necesitarás para realizar solicitudes a la API.

Token en Postman: Si deseas probar las solicitudes en Postman, asegúrate de incluir el token de autenticación en la cabecera de tus solicitudes. Utiliza el token generado durante el inicio de sesión para autorizar tus peticiones.

Almacenamiento del Token: El token de autenticación estará disponible en el Session Storage después de iniciar sesión. Asegúrate de recuperar y utilizar este token para cada solicitud a la API.

## Documentación
Consulta la documentación completa de la API en Swagger 👉[aquí](https://apipanaderia-production.up.railway.app/swagger-ui/index.html)📖 , Puedes explorar los endpoints en postman, probar las solicitudes y obtener información detallada sobre cada recurso. ¡No dudes en ponerte en contacto si necesitas ayuda!
## Instalación
1. Clona el repositorio.
2. Abre el proyecto en tu entorno de desarrollo preferido.
3. Configura la base de datos MySQL.
4. Ejecuta la aplicación Spring Boot.
```bash
mvn spring-boot:run
