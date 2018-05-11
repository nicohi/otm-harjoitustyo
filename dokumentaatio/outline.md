# Planeettasimulaatio
Ohjelma näyttää planeettoja ja niiden liikkeitä graafisesti.

## Toiminallisuudet
### Fysiikka
- planeetat liikkuvat [Newtonin painovoimalain](https://fi.wikipedia.org/wiki/Painovoima#Newtonin_laki_vetovoimasta) mukaan.
- törmäykset mallinnetaan ja noudattavat liikemäärän säilymislakia
- planeetat simuloidaan kolmiulotteisilla vektoreilla
- liian suurella aikavälillä simulaatio on epätarkka (planeetat liikkuvat oudosti tai toistensa läpi)
### Käyttöliittymä 
- ohjelmaan voi lisätä planeettoja eri parametreillä (esim. massa, nopeus, sijainti)
- simulaation aikaväliä voi muuttaa
- planeetat sekä niiden nopeus ja kiihtyvyys vektorit näkyvät graafisessa käyttöliittymässä 
- kolmas ulottuvuus näytetään käyttäjälle muuttoksina planeettojen kokoon ja väriin
- kaikki planeetat voidaan poistaa
- planeetat voidaan tallentaa ja ladata tietokannasta
- painavin planeetta on keskimmäinen
### Tiedon tallennus
- planeettojen tila voidaan tallentaa HSQLDB tietokantaan
