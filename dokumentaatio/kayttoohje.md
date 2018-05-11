# Käyttöohje
Lataa [jar tiedosto](https://github.com/nicohi/otm-harjoitustyo/releases/).

## Ohjelman käynnistäminen
Ohjelma käynnistyy joko tuplaklikkaamalla .jar tiedostoa tai komenolla
```
java -jar planetsim.jar
```

## Yleinen Käyttö
Sovelluksen pitäisi näyttää tältä käynnistyttyään

![kl](kliittyma.png?raw=true)

- Planeettoja voi lisätä painamalla "add planet". Planeetta lisätään paramatreillä jotka lukevat tekstikentissä. Parametrejä voi muuttaa.
- Painamalla "save" sovellus tallentaa tämänhetkisen tilan tietokantaan "planetdb" **siihen polkuun mistä ohjelma on käynnistetty**. "load" lataa tilan samasta tietokannasta.
- "time" ja "scale" muuttavat simulaation aikaväliä ja 'zoomia'
- "unpause/pause" pysäyttää ja käynnistää simulaation. HUOM pause:in aikana tekemäsi muutokset näkyvät vasta unpaus:in jälkeen
- "clear" poistaa kaikki planeetat (ei poista tietokannantaan säilöttyä tilaa)
