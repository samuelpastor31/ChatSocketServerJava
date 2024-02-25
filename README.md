Proyecto Final: Servidor de Chat Concurrente en Java

Descripción
En este proyecto, desarrollarás un servidor de chat concurrente en Java que permitirá a múltiples usuarios conectarse y comunicarse en tiempo real. Este servidor debe ser capaz de manejar conexiones simultáneas, gestionar clientes desconectados y enviarles los mensajes que se hayan producido durante su ausencia, optimizar el manejo de hilos en función de los núcleos del procesador y cerrar de manera ordenada los hilos que atienden a los clientes al recibir una señal de terminación del sistema operativo.

Objetivos
Implementar un servidor de chat utilizando sockets y concurrencia en Java.
Gestionar múltiples conexiones de cliente de manera eficiente utilizando ThreadPoolExecutor.
Almacenar mensajes para clientes desconectados y entregarlos cuando se reconecten.
Detectar la señal SIGTERM (kill <número_proceso>) para cerrar el servidor de forma ordenada y notificar a los clientes conectados.
Requisitos
Servidor de Chat:
Debe aceptar conexiones de múltiples clientes utilizando ServerSocket.
Utiliza ThreadPoolExecutor para manejar las conexiones de clientes en hilos separados. El tamaño del pool debe basarse en el número de núcleos de CPU disponibles.
Mantén un registro de todos los clientes conectados y gestiona adecuadamente sus conexiones y desconexiones.
Almacenamiento de Mensajes:
Implementa una estructura de datos (por ejemplo, ConcurrentHashMap) para almacenar mensajes destinados a clientes desconectados.
Cuando un cliente se reconecta, envíale todos los mensajes acumulados durante su ausencia.
Manejo de SIGTERM:
Implementa un mecanismo para detectar cuando se recibe la señal SIGTERM y cierra el servidor de manera ordenada.
Antes de cerrar, el servidor debe notificar a todos los clientes conectados sobre el cierre inminente.
Mostrar mensajes de Debug:
Ha de mostrar un mensaje cada vez que se conecta un cliente indicando el número de clientes actualmente conectados.
Ha de mostrar un mensaje cada vez que un cliente se desconecte indicando el número de clientes actualmente conectados.
Ha mostrar un mensaje cuando se vaya a apagar indicándolo.
Clientes:
Deben poderse conectar utilizando el programa telnet indicando la IP y el número de puerto.
Cuando nos conectamos al servidor, opcionalmente se puede mostrar un mensaje de bienvenida indicando que la siguiente línea será el nick de usuario con el que nos conectaremos al chat.
En caso de que el servidor tenga mensajes guardados para ese usuario, se le enviarán en ese momento.
A partir de ese momento, empezaremos a recibir los mensajes del resto de usuarios y podremos enviar mensajes.
cuando deseemos finalizar la sesión, escribiremos la palabra bye y finalizará nuestra conexión.
