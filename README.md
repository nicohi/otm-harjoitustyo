# planetsim
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
