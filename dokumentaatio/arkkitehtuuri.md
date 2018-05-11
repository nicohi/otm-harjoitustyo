![dia1](dia1.png?raw=true)

## Vektorit
Luokka Vector vastaa kolmiulotteisista vektoreista. Sen avulla Physics luokka laskee fysiikkaan liittyviä laskuja (kuten painovoima kahden planeetan välillä). Physics luokka laskee myös perusasioita vektoreihin liittyen (kuten summa).  
## Simulaattori
Simulator luokka säilöö planeettoja ArrayList:issä. Sen tick() metodi suorittaa eri operaatioita näihin hyödyntäen stream() metodia. 
## Käyttöliittymä
Käyttöliittymän esittämät planeetat seuraavat Simulator luokan planeetta listaa. StatusTimer kutsuu Simulator.tick() metodia ja päivittää UserInterface:in piirtämiä planeettoja ja vektoreita. Vektorit piirretään yhteen Pane olioon ja planeetat toiseen. Metodin toggleShowVectors() Button muuttaa vektorien Pane:in näkymättömksi ja takaisin näkyväksi.
## Tietokanta
PlanetDB metodi save() tallentaa ArrayList<Planet> HSQLDB tietokantaan. Tallenettu lista saadan ladattua load() metodilla. Tietokanta käyttää tiedostoa 'planetdb' ja luo sen siihen polkuun mistä ohjelma suoritetaan.
## Ohjelman rakenteen heikkoudet
- liikaa sovelluslogiikkaa UserInterface luokassa
