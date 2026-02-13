# Restaurant Order Management System

Sistema completo de gestión de pedidos para restaurantes con frontend Angular 21 y backend Spring Boot.

## 🚀 Inicio Rápido con Docker

### Requisitos Previos
- Docker Desktop instalado
- Docker Compose instalado

### Levantar Todo el Sistema

```bash
# Desde la raíz del proyecto
docker-compose up --build
```

Esto levantará:
- **Frontend Angular**: http://localhost (puerto 80)
- **Backend Spring Boot**: http://localhost:3000 (puerto 3000)
- **Base de Datos**: PostgreSQL remota en 51.222.142.204:6432

### Detener el Sistema

```bash
docker-compose down
```

---

## 📦 Estructura del Proyecto

```
restApp/
├── frontend/           # Angular 21 Application
│   ├── src/
│   ├── Dockerfile
│   ├── nginx.conf
│   └── package.json
├── backend/            # Spring Boot Application
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
└── docker-compose.yml  # Configuración de Docker Compose
```

---

## 🗄️ Base de Datos

### Conexión PostgreSQL Remota

```
Host: 51.222.142.204
Port: 6432
Database: complements-angular
User: complements-angular
Password: Riwi123+
```

La base de datos está configurada en el `docker-compose.yml` y se conecta automáticamente.

### Tablas Necesarias

El backend usa JPA con `ddl-auto=update`, por lo que creará las tablas automáticamente:

- `users` - Usuarios del sistema
- `products` - Catálogo de productos
- `orders` - Pedidos
- `order_items` - Items de cada pedido

---

## 🔧 Desarrollo Local (Sin Docker)

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

El backend estará disponible en: http://localhost:8080

### Frontend

```bash
cd frontend
npm install
npm start
```

El frontend estará disponible en: http://localhost:4200

---

## 🌐 Endpoints del API

### Autenticación
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión

### Productos
- `GET /api/products` - Listar productos
- `POST /api/products` - Crear producto (ADMIN)
- `PUT /api/products/:id` - Actualizar producto (ADMIN)
- `DELETE /api/products/:id` - Eliminar producto (ADMIN)

### Pedidos
- `GET /api/orders` - Listar pedidos
- `POST /api/orders` - Crear pedido
- `PUT /api/orders/:id/status` - Actualizar estado (ADMIN)
- `PUT /api/orders/:id/cancel` - Cancelar pedido (USER)

---

## 👥 Roles de Usuario

### USER (Cliente)
- Ver productos activos
- Agregar productos al carrito
- Realizar pedidos
- Ver sus propios pedidos
- Cancelar pedidos pendientes

### ADMIN (Administrador)
- Todas las funciones de USER
- Gestionar productos (CRUD completo)
- Ver todos los pedidos
- Actualizar estado de pedidos
- Dashboard con estadísticas

---

## 🔐 Seguridad

- Autenticación JWT
- Tokens almacenados en localStorage
- Interceptor automático para agregar token a requests
- Guards para proteger rutas
- CORS configurado para desarrollo

---

## 🐳 Docker Compose

### Servicios

#### Backend
- Puerto: 3000 (mapeado desde 8080 interno)
- Health check configurado
- Variables de entorno para DB y JWT

#### Frontend
- Puerto: 80
- Nginx como servidor web
- Depende del backend (espera health check)

### Comandos Útiles

```bash
# Ver logs
docker-compose logs -f

# Ver logs solo del backend
docker-compose logs -f backend

# Ver logs solo del frontend
docker-compose logs -f frontend

# Reconstruir solo un servicio
docker-compose up --build backend

# Detener y eliminar volúmenes
docker-compose down -v
```

---

## 🛠️ Troubleshooting

### El frontend no carga
1. Verificar que el backend esté corriendo: `docker-compose logs backend`
2. Verificar health check: `docker-compose ps`
3. Revisar logs de nginx: `docker-compose logs frontend`

### Error de conexión a la base de datos
1. Verificar que la IP remota sea accesible
2. Revisar credenciales en `docker-compose.yml`
3. Verificar logs del backend: `docker-compose logs backend`

### Cambios en el código no se reflejan
```bash
# Reconstruir las imágenes
docker-compose up --build

# O reconstruir sin caché
docker-compose build --no-cache
docker-compose up
```

---

## 📝 Variables de Entorno

### Backend (docker-compose.yml)
```yaml
DATABASE_URL: jdbc:postgresql://51.222.142.204:6432/complements-angular
DATABASE_USER: complements-angular
DATABASE_PASSWORD: Riwi123+
JWT_SECRET: daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb
```

### Frontend
Las URLs del API están configuradas en:
- `src/environments/environment.ts` (producción)
- `src/environments/environment.development.ts` (desarrollo)

---

## 🎯 Próximos Pasos

1. **Primer inicio**:
   ```bash
   docker-compose up --build
   ```

2. **Acceder a la aplicación**:
   - Abrir http://localhost en el navegador
   - Registrar un usuario
   - Iniciar sesión

3. **Crear productos** (como ADMIN):
   - Necesitarás crear un usuario ADMIN directamente en la base de datos
   - O modificar el código para permitir registro como ADMIN

4. **Probar flujo completo**:
   - Ver productos
   - Agregar al carrito
   - Realizar pedido
   - Ver pedidos

---

## 📄 Licencia

Este proyecto es para fines educativos.

---

## 👨‍💻 Soporte

Para problemas o preguntas, revisar los logs:
```bash
docker-compose logs -f
```
