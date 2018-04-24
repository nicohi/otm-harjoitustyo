# planetsim
Javalla ohjelmoitu [n-kappale](https://en.wikipedia.org/wiki/N-body_simulation) simulaattori. 
Painovoima on mallinnettu mahdollisimman realistisesti. 
Planeettojen kiihtyvyydet ja liikkumiset lasketaan samaan aikaan jollain aikavälillä. 
Pienentämällä aikaväliä simulaatiosta voi tehdä tarkemman.


## linkkejä
[määrittely](https://github.com/nicohi/otm-harjoitustyo/blob/master/harjoitustyo/outline.md)

[työaikakirjanpito](https://github.com/nicohi/otm-harjoitustyo/blob/master/harjoitustyo/tyoaikakirjanpito.md)

[arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

# Ohjeita
## lataaminen
```
git clone https://github.com/nicohi/otm-harjoitustyo
cd otm-harjoitustyo
```
## suorittaminen
```
mvn compile exec:java -Dexec.mainClass=nicohi.planetsim.Main
```
## luo .jar tiedosto kansioon target/
```
mvn package
```
## testaus
```
mvn test
```
## automaattinen raportti
```
mvn test jacoco:report
```
## checkstyle
```
mvn jxr:jxr checkstyle:checkstyle
```
