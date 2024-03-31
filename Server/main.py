import logging
import socket
import threading
import sys
from time import ctime


# Créer un socket
# Définir le port lié et l'adresse IP
# Définir le nombre maximum de connexions
# Recevoir des données ou envoyer et des données
# Fermer la connexion

def socket_service():
    try:
        logging.basicConfig(level=logging.DEBUG,
                            format='%(levelname)s [%(asctime)s] : %(message)s',
                            datefmt='%Y-%M-%D %H:%M')

        bind_ip = "127.0.0.1"
        bind_port = 60000

        # Créer un socket
        server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server.bind((bind_ip, bind_port))
        server.listen(5)
        logging.info("[*] Listening on %s:%d" % (bind_ip, bind_port))
    except socket.error as msg:
        print(msg)
        sys.exit(1)
    logging.info("[*] Waiting connection...")

    while True:
        # Mettre le serveur en écouter
        logging.info("[*] En écoute...")
        server.listen()

        # Accepter une connexion
        client, addr = server.accept()
        logging.info("[*] Accept connection from: %s:%d" % (addr[0], addr[1]))

        # Suspend client thread processing incoming data
        client_handler = threading.Thread(target=handle_client, args=(client, addr))
        client_handler.start()


# Client Processing Thread
def handle_client(client_socket, addr_socket):
    request = client_socket.recv(1024).decode("utf-8")
    if not request:
        client_socket.send("[*] Error data".encode("utf-8"))
        sys.exit(1)

    logging.info("[*] Received: %s" % request)

    # Return a Package
    client_socket.send("[*] Get your data:%s\n[%s]" % (request, ctime()))
    client_socket.close()


if __name__ == '__main__':
    socket_service()
