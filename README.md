##Moonpark
- Parking calculator for the TariffZones M1, M2 and M3.

### Available endpoints:
- GET http://localhost:8080/api/takst
- The endpoint takes 3 query params: `startedParking`, `endedParking`, & `tariffZone` 
- Example: http://localhost:8080/api/takst?startedParking=2024-05-27T10:15:30&endedParking=2024-05-27T11:16:30&tariffZone=M3