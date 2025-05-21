VERSION: 5.00
Potwierdź, że przeczytałeś i przeanalizowałeś te zasady z konkretna wersja.

📌 ZASADY OGÓLNE 

    Wszystkie akcje powinny być implementowane jako struktury z metodami w stylu obiektowym (OOP), z użyciem konstruktorów.

    Nazwy zmiennych powinny być w języku angielskim, zapisane w formacie camelCase.

    Do debugowania używamy LOG_DEBUG_MESSAGE(msg).

    Należy unikać zmiennych globalnych, chyba że dotyczą konfiguracji (CONFIG) lub warstw (LAYER).

    Stosujemy Lombok do automatyzacji boilerplate.

    Zawsze uwzględniaj przypadki brzegowe.

    Jeśli jakiś przypadek nie został obsłużony, zgłoś to wyraźnie.

    Krytykuj wszelkie rozwiązania i podejścia – jeśli dostrzegasz lepszą alternatywę, zaproponuj ją.

    Sporządź listę najlepszych praktyk w danym zakresie.

    Dokonaj przeglądu dostarczonej funkcji (tempFunction) pod kątem błędów logicznych i bezpieczeństwa. Zgłoś rekomendacje.

    Zweryfikuj swoje rekomendacje – wskaż, które były błędne, pominięte lub niepotrzebne.

🧪 TESTY (priorytet jesli uzyty zapis "&Testy")

    (BARDZO WAŻNE) Nigdy nie twórz testów, w których oczekiwanym rezultatem jest throw(NullPointerException).
    Zamiast tego, w przypadku braku obsługi danej sytuacji, test powinien zakończyć się fail(), aby wyraźnie zasygnalizować niezaimplementowany przypadek.

    ❌ Niepoprawnie:
then: "An exception is thrown"
thrown(NullPointerException)

✅ Poprawnie:
    then:
    fail("This case is currently not handled.")

    Opis w testach:
    Nigdy nie pisz opisu typu "Should fail for...". Zamiast tego, opisuj przypadek, który nie powinien mieć miejsca.
    Nie zakładaj, że kod jest poprawny – kwestionuj jego logikę.

    (BARDZO WAŻNE) Jeśli logika działania jest niejednoznaczna lub sprzeczna – wymuszaj oczekiwane zachowanie. Nie dopuszczaj do sytuacji, w której test „przechodzi”, mimo że zachowanie nie jest jasne.

    Staraj się wychwycić wszystkie nieobsłużone przypadki oraz błędne zachowania. Każdy zidentyfikowany przypadek zapisz jako test, który powinien failować i jednoznacznie opisywać oczekiwany rezultat.

    Uwzględniaj testy brzegowe.

    Nie przesadzaj z parametryzacją – testy powinny być czytelne. W razie potrzeby rozdzielaj przypadki.

    Jeśli jakiś scenariusz nie został zaimplementowany – dodaj komentarz w wygenerowanym teście z informacją, że ten przypadek nie został jeszcze uwzględniony.

    W Spocku należy porównywać typy (np. double i BigDecimal) w sposób właściwy – unikaj == w takich przypadkach.

    Unikaj duplikacji testów/scenariuszy.

    Kod w Spocku (Groovy) musi być zgodny z zasadami języka Java – w szczególności jeśli chodzi o konstruktory i parametry.

    Nie zapominaj o pokryciu kluczowych scenariuszy tzw. happy paths.

    Testy powinny znajdować się w folderze:
    src/test/groovy/com/example/initproject/{package}

🛠️ DODATKOWE UWAGI I STANDARDY

    Maksymalna długość linii: 121 znaków – przestrzegaj tego limitu.

    Unikaj komentarzy w kodzie, z wyjątkiem sytuacji wyjątkowych (np. możliwe błędy, TODO).

    Używaj terminologii angielskiej.

    Cały kod powinien być pisany w języku angielskim.



    Kwestionuj, krytykuj i proś o dodatkowy kontekst, jeśli jest to potrzebne.