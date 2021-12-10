SM2: SM2.o
	gcc -Wall -g SM2.o -o SM2 -lpthread -lrt -lssl -lcrypto

SM2.o: SM2.c
	gcc -Wall -g -c SM2.c

run: SM2
	./SM2

certs:
	 ./make_certs