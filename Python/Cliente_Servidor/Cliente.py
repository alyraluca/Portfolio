#Autor: Alexandra Savu 

import socket
import sys
import threading
import random
import time

HOST = '127.0.0.1'  
PORT = 4500
lock = threading.Lock() #Para sincronizar la salida en consola

class hilo_Cliente(threading.Thread):
    def __init__(self, id_cliente):
        super().__init__()
        self.id_cliente = id_cliente
    
    def run (self):
        try:
            socket_cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)    
        except socket.error:
            print('Fallo en la creación del socket ciente')
            sys.exit()
        
        socket_cliente.connect((HOST, PORT))
        
        with socket_cliente:
            try:
                for i in range (1,4): # 3 mensajes (el ultimo numero del rango no lo incluye)
                    mensaje = f"Mensaje {i} del cliente {self.id_cliente}"
                    socket_cliente.sendall(mensaje.encode())
                    respuesta = socket_cliente.recv(1024).decode()
                    with lock:
                        print(f"[Cliente {self.id_cliente}] Mensaje Servidor: {respuesta}")
                    time.sleep(random.uniform(1,3))
            except Exception as e:
                with lock:
                    print(f"Cliente {self.id_cliente}: Error: {e}")
        
if __name__== "__main__":
    clientes = []
    for i in range (1,6): #Simulamos 5 clientes (el último numero no se incluye)
        hilo = hilo_Cliente(i)
        clientes.append(hilo)
        hilo.start()
              
    for cliente in clientes:
        cliente.join()
    
    print("Conexiones finalizadas")
    