# Aplicación de Tienda en Línea de Informáticos


## Descripción de la Aplicación

La aplicación de tienda en línea de informáticos es una plataforma móvil diseñada para ofrecer una experiencia de compra fluida y conveniente para encontrar los productos que satisfagan las necesidades y requerimientos específicos de cada usuario en el ámbito de la informática y la tecnología. La aplicación integrará un chatbot de asistencia para proporcionar ayuda personalizada y respuestas rápidas a las consultas de los usuarios.

### Lenguaje de Programación:
- Kotlin

### Arquitectura:
- Arquitectura de Componentes (ViewModel, LiveData)
- Patrón de Diseño MVVM (Modelo-Vista-VistaModelo)

### Navegación:
- Componente de Navegación de Android

### Almacenamiento de Datos:
- Biblioteca de Persistencia de Datos ROOM para SQLite
- Corrutinas y Flow para operaciones asíncronas
- Uso de LiveData para reactividad en la interfaz de usuario

### Servicios Web:
- Biblioteca Retrofit para consumo de servicios web RESTful
- Posiblemente otra librería para manejar la comunicación con el servidor

### Interfaz de Usuario:
- XML para diseño de interfaces de usuario (layouts)
- RecyclerView para listados de productos y valoraciones
- Fragments para modularizar la interfaz de usuario y facilitar la reutilización de componentes
- Personalización de la barra de herramientas con menús y opciones de navegación
- Uso de NavigationView para la navegación entre diferentes secciones de la aplicación

### Integración de Funcionalidades:
- Utilización de servicios del dispositivo como cámara, GPS, envío o recepción de SMS, calendario y acelerómetro según sea necesario
- Integración de un chatbot de asistencia para proporcionar ayuda a los usuarios durante la experiencia de compra

## Estructura de Carpetas y Archivos

El proyecto estará organizado de la siguiente manera:
- **java**
  - **com.zy.proyecto_final**
    - **activities**
    - **adapters**
    - **dao**
    - **fragments**
    - **pojo**
    - **repositories**
    - **retrofit**
    - **util**
- **res**
  - **layout**
  - **menu**
  - **navigation**
  - **values**

## Funcionamiento General

Ordenado por su prioridad de desarrollo:

1. **Inicio de Sesión/Registro:**
   - Los usuarios pueden crear una cuenta nueva o iniciar sesión en una cuenta existente para acceder a todas las funcionalidades de la aplicación.
   - Proporcionar opciones de inicio de sesión con correo electrónico

2. **Exploración de Productos:**
   - Navegar por la amplia variedad de productos disponibles en la tienda.
   - Utilizar filtros y categorías para facilitar la búsqueda de productos específicos.

3. **Selección y Compra:**
   - Los usuarios pueden seleccionar productos y agregarlos al carrito de compras.
   - Proporcionan formas de pago seguras y convenientes.

4. **Gestión de Pedidos:**
   - Visualizar el historial de pedidos.
   - Realizar un seguimiento del estado de los envíos.


