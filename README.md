# OctanoGT — Sistema Web para la Gestión de Ventas de Combustible y Asistencia de Trabajadores de una Gasolinera

**Avance 2** — Proyecto funcional con **Spring Boot + Thymeleaf**. Todas las interfaces CRUD son funcionales, pero **no se conecta a base de datos**: los datos viven en memoria (listas Java dentro de los `@Service`) y se reinician cada vez que se reinicia la aplicación. No se implementó Spring Security, JWT ni JPA todavía, ni el módulo de métricas (fuera del alcance de este avance).

## Requisitos para ejecutarlo

- **Java 17** o superior (JDK).
- **Maven 3.8+** (o usar el wrapper `mvnw` si lo agregas con `mvn -N wrapper:wrapper`).
- Conexión a internet la primera vez, para que Maven descargue las dependencias de Spring Boot desde Maven Central.

## Cómo ejecutar el proyecto localmente

1. Descomprime el proyecto y entra a la carpeta:
   ```bash
   cd gasolinera-app
   ```
2. Ejecuta con Maven:
   ```bash
   mvn spring-boot:run
   ```
   (o, si prefieres compilar primero: `mvn clean package` y luego `java -jar target/gasolinera.jar`)
3. Abre el navegador en:
   ```
   http://localhost:8080/login
   ```
4. Inicia sesión con las credenciales simuladas:
   - **Usuario:** `admin`
   - **Contraseña:** `admin123`

   (La autenticación real con Spring Security + JWT se implementará en un avance posterior; por ahora solo valida estas credenciales fijas para simular el control de acceso).

### Desde un IDE (IntelliJ IDEA / Eclipse / VS Code)

Importa el proyecto como **Maven Project** y ejecuta la clase `GasolineraApplication` (tiene un método `main`).

## Estructura del proyecto

```
gasolinera-app/
├── pom.xml
└── src/main/
    ├── java/com/octanogt/gasolinera/
    │   ├── GasolineraApplication.java     → Clase principal
    │   ├── model/                          → Entidades (POJOs): Usuario, Trabajador, Asistencia,
    │   │                                      Categoria, Producto, MovimientoInventario, Cliente,
    │   │                                      Venta, DetalleVenta
    │   ├── dto/                            → Comandos de formulario: LoginForm, VentaForm, DetalleVentaForm
    │   ├── service/                        → Lógica de negocio y "repositorios" en memoria (sin BD)
    │   └── controller/                     → Controladores Spring MVC (uno por módulo)
    └── resources/
        ├── application.properties
        ├── static/
        │   ├── css/style.css               → Estilos (mismo diseño del Avance 1)
        │   └── js/script.js                 → Interacciones de frontend
        └── templates/
            ├── fragments/layout.html        → Sidebar, topbar, alertas y footer reutilizables
            ├── login.html, index.html, gestion.html, contacto.html, publicidad.html,
            │   metricas.html, inventario.html, reportes.html
            └── usuarios/, trabajadores/, categorias/, productos/, clientes/,
                movimientos/, ventas/, asistencia/   → list.html / form.html de cada módulo
```

## Módulos funcionales (CRUD real, sin base de datos)

| Módulo | Ruta principal | Operaciones |
|---|---|---|
| Usuarios | `/usuarios` | Listar, crear, editar, eliminar |
| Trabajadores | `/trabajadores` | Listar, crear, editar, eliminar |
| Asistencia | `/asistencia` | Listar, registrar entrada, registrar salida |
| Categorías | `/categorias` | Listar, crear, editar, desactivar (no se eliminan físicamente) |
| Productos y combustibles | `/productos` | Listar, crear, editar, eliminar |
| Inventario | `/inventario` | Consulta de stock y alertas (solo lectura) |
| Movimientos de inventario | `/movimientos` | Listar, registrar (ajusta el stock automáticamente) |
| Clientes | `/clientes` | Listar, crear, editar, eliminar |
| Ventas | `/ventas/nueva` | Registrar venta (hasta 5 productos, calcula total y descuenta stock) |
| Historial de ventas | `/ventas/historial` | Listar, ver detalle, anular |
| Reportes | `/reportes` | Resumen con enlaces a cada módulo |
| Métricas | `/metricas` | Vista informativa (funcionalidad prevista para un avance posterior) |
| Contacto | `/contacto` | Formulario funcional (envío simulado) |
| Publicidad | `/publicidad` | Página estática con carrusel |

## Notas importantes

- **Sin base de datos:** los datos se guardan en listas dentro de las clases `@Service` (anotadas con `@PostConstruct` para precargar datos de ejemplo). Al reiniciar la aplicación, los datos vuelven a su estado inicial.
- **Sin Spring Security / JWT:** el login solo compara usuario y contraseña contra un valor fijo en `AuthController`. La sesión "activa" (nombre e ícono en la barra superior) está fija como "Marcos Ramírez — Administrador" en la plantilla, ya que todavía no hay manejo real de sesión de usuario.
- **Sin métricas:** la pantalla `/metricas` existe solo para mantener la navegación completa del sistema, tal como se definió en el Avance 1, pero no calcula ni muestra datos reales, conforme a lo solicitado para este avance.
- **Compilación:** este código fue escrito y revisado cuidadosamente (sintaxis Java, nombres de propiedades Thymeleaf, coincidencia entre controladores y plantillas), pero no pudo compilarse dentro de este entorno porque no tiene acceso a Maven Central para descargar las dependencias de Spring Boot. Se recomienda compilarlo y probarlo en tu equipo con `mvn spring-boot:run` antes de la entrega.

## Reglas de negocio implementadas en el código

- **R1** Al registrar una venta se descuenta automáticamente el stock de cada producto vendido.
- **R2** No se permite vender una cantidad mayor al stock disponible.
- **R3** Las categorías no se eliminan; solo se desactivan.
- **R4** La salida de un trabajador solo puede registrarse si tiene una entrada previa.
- **R5** Toda venta requiere un trabajador responsable y un método de pago.
- **R6** Al anular una venta pagada se restituye el stock de sus productos.
