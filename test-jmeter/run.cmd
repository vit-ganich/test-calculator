@echo off
:: Set datetime format for timestamp
set mydate=%date:/=_%
set mytime=%time::=_%
set mytime=%mytime:,=%
set mytime=%mytime:.=%
set _timestamp=%mydate: =_%_%mytime%

:: Create folder with timestamp
mkdir reports\%_timestamp%\dashboard

:: Run jmeter tests
jmeter -n --testfile testCalc-rest-api.jmx -Jthreads=100 -l reports/%_timestamp%/output.csv -e -o reports/%_timestamp%/dashboard