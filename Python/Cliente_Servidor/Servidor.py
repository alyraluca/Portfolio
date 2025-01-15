#Autor: Alexandra Savu 

import socket
import sys
import threading

lock = threading.Lock() #Para sincronizar la salida de mensajes en consola

#Clase para manejar cada cliente en un hilo separado
class hilo_Servidor(threading.Thread ):
    def __init__(self, socket_atiende, addr_cliente, id_cliente):
        super().__init__()
        self.socket_atiende = socket_atiende
        self.addr_cliente = addr_cliente
        self.id_cliente = id_cliente
    
    #Metodo para ejecutar el hilo
    def run(self):
        #Nos aseguramos la impresión sea sincronizada entre hilos
        with lock:
            print(f"[Servidor] Conexión exitosa con el cliente {self.id_cliente}")
        try:
            #Mantener la conexión activa hasta que el cliente cierre la conexión
            with self.socket_atiende:
                while True:
                    mensaje = self.socket_atiende.recv(1024).decode() #El servidor queda bloqueado esperando el mensaje que le va a enviar el cliente
                    if not mensaje:
                        break
                    else:
                        with lock:
                            print(f"[Servidor] Mensaje Cliente {self.id_cliente}: {mensaje}")
                        #El servidor envia un mensaje al cliente
                        respuesta = f"Comunicación correcta con el cliente {self.id_cliente}"
                        self.socket_atiende.sendall(respuesta.encode())
        except Exception as e:
            print(f"[Servidor] Error con el cliente {self.id_cliente}: {e}")
        finally:
            #Mensaje de finalización con el cliente
            with lock:
                print(f"[Servidor] Fin de conversación con el cliente {self.id_cliente}")
                self.socket_atiende.close()

if __name__=="__main__":
    #Decidimos la IP y el puerto del servidor
    HOST = '127.0.0.1'  
    PORT = 4500
    
    #Intentar crear el socket del servidor
    try:
        socket_escucha = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error:
        print('Fallo en la creación del socket servidor')
        sys.exit()
    
    # Vincular el socket al host y puerto definidos
    try:
        socket_escucha.bind((HOST, PORT)) # Definimosel punto de enlace del ervidor.
    except socket.error as e:
        print(f"Error socket: {e}")
        sys.exit()
    
    socket_escucha.listen(5) # El servidor puede escuchar hasta 5 clientes.
    cliente_id = 1 # El ID inicial del primer cliente
    try:
        while True:
            # Aceptar una nueva conexión de cliente
            socket_atiende, addr_cliente = socket_escucha.accept()
            hilo = hilo_Servidor(socket_atiende, addr_cliente,cliente_id)
            hilo.start()
            cliente_id += 1
    except KeyboardInterrupt:
        print ("Conexion con el servidor finalizada")
    