# Bysykkeloversikt

## Oppstart
### Krav
Java JDK installert (testet med Java 16 og 17)
npm installert (testet med v 7.20.5)

### Backend: 
- `cd bysykkeloversikt-be`
- På Linux/Mac: `chmod 755 mvnw`
- Bygg: `./mvnw clean install`
- Kjør: `./mvnw spring-boot:run`

### Frontend:
- `cd bysykkeloversikt-web`
- Bygg: `npm install vite`
- Kjør: `npm run dev`

Applikasjonen nås ved å åpne http://localhost:3000 i en browser 

## Funksjonalitet
Viser i sanntid (hvert 10 sekund) oversikt over ledige bysykler og ledige låser for bysykler i Oslo. 
Appen indikerer med farger dersom det er få eller ingen ledige låser eller sykler. 

## Internt API er dokumentert med swagger
Kan aksesseres på http://localhost:8080/swagger-ui.html