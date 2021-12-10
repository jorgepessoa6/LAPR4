#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h> 
#include <fcntl.h> 
#include <pthread.h>
#include <sys/socket.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

// for SSL/TSL
#include <openssl/crypto.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define SERVER_SSL_CERT_FILE "serverC.pem"
#define SERVER_SSL_KEY_FILE "serverC.key"
#define AUTH_CLIENTS_SSL_CERTS_FILE "authentic-clients.pem"

#define SERVER_J_SSL_CERT_FILE "serverJ.pem"

#define HELLO 0
#define ACK 150
#define MSG 1
#define CONFIG 2
#define RESET 3
#define VERSION_DEFAULT 1

#define SIZE_DEFAULT 1

#define BUF_SIZE 300
#define SERVER_PORT "30604"
// read a string from stdin protecting buffer overflow
#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

typedef struct{ 
	char * ipAddressName;
	unsigned int machineID;
	int cadence;
} Machine;

typedef struct{
	unsigned char version;
	unsigned char code;
	unsigned short id;
	unsigned short dataLength;
	char* rawData;
} Message;

void *thread_server_CONFIG(void *arg){
	
	Machine *m = (Machine*) arg;

	struct sockaddr_storage from;
	int err, newSock, sock;
	unsigned int adl;
	unsigned long i, f, n, num, sum;
	unsigned char bt;
	char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
	struct addrinfo req, *list;
	
	bzero((char *)&req,sizeof(req));
	// requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
	req.ai_family = AF_INET6;
	req.ai_socktype = SOCK_STREAM;
	req.ai_flags = AI_PASSIVE; // local address
	err=getaddrinfo(NULL, SERVER_PORT , &req, &list);
	if(err) { 
		printf("Failed to get local address, error: %s\n",gai_strerror(err));
		exit(1);
	
	}
	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
	
	if(sock==-1) { 
		perror("Failed to open socket"); 
		freeaddrinfo(list); exit(1);
	}
	
	if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
		perror("Bind failed");
		close(sock);
		freeaddrinfo(list);
		pthread_exit((void*)NULL);
	}
		
	freeaddrinfo(list);
	listen(sock,SOMAXCONN);

	const SSL_METHOD *method;

	SSL_CTX *ctx;
	method = SSLv23_server_method();
	ctx = SSL_CTX_new(method);
	// Load the server's certificate and key
	SSL_CTX_use_certificate_file(ctx, SERVER_SSL_CERT_FILE, SSL_FILETYPE_PEM);
	SSL_CTX_use_PrivateKey_file(ctx, SERVER_SSL_KEY_FILE, SSL_FILETYPE_PEM);
	if (!SSL_CTX_check_private_key(ctx)) {
		puts("Error loading server's certificate/key");
		close(sock);
		pthread_exit((void*)NULL);
	}
	// THE CLIENTS' CERTIFICATES ARE TRUSTED
	SSL_CTX_load_verify_locations(ctx, AUTH_CLIENTS_SSL_CERTS_FILE, NULL);
	// Restrict TLS version and cypher suite
	SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
	SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");
	// Clients must provide a trusted certificate, the handshake will fail otherwise
	SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);
	
	puts("Accepting TCP connections (IPv6/IPv4). Use CTRL+C to terminate the server");
	adl=sizeof(from);
	newSock=accept(sock,(struct sockaddr *)&from,&adl);
	
	if(!fork()) {
		close(sock);
		getnameinfo((struct sockaddr *)&from,adl,cliIPtext,BUF_SIZE,
		cliPortText,BUF_SIZE, NI_NUMERICHOST|NI_NUMERICSERV);
		printf("\nNew connection from %s, port number %s\n", cliIPtext, cliPortText);

		SSL *sslConn = SSL_new(ctx);
		SSL_set_fd(sslConn, newSock);
		if(SSL_accept(sslConn)!=1) {
			puts("TLS handshake error: client not authorized");
			SSL_free(sslConn);
			close(newSock);
			pthread_exit((void*)NULL);
		}
		printf("\nTLS version: %s\nCypher suite: %s\n", SSL_get_cipher_version(sslConn),SSL_get_cipher(sslConn));
		X509* cert=SSL_get_peer_certificate(sslConn);
		X509_free(cert);
		X509_NAME_oneline(X509_get_subject_name(cert),cliIPtext,BUF_SIZE);
		printf("\nClient's certificate subject: %s\n",cliIPtext);

		Message msg;
		msg.rawData=0;
		msg.dataLength=0;
		msg.id=0;
		msg.version=0;

		printf("\nWaiting for message\n");

		Message msgBack;
		//msgBack.rawData=0;
		msgBack.dataLength=0;
		msgBack.id=0;
		msgBack.version=0;
		msgBack.code=0;

		printf("\nMessage Received!");

		SSL_read(sslConn, &msgBack.code,1);
		printf("MESSAGE CODE : %d\n", msgBack.code);
		SSL_read(sslConn, &msgBack.id,2);
		printf("MESSAGE ID : %d\n", msgBack.id);
		SSL_read(sslConn, &msgBack.dataLength,2);
		printf("MESSAGE DATA LENGTH : %hi\n", msgBack.dataLength);
		msgBack.rawData = (char*) malloc(42);
		printf("MESSAGE RAW DATA 1 : %u\n", msgBack.rawData);
		SSL_read(sslConn, &msgBack.rawData,42);

		//printf("%d", c);
		//printf("MESSAGE RAW DATA 2 : %s\n", msgBack.rawData);
		
		unsigned char n1 = (unsigned char) 2;
		
		if(msgBack.code == n1 && msgBack.id == m->machineID){ //CONFIG code
			
			//Write raw data in a file

			FILE *fptr;
		    fptr = fopen("MachineConfiguration.txt","w");


		    if(fptr == NULL)
		    {
			   printf("Error!");   
			   exit(1);             
		    }

		    fprintf(fptr,"%s",&msgBack.rawData);
		    fclose(fptr);			
				
			unsigned char ack = (unsigned char) 150;
			msg.code = ack;
			printf("\nSending ACK message....");			
			SSL_write(sslConn, &msg, sizeof(msg));
		}else {  
			unsigned char nack = (unsigned char) 151;
			msg.code = nack;
			printf("\nSending NACK message....");
			SSL_write(sslConn, &msg, sizeof(msg));				  
		}
		
		SSL_free(sslConn);
		close(newSock);
		printf("\nConnection %s, port number %s closed\n", cliIPtext, cliPortText);
		pthread_exit((void*)NULL);
	}
	close(newSock);
	close(sock);
	pthread_exit((void*)NULL);

}

void* thread_cli_MSG(void *arg){

    Machine *m = (Machine*) arg;

    int err, sock;
    unsigned char bt;
    int i;
    char linha[BUF_SIZE];
    struct addrinfo req, *list;

    bzero((char *)&req,sizeof(req));// let getaddrinfo set the family depending on the supplied server address

    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;

    err=getaddrinfo(m->ipAddressName, SERVER_PORT , &req, &list);
    if(err) {  //COLOCAR AQUI IP DO SERVIDOR
        printf("Failed to get server address, error: %s\n",gai_strerror(err));
        pthread_exit((void*)NULL);
    }

    sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

    if(sock==-1){
        perror("Failed to open socket");
        freeaddrinfo(list);
        pthread_exit((void*)NULL);
    }

    if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1){
        perror("Failed connect");
        freeaddrinfo(list);
        close(sock);
        pthread_exit((void*)NULL);
    }
    	
	const SSL_METHOD *method=SSLv23_client_method();
	SSL_CTX *ctx = SSL_CTX_new(method);
	// Load client's certificate and key
	strcpy(linha,(int)m->machineID);strcat(linha,".pem");
	SSL_CTX_use_certificate_file(ctx, linha, SSL_FILETYPE_PEM);
	strcpy(linha,(int)m->machineID);strcat(linha,".key");
	SSL_CTX_use_PrivateKey_file(ctx, linha, SSL_FILETYPE_PEM);
	if (!SSL_CTX_check_private_key(ctx)) {
		puts("Error loading client's certificate/key");
		close(sock);
		pthread_exit((void*)NULL);
	}

	SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT,NULL);
	// THE SERVER'S CERTIFICATE IS TRUSTED
	SSL_CTX_load_verify_locations(ctx,SERVER_J_SSL_CERT_FILE,NULL);
	// Restrict TLS version and cypher suites
	//SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
	SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");
	SSL *sslConn = SSL_new(ctx);
	SSL_set_fd(sslConn, sock);
	if(SSL_connect(sslConn)!=1) {
		puts("TLS handshake error");
		SSL_free(sslConn);
		close(sock);
		pthread_exit((void*)NULL);
	}
	printf("TLS version: %s\nCypher suite: %s\n",
	SSL_get_cipher_version(sslConn),SSL_get_cipher(sslConn));
	if(SSL_get_verify_result(sslConn)!=X509_V_OK) {
		puts("Sorry: invalid server certificate");
		SSL_free(sslConn);
		close(sock);
		pthread_exit((void*)NULL);
	}
	X509* cert=SSL_get_peer_certificate(sslConn);
	X509_free(cert);
	if(cert==NULL) {
		puts("Sorry: no certificate provided by the server");
		SSL_free(sslConn);
		close(sock);
		pthread_exit((void*)NULL);
	}

		int nBytes;
        Message msge;

		//Version
		char version = 1;
		msge.version= version;

		//Code
		char cod = 0;
		msge.code = cod;

        short a = (short) m->machineID;
        msge.id = a;
        printf("ID: %hu\n",msge.id);
        msge.dataLength = 0;
        msge.rawData = 0;

		SSL_read(sslConn, &msge, sizeof(msge));
	    printf("HELLO MESSAGE SENT\n");

	    FILE * fp;
        char * line = (char*)malloc(60 * sizeof(char));
        size_t len = 60;
        ssize_t readu;
        char *file = (char*)malloc(13 * sizeof(char));

        sprintf(file, "Machine%d.txt",m->machineID);
        fp = fopen(file, "r");
        if (fp == NULL){
            printf("FICHEIRO NAO ENCONTRADO \n");
            exit(EXIT_FAILURE);
    	}


	printf("WAITING FOR MESSAGE BACK\n");
	Message msgBack;
	msgBack.rawData=0;
	msgBack.dataLength=0;
	msgBack.id=0;
    msgBack.version=0;
	SSL_read(sslConn,&msgBack.code,1);


	//read(sock, &msge, sizeof(msge));
	printf("MSGBACK %d\n",msgBack.code);
    unsigned char n = (char) 150;
	if(msgBack.code==n){
		while ((readu = getline(&line, &len, fp)) != -1) {
		    Message msgMSG;
		    nBytes =  sizeof(line);
		    printf("\nMessage: \n");
		    printf("%s", line);
		    uint32_t un = htonl(nBytes);
            line[nBytes] = 0;
               //Version
	      	msgMSG.version= VERSION_DEFAULT;
		        //Code
		   	unsigned char codMSG = 1;
          	msgMSG.code = codMSG;
            msgMSG.id = a;
	        	//DataLength
            msgMSG.dataLength = nBytes;
            int p;
            char* temp;
            msgMSG.rawData = line;
	        	//RawData
		    SSL_write(sslConn, &msgMSG, sizeof(msgMSG));
		    printf("\nMessage Sent");
		    sleep(m->cadence);
		    printf("\n---\n");
		}
    }else {
        printf("NACK code");
    }
    SSL_free(sslConn);
    printf("Exit");
    fclose(fp);
    close(sock);
    pthread_exit((void*)NULL);
}

void* thread_reset_no_prot(void *arg){

    Machine *m = (Machine*) arg;

    struct sockaddr_storage client;

    int err1, sock1, res, i;

    unsigned int adl;

    char linha[BUF_SIZE];

    char* linha1;

    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];

    struct addrinfo  req, *list;

    bzero((char *)&req,sizeof(req));// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it

    req.ai_family = AF_INET6;req.ai_socktype = SOCK_DGRAM;

    req.ai_flags = AI_PASSIVE;// local address

    err1=getaddrinfo(NULL, SERVER_PORT , &req, &list);

    if(err1) {
        printf("Failed to get local address, error: %s\n",gai_strerror(err1));
         pthread_exit((void*)NULL);
    }

    sock1=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

    if(sock1==-1) {
		perror("Failed to open socket");
		freeaddrinfo(list);
		pthread_exit((void*)NULL);
	}

    if(bind(sock1,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("Bind failed");
        close(sock1);
        freeaddrinfo(list);
        pthread_exit((void*)NULL);
    }

    freeaddrinfo(list);

    puts("Listening for UDP reset requests (IPv6/IPv4). Use CTRL+C to terminate the server");

    adl=sizeof(client);

    while(1){
		printf("\nWaiting for message\n");
		res=recvfrom(sock1,linha,BUF_SIZE,0,(struct sockaddr *)&client,&adl);
		printf("\nMessage Recieved!");
        if(!getnameinfo((struct sockaddr *)&client,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV))
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
        else puts("Got request, but failed to get client address");
		for(i=0;i<res;i++) {
			unsigned char n1 = (char) 3;
			int k;
			if(linha[1] == n1){ //Reset code
					Machine *m = (Machine*) arg;
					int err, sock;
					unsigned char bt;
					int i;
					char linha[BUF_SIZE];
					struct addrinfo req, *list;

					bzero((char *)&req,sizeof(req));// let getaddrinfo set the family depending on the supplied server address

					req.ai_family = AF_UNSPEC;
					req.ai_socktype = SOCK_STREAM;

					err=getaddrinfo(m->ipAddressName, SERVER_PORT , &req, &list);
					if(err) {  //COLOCAR AQUI IP DO SERVIDOR
						printf("Failed to get server address, error: %s\n",gai_strerror(err));
						pthread_exit((void*)NULL);
					}

					sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

					if(sock==-1){
						perror("Failed to open socket");
						freeaddrinfo(list);
						pthread_exit((void*)NULL);
					}

					if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1){
						perror("Failed connect");
						freeaddrinfo(list);
						close(sock);
						pthread_exit((void*)NULL);
					}


					int nBytes;
					Message msge;

					//Version
					char version = 1;
					msge.version= version;

					//Code
					char cod = 0;
					msge.code = cod;

					short a = (short) m->machineID;
					msge.id = a;
					printf("ID: %hu\n",msge.id);
					msge.dataLength = 0;
					msge.rawData = 0;

					write(sock, &msge, sizeof(msge));
					printf("HELLO MESSAGE SENT TO SCM\n");

					printf("WAITING FOR MESSAGE BACK\n");
					Message msgBack;
					msgBack.rawData=0;
					msgBack.dataLength=0;
					msgBack.id=0;
					msgBack.version=0;
					read(sock,&msgBack.code,1);


					//read(sock, &msge, sizeof(msge));
					printf("MSGBACK %d\n",msgBack.code);
					unsigned char n = (char) 150;
					unsigned char r = (char) 151;
					if(msgBack.code==n){
						r = (char) 150;
					}
					close(sock);
					printf("\nSending success status message....");
					linha[1] = r;
					sendto(sock1,linha,res,0,(struct sockaddr *)&client,adl);
			}


	    }
	}
    printf("\nExiting");
    pthread_exit((void*)NULL);

    close(sock1);
    pthread_exit((void*)NULL);
}

void* thread_reset(void *arg){
		
    Machine *m = (Machine*) arg;
    
    struct sockaddr_storage client;
    
    int err1, sock1, res, i;
    
    unsigned int adl;
    
    char linha[BUF_SIZE];
    
    char* linha1;
    
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    
    struct addrinfo  req, *list;
    
    bzero((char *)&req,sizeof(req));// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    
    req.ai_family = AF_INET6;req.ai_socktype = SOCK_DGRAM;
    
    req.ai_flags = AI_PASSIVE;// local address
    
    err1=getaddrinfo(NULL, SERVER_PORT , &req, &list);
    
    if(err1) {
        printf("Failed to get local address, error: %s\n",gai_strerror(err1));
         pthread_exit((void*)NULL);
    }
    
    sock1=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
    
    if(sock1==-1) {
		perror("Failed to open socket"); 
		freeaddrinfo(list); 
		pthread_exit((void*)NULL);
	}
		
    if(bind(sock1,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("Bind failed");
        close(sock1);
        freeaddrinfo(list);
        pthread_exit((void*)NULL);
    }
    
    freeaddrinfo(list);
    
    puts("Listening for UDP reset requests (IPv6/IPv4). Use CTRL+C to terminate the server");
    
    adl=sizeof(client);
    
    while(1){
		printf("\nWaiting for message\n");
		res=recvfrom(sock1,linha,BUF_SIZE,0,(struct sockaddr *)&client,&adl);
		printf("\nMessage Recieved!");
        if(!getnameinfo((struct sockaddr *)&client,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV))
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
        else puts("Got request, but failed to get client address");
		for(i=0;i<res;i++) {
			unsigned char n1 = (char) 3;
			int k;
			if(linha[1] == n1){ //Reset code
					Machine *m = (Machine*) arg;
					int err, sock;
					unsigned char bt;
					int i;
					char linha[BUF_SIZE];
					struct addrinfo req, *list;

					bzero((char *)&req,sizeof(req));// let getaddrinfo set the family depending on the supplied server address

					req.ai_family = AF_UNSPEC;
					req.ai_socktype = SOCK_STREAM;

					err=getaddrinfo(m->ipAddressName, SERVER_PORT , &req, &list);
					if(err) {  //COLOCAR AQUI IP DO SERVIDOR
						printf("Failed to get server address, error: %s\n",gai_strerror(err));
						pthread_exit((void*)NULL);
					}

					sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

					if(sock==-1){
						perror("Failed to open socket");
						freeaddrinfo(list);
						pthread_exit((void*)NULL);
					}

					if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1){
						perror("Failed connect");
						freeaddrinfo(list);
						close(sock);
						pthread_exit((void*)NULL);
					}


					int nBytes;
					Message msge;

					//Version
					char version = 1;
					msge.version= version;

					//Code
					char cod = 0;
					msge.code = cod;

					short a = (short) m->machineID;
					msge.id = a;
					printf("ID: %hu\n",msge.id);
					msge.dataLength = 0;
					msge.rawData = 0;

					write(sock, &msge, sizeof(msge));
					printf("HELLO MESSAGE SENT TO SCM\n");
					
					printf("WAITING FOR MESSAGE BACK\n");
					Message msgBack;
					msgBack.rawData=0;
					msgBack.dataLength=0;
					msgBack.id=0;
					msgBack.version=0;
					read(sock,&msgBack.code,1);


					//read(sock, &msge, sizeof(msge));
					printf("MSGBACK %d\n",msgBack.code);
					unsigned char n = (char) 150;
					unsigned char r = (char) 151;
					if(msgBack.code==n){
						r = (char) 150;
					}
					close(sock);
					printf("\nSending success status message....");
					linha[1] = r;
					sendto(sock1,linha,res,0,(struct sockaddr *)&client,adl);	
			}
					
			    
	    }
	}
    printf("\nExiting");
    pthread_exit((void*)NULL);
    
    close(sock1);
    pthread_exit((void*)NULL);
}

