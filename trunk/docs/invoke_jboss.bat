@echo off
timeout 20
start  /d "D:\jboss\bin" standalone.bat
timeout 60
start firefox.exe "http://localhost:18080/need/"
exit