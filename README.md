# Evaluación 2 - PetsOnline

**PetsOnline** es una aplicación que permite a los usuarios gestionar el perfil de sus mascotas, servicios veterinarios, productos y reservas. La aplicación está diseñada para ofrecer una experiencia amigable y sencilla de interactuar con las funcionalidades esenciales para el cuidado de las mascotas.


- **Autenticación**: Los usuarios pueden registrarse, iniciar sesión y mantener su sesión iniciada.
- **Gestión de mascotas**: Los usuarios pueden registrar y eliminar sus mascotas.
- **Productos**: Visualiza productos disponibles para mascotas.
- **Servicios**: Los usuarios pueden consultar servicios veterinarios.
- **Reserva**: Los usuarios pueden realizar reservas para servicios.


La aplicación está construida usando **Jetpack Compose** para la interfaz de usuario y **MVVM** para la arquitectura. Los datos se almacenan localmente utilizando **DataStore** para persistir la sesión y las mascotas registradas. El backend de la aplicación está conectado a una **API REST** proporcionada por **Xano**, que maneja la autenticación y los datos de las mascotas.

- **Login y Registro**: Implementados con validaciones de formato de correo electrónico y seguridad de contraseña.
- **Pantallas de mascotas, productos, servicios y reservas**: Cada una con navegación independiente y validación de entradas.
- **Persistencia**: Uso de **DataStore** para guardar el token de sesión y mantener al usuario autenticado entre reinicios de la aplicación.


La aplicación interactúa con una API REST externa que maneja la autenticación, el perfil del usuario y otros datos relacionados con las mascotas:

1. **Login**: `POST /auth/login`
   - Requiere: correo electrónico y contraseña. 
   - Respuesta: Token de autenticación.


2. **Signup**: `POST /auth/signup`
   - Requiere: correo electrónico y contraseña.
   - Respuesta: Token de autenticación.


3. **Perfil de usuario**: `GET /auth/me`
   - Requiere: Header con token de autenticación.
   - Respuesta: Detalles del usuario (como correo electrónico).


- **Jetpack Compose**: Para construir la interfaz de usuario de manera declarativa.
- **MVVM**: Para mantener una arquitectura limpia y separada de la lógica de la UI.
- **Retrofit**: Para realizar las llamadas a la API REST.
- **DataStore**: Para persistir datos de manera eficiente y moderna.


1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/ladbrone/Evaluacion2_PetsOnline.git