void* thread_server(void *arg){
		
    Machine *m = (Machine*) arg;
    
    struct sockaddr_storage client;
    
    int err, sock, res, i;
    
    unsigned int adl;
    
    char linha[BUF_SIZE];
    
    char* linha1;
    
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    
    struct addrinfo  req, *list;
    
    bzero((char *)&req,sizeof(req));// request a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    
    req.ai_family = AF_INET6;req.ai_socktype = SOCK_DGRAM;
    
    req.ai_flags = AI_PASSIVE;// local address
    
    err=getaddrinfo(NULL, SERVER_PORT , &req, &list);
    
    if(err) {
        printf("Failed to get local address, error: %s\n",gai_strerror(err));
         pthread_exit((void*)NULL);
    }
    
    sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
    
    if(sock==-1) {
		perror("Failed to open socket"); 
		freeaddrinfo(list); 
		pthread_exit((void*)NULL);
	}
		
    if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        pthread_exit((void*)NULL);
    }
    
    freeaddrinfo(list);
    
    puts("Listening for UDP requests (IPv6/IPv4). Use CTRL+C to terminate the server");
    
    adl=sizeof(client);
    
    while(1){
    printf("\nWaiting for message");
		res=recvfrom(sock,linha,BUF_SIZE,0,(struct sockaddr *)&client,&adl);
		printf("\nMessage Recieved!");
        if(!getnameinfo((struct sockaddr *)&client,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV))
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
        else puts("Got request, but failed to get client address");
		for(i=0;i<res;i++) {
			unsigned char n1 = (char) 0;
			int k;
			if(linha[1] == n1){ //HELLO code
				unsigned char ack = (unsigned char) 150;
				linha[1] = ack;
			    printf("\nSending ACK message....");
				sendto(sock,linha,res,0,(struct sockaddr *)&client,adl);
			}		
			    
	    }
	}
    printf("\nExiting");
    pthread_exit((void*)NULL);
    
    close(sock);
    pthread_exit((void*)NULL);
}

int main(int argc, char *argv[]){
	char a[10];
	int d;
	pthread_t threads[5];
    Machine m;
    if(argc!=4){
		printf("\nVolte a introduzir os parametros: \n - IP do Servidor\n - Numero da mÃ¡quina\n - CadÃªncia (segundos))\n");
		return;
	}

    m.ipAddressName = argv[1];
    m.machineID = atoi(argv[2]);
    m.cadence = atoi(argv[3]);


    printf("\nIP do Servidor: %s\nNumero da maquina: %d\nCadencia: %d\n\n", argv[1], atoi(argv[2]), atoi(argv[3]));

    //pthread_create(&threads[1], NULL, thread_server, &m);
	//pthread_create(&threads[0], NULL, thread_cli_MSG, &m);
	//pthread_create(&threads[2], NULL, thread_server_CONFIG, &m);
	//pthread_create(&threads[3], NULL, thread_reset, &m);
	pthread_create(&threads[3], NULL, thread_reset_no_prot, &m);

	//pthread_join(threads[2], NULL);
	//pthread_join(threads[0], NULL);
	//pthread_join(threads[1], NULL);
	//pthread_join(threads[3], NULL);
	pthread_join(threads[4], NULL);

    return 0;
}
