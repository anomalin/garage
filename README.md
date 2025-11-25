# GARAGE API

Ladda ner eller klona projektet till din dator.<br> 
Packa upp och navigera till projektets rotkatalog. <br>
Kör scriptet garage_script.sh. 

Servern öppnar på http://localhost:8080

## ENDPOINTS
Testa API:ets endpoints med valfri API-klient. 

POST /customers?name=Anna&phoneNumber=123456789

POST /vehicles?registrationNumber=CFM538&brand=Ford&model=Focus&productionYear=2015

GET /get-customer-id?name=Anna

GET /vehicles-by-brand?brand=Ford

GET /vehicles

GET /customers

POST /connect?customerId=1&vehicleId=1

