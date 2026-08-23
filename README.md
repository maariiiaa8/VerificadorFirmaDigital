# Verificador de Firma Digital

Aplicación cliente-servidor en Java que demuestra el funcionamiento de la firma digital: el servidor firma un mensaje con una clave privada RSA, y el cliente verifica la autenticidad de esa firma usando la clave pública correspondiente. Comunicación por sockets TCP, con una interfaz de terminal personalizada tipo consola de hacking.

## Funcionalidades

- Generación de un par de claves RSA (2048 bits) en el servidor
- Firma digital de mensajes con `SHA256withRSA`
- Envío de clave pública, mensaje y firma al cliente vía sockets
- Verificación de la firma en el cliente con la clave pública recibida
- Gestión de múltiples clientes en cola (productor-consumidor con `BlockingQueue`)
- Salida de terminal estilizada (cabeceras, logs con formato propio)

## Cómo funciona

1. El **servidor** arranca y espera conexiones en el puerto `5000`
2. Cada **cliente** se conecta, se identifica con un nombre y queda en cola
3. Desde el servidor, se escribe un mensaje para el cliente en cola
4. El servidor genera un par de claves RSA, firma el mensaje con la clave privada, y envía al cliente: la clave pública, el mensaje y la firma
5. El cliente verifica la firma con la clave pública recibida y muestra si es **válida** o **inválida**

## Stack tecnológico

- **Java 23**
- Sockets TCP (`java.net`)
- Java Security API (`KeyPairGenerator`, `Signature`, RSA)
- Concurrencia con `BlockingQueue` e hilos (`Thread`)
- Maven

## Arquitectura

```
servidor/
  Servidor.java        → acepta conexiones, gestiona cola de clientes
  GestorClientes.java  → representa un cliente conectado
  GestorFirma.java      → genera claves RSA y firma mensajes

cliente/
  Cliente.java             → se conecta, recibe clave/mensaje/firma
  VerificadorFirma.java    → verifica la firma con la clave pública

util/
  Terminal.java         → utilidades de formato de salida por consola
```

## Puesta en marcha

Requisitos: Java 23, Maven.

```bash
# Compilar
mvn clean install
```

Ejecuta primero el servidor y luego uno o varios clientes, cada uno en su propia terminal:

```bash
# Terminal 1: servidor
mvn exec:java -Dexec.mainClass="servidor.Servidor"

# Terminal 2: cliente
mvn exec:java -Dexec.mainClass="cliente.Cliente"
```

## Demo

El repositorio incluye un diagrama del flujo (`diagrama.png`) y un vídeo de demostración de la aplicación en funcionamiento.
