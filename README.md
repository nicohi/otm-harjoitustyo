# planetsim
Javalla ohjelmoitu [n-kappale](https://en.wikipedia.org/wiki/N-body_simulation) simulaattori painovoimalle ja planeetoille. 
Fysiikka on mallinnettu mahdollisimman realistisesti. 

Vaatii: java ja javafx

![ex1](dokumentaatio/ex1.png?raw=true)

## linkkejä
[uusin release](https://github.com/nicohi/otm-harjoitustyo/releases/)

[käyttöohje](dokumentaatio/kayttoohje.md)

[määrittely](dokumentaatio/outline.md)

[työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

[arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

[testit](dokumentaatio/testit.md)

# ohjeita
## lataaminen ja suorittaminen
Joko lataa ja suorita [uusin jar tiedosto](https://github.com/nicohi/otm-harjoitustyo/releases/) komenolla
```
java -jar planetsim.jar
```
tai jos haluat kasata itse lähdekoodista
```
git clone https://github.com/nicohi/otm-harjoitustyo
cd otm-harjoitustyo
mvn compile exec:java -Dexec.mainClass=nicohi.planetsim.Main
```
# muita ohjeita
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
## javadoc
```
mvn javadoc:javadoc
```
