El cliente se conectará al servidor en "localhost" y el puerto 6789 por defecto.

## Arquitectura y Características Principales

Este servidor de chat sigue un modelo de servidor-cliente. Algunas características clave incluyen:

- Manejo de múltiples clientes simultáneos.
- Mensajes de bienvenida y salida para los clientes.
- Detecta la desconexión del cliente y notifica a los demás usuarios.
- Cierre ordenado del servidor utilizando un "shutdown hook".
- Los clientes pueden enviar mensajes y recibir mensajes de otros clientes.

## Uso del Servidor de Chat con Telnet

Pasos para conectarte al servidor con Telnet.

1. Abre una terminal y ejecuta el siguiente comando para conectarte al servidor:
   
   telnet localhost 6789

2. Una vez conectado, deberías recibir un mensaje de bienvenida desde el servidor.

3. Ingresa tu nombre de usuario cuando se solicite.

4. A partir de este punto, puedes enviar mensajes al servidor escribiendo directamente en la terminal y presionando "Enter".

5. Si deseas salir del chat, puedes escribir "bye" y presionar "Enter".

6. La desconexión se manejará correctamente y se notificará a los demás usuarios.
