# Pr-ctica-de-laboratorio-04-Servicios-Web

* 1.Crear un repositorio en GitHub con el nombre “Práctica de laboratorio 04: Servicios Web”

  * Creación del proyecto Práctica de laboratorio 04: Servicios Web
  
  ![image](https://user-images.githubusercontent.com/49213346/124577541-fb30f780-de12-11eb-838c-14c944a23fb6.png)

Repositorio creado solo es cuestión de unir la rama remota , con nuestro proyecto que lo tenemos localmente

![image](https://user-images.githubusercontent.com/49213346/124577617-0c7a0400-de13-11eb-83b9-0e343ed7e852.png)

Proyecto ya creado con la parte local y remota 

![image](https://user-images.githubusercontent.com/49213346/124577647-16036c00-de13-11eb-9ce7-04ac7d0fda7d.png)

* 2.Desarrollar una aplicación con tecnología JEE para gestionar pedidos a una distribuidora de productos para el hogar a través de servicios web.

Con base a la Practica de laboratorio 03 - EJB - JSF – JPA se pide aumentar las funcionalidades de la aplicación JEE usando Web Services Restful. Los servicios web deben permitir:

* Gestión de cuentas de usuario de los Clientes de la distribuidora
  * Iniciar Sesión con base a un usuario y contraseña

Para realizar el servicio rest de login necesitamos pasar lo que es el correo y la contraseña , a través de un formulario se enviara la solicitud POST. Observamos que el usuario Ingreso con éxito y nos devuelve un registro en json

![image](https://user-images.githubusercontent.com/49213346/124577856-4814ce00-de13-11eb-9d5d-4f9a3cb9d87d.png)

Figura 1. Servicio loginUsuario mediante la petición POST

  * Registrar cuenta del cliente con base al número de cédula

![image](https://user-images.githubusercontent.com/49213346/124578085-80b4a780-de13-11eb-87a1-d7fc444d3889.png)

Figura 2. Creación de la cuenta del cliente mediante la solicitud de petición PUT

  * Modificar datos de la cuenta y personales del cliente

Se realizo la petición PUT y se le paso como parámetros que se pueden actualizar como : id , correo , usuario, cedula , oldPassword , newPassword , confirmPassword , nombre y apellido.


![image](https://user-images.githubusercontent.com/49213346/124578167-94f8a480-de13-11eb-825b-39b78c3ccecd.png)

Figura 3. Modificación de datos mediante los parámetros del usuario mediante la solicitud PUT

  * Anular cuenta del cliente. (eliminado lógico)
Aquí podemos observar que se uso una solicitud DELETE para realizar el eliminado lógico de la cuenta del cliente , el cual nos pide un parámetro que es el id del cliente para poder eliminar , se envia un estado de 1 para el eliminado logico

![image](https://user-images.githubusercontent.com/49213346/124578261-a8a40b00-de13-11eb-9484-56024a779006.png)

Figura 4. Anulación de la cuenta del cliente mediante la solicitud DELETE

* Gestión de Pedidos
  * Listar productos del catálogo organizados por categorías con base a la selección de una bodega.

Observamos la solicitud GET que se realizo para obtener la lista de productos organizados por categoría según la bodega que se escoja , en este caso se escogió la bodega con id 1 y la categoría con id 1

![image](https://user-images.githubusercontent.com/49213346/124578366-c40f1600-de13-11eb-8fde-e9f821dac538.png)

Figura 5. Lista de productos organizado por categorías según la selección de una bodega

  * Enviar la solicitud de un pedido a la distribuidora

![image](https://user-images.githubusercontent.com/49213346/124578420-d0936e80-de13-11eb-85cd-fc9d4b2954b3.png)

Figura 6. Solicitud a través de POSTMAN el cual se realiza una petición POST para crear un pedido

  * Revisar el estado de los pedidos del cliente.

Observamos los pedidos que tiene el cliente con id 1 , realizamos la consulta a través de una petición GET y pasando como parámetros a través de la url el código del usuario

![image](https://user-images.githubusercontent.com/49213346/124578513-e5700200-de13-11eb-8021-1a7a15d12915.png)

Figura 7. Pedidos del cliente con id 1


## RESULTADO(S) OBTENIDO(S):

o	Manipulación  de Objetos Json para la serialización y deserialización
o	Manejo del API JSON-B con la aplicación java 
o	Manero de API RESTFULL , con las peticiones POST, PUT , DELETE , GET 
o	Manejo de Respuesta HTTP a través de solicitudes POST , PUT ,DELETE , GET

## CONCLUSIONES:

Se llego a construir una aplicación web  a nivel empresarial y que dispone de varios servicios , a una lógica que esta separada en una parte de negocios y que posee también reglas de negocio como seria un descuento a nivel de base de datos.

Nos sirvió de mucho POSTMAN para saber si estaba haciendo bien las solicitudes y también saber si servían como servicios RESTFULL

## RECOMENDACIONES:


o	Revisar las diapositivas de la materia 
o	Saber sobre java Web
o	Manipular Base de Datos
o	Saber manipular objetos Json
o	Configurar correctamente el pool de conexión para que no haya problemas con errores del servidor Glassfish




