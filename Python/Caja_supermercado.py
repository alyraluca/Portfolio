'''
Evaluable 3
Autor: Alexandra Raluca, Savu 2º DAM
03/11/2024
'''

import threading
import time
import random


#Semaforo con un contador inicial para gestionar las 5 cajas disponibles
semaforo_cajas = threading.Semaphore(5)

#Variable para controlar el estado de las promociones
promocion = False
#Lock() para controlar la variable de la promocion, a la que accederan todas las funciones
lock_promocion = threading.Lock()

#Barrera para asegurarnos de que todos los clientes han terminado
barrera_atendida = threading.Barrier(15)



#Función para activar la promoción
def promo_activa():
    global promocion
    print(f"-----PROMOCIÓN ACTIVADA-----")
    #Utilizamos el lock() para asegurar un buen funcionamiento de la variable global 'promocion'
    with lock_promocion:
        promocion = True
    temporizador_cancelar = threading.Timer(5, promo_inactiva)#Utilizamos un timer para desactivar la promociond despues de 5 segundos
    temporizador_cancelar.start()



#Función para desactivar la promoción
def promo_inactiva():
    #Variable global
    global promocion
    
    print(f"-----PROMOCIÓN CANCELADA-----")
    #Utilizamos el lock() para asegurar un correcto uso de la variable 'promocion'
    with lock_promocion:
        promocion = False
    temporizador_activar = threading.Timer(10, promo_activa)#Usamos un timer para activar la promoción despues de 10 segundos de espera
    temporizador_activar.start()



#Función para atender a un cliente
def persona_entra(numero_cliente):
    #Variables globales
    global promocion, barrera_atendida
    print(f"Cliente {numero_cliente} está esperando en la caja")

    #Intentar adquirir una caja para el cliente
    semaforo_cajas.acquire()
    print(f"Cliente {numero_cliente} está siendo atendido")
    
    #Gestionar lo que pasa si la promoción esta activada
    with lock_promocion:
        if promocion == True:
            print(f"Cliente {numero_cliente} ha aprovechado la promoción")
        
    #Simular tiempo de atención al cliente
    tiempo_caja = random.randint(1,3)
    time.sleep(tiempo_caja)#Simular tiempo en la caja

    print(f"Cliente {numero_cliente} ha dejado la caja despues de {tiempo_caja} segundos")

    #Liberar la caja utilizada
    semaforo_cajas.release()

    #Utilizamos Barrier para asegurarnos de que todos los clientes sean atendidos antes de cerrar el supermercado
    barrera_atendida.wait()



#Programa principal
if __name__ == "__main__":
    print("-----------SUPERMERCADO ABIERTO-----------")
    #Lista para gestionar los threads de los clientes
    hilos_personas = []
    
    #Hilos para gestionar la funcion de la promocion 
    hilo_promocion = threading.Thread(target=promo_activa, daemon=True)#Utilizamos el daemon = True para que se termine el bucle al terminar el hilo principal
    hilo_promocion.start()#Iniciamos la promoción

    #Creamos hilos que representan personas que quieran ser atendidas en las cajas
    for i in range (15):
        hilo = threading.Thread(target=persona_entra, args=(i+1,))
        hilos_personas.append(hilo)#Añadimos los hilos a la lista
        hilo.start()#Iniciamos el hilo

    #Nos aseguramos que todos los hilos hayan acabado antes de avanzar con el programa
    for hilo in hilos_personas:
        hilo.join()

    print("-----------SUPERMERCADO CERRADO-----------")