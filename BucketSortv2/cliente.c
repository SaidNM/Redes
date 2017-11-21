#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>
#define pto "5000"
#define tam 3000


void error(char *msj){
    perror(msj);
    exit(1);
}

typedef struct Cubeta {
  int datos[tam];
  int min, max, count , id;
  const char * port;
} Cubeta;

void llenarArreglo(int arreglo[tam]) {
  srand(time(NULL));
  int i;
  for (i = 0; i < tam; i++)
    arreglo[i] = (rand() % 999) + 1;
}

int enviarNumero(int n) {
  struct addrinfo hints, *servinfo, *p;
  int cd,v,n1,rv,op=0;
  char *srv="127.0.0.1";
  memset(&hints, 0, sizeof hints);
  hints.ai_family = AF_UNSPEC;    /* Allow IPv4 or IPv6  familia de dir*/
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_protocol = 0;
  if ((rv = getaddrinfo(srv, pto, &hints, &servinfo)) != 0) {
    fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
    return 1;
  }
  for(p = servinfo; p != NULL; p = p->ai_next) {
    if ((cd = socket(p->ai_family, p->ai_socktype,p->ai_protocol)) == -1) {
      perror("client: socket");
      continue;
    }
    if (connect(cd, p->ai_addr, p->ai_addrlen) == -1) {
      close(cd);
      perror("client: connect");
      continue;
    }
    break;
  }
  if (p == NULL) {
    fprintf(stderr, "client: error al conectar con el servidor\n");
    return 2;
  }
  freeaddrinfo(servinfo); // all done with this structure
  FILE *f = fdopen(cd,"w+");
  printf("Enviando numero de cubetas\n");
  int toSend = htonl(n);
  v = write(cd, &toSend, sizeof(toSend));
  fflush(f);
}

int connectToServer(const char * puerto) {
  struct addrinfo hints, *servinfo, *p;
  int cd, n1, rv, op = 0;
  char *srv = "127.0.0.1";
  memset(&hints, 0, sizeof hints);
  hints.ai_family = AF_UNSPEC;    /* Allow IPv4 or IPv6  familia de dir*/
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_protocol = 0;
  if (!strcmp("8000", puerto))
    sleep(1);
  if ((rv = getaddrinfo(srv, puerto, &hints, &servinfo)) != 0) {
    fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
    return 1;
  }
  for(p = servinfo; p != NULL; p = p->ai_next) {
    if ((cd = socket(p->ai_family, p->ai_socktype,p->ai_protocol)) == -1) {
      perror("client: socket");
      continue;
    }
    if (connect(cd, p->ai_addr, p->ai_addrlen) == -1) {
      close(cd);
      perror("client: connect");
      continue;
    }
    break;
  }
  if (p == NULL) {
    fprintf(stderr, "client: error al conectar con el servidor\n");
    return 2;
  }
  freeaddrinfo(servinfo); // all done with this structure
  return cd;
}


int sendInt(int cd, int n) {
  int v;
  FILE *f = fdopen(cd, "w+");
  int toSend = htonl(n);
  v = write(cd, &toSend, sizeof(toSend));
  fflush(f);
}

int readInt(int cd) {
  int buffer = 0, readBytes = 0, readInteger = 0;
  while (readBytes < 4) {
    int n = read(cd, &buffer, (sizeof buffer) - readBytes);
    readInteger += (buffer << (8 * readBytes));
    readBytes += n;
  }
  return ntohl(readInteger);
}

void *enviarCubeta(void *arg) {
  Cubeta* bucket = (Cubeta*)arg;
  int cd = connectToServer(bucket -> port); 
  sendInt(cd, bucket -> count);
  if (bucket -> count != 0) {
    for (int i = 0; i < bucket -> count; i++) {
      sendInt(cd, bucket -> datos[i]);
    }  
    printf("\nNumeros ordenados recibidos en la cubeta %d:\n", bucket -> id);
    for (int i = 0; i < bucket -> count; i++) {
      bucket -> datos[i]= readInt(cd); 
    //  printf(" %d ", bucket->datos[i]);
    }
    //printf("\nNumeros ordenados recibidos\n");
  } else {
    printf("No hay numeros...\n");
  }
  pthread_exit(bucket);
}

int main(int argc, char const *d[]) {
  pthread_t * id_hilo;
  int cubetas, arreglo[tam];
  printf("Numero de cubetas: ");
  scanf("%d", &cubetas);
  id_hilo = (pthread_t*)malloc(sizeof(pthread_t) * cubetas);
  Cubeta** cb = (Cubeta**)malloc(sizeof(Cubeta*) * cubetas);
  for (int i = 0; i < cubetas; i++) {
    cb[i] = (Cubeta*)malloc(sizeof(Cubeta));
    cb[i] -> min = 1 + ((1000 / cubetas) * i);
    cb[i] -> max = (1000 / cubetas) * (i + 1);
    cb[i] -> count = 0;
    cb[i] -> id = i + 1;
    cb[i] -> port = (const char *)malloc(sizeof(const char) * 4);
    int tempPort = atoi("7999");
        tempPort += i + 1;
    sprintf((char * restrict)cb[i] -> port, "%d", tempPort);
  }

  printf("\nGenerando cubetas\n");
  if ((cb[cubetas - 1] -> max) != 1000) {
    cb[cubetas - 1] -> max = 1000;
  }
  llenarArreglo(arreglo);
    printf("\n");
  for(int i=0; i<tam; i++){
    printf(" %d ", arreglo[i]);

  }
  for (int i = 0; i < tam; i++) {
    for (int k = 0; k < cubetas; k++) {
      if (arreglo[i] >= cb[k] -> min && arreglo[i] <= cb[k] -> max) {
        cb[k] -> datos[cb[k] -> count] = arreglo[i];
        (cb[k] -> count)++;
        k = cubetas + 1;
      }
    }
  }
  enviarNumero(cubetas);
  //creamos cada uno de los clientes
  for (int i = 0; i < cubetas; i++) {
    //printf("Cubeta %d tiene: %d numeros guardados\n", i + 1, datosCubeta[i] -> saved);
    pthread_create(&id_hilo[i], NULL, enviarCubeta, (void*) cb[i]);
    pthread_join(id_hilo[i], (void *)&cb[i]);
  }

  int min=0;
  for(int i=0 ; i< cubetas; i++){
    for(int j=0; j< cb[i]->count; j++,min++){
        arreglo[min]=cb[i]->datos[j];
    }
  }
  printf("\n");
  for(int i=0; i<tam; i++){
    printf(" %d ", arreglo[i]);
  }
  printf("\n");
  return 0;
}



