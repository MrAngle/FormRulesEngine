VERSION: 1.05
ZAWSZE Potwierdzaj, że przeczytałeś i przeanalizowałeś te zasady z konkretna wersja.

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

🧪 TESTY (priorytet jesli uzyty zapis "&Testy" - potwierdz ze o tym wiesz)
W testach uzywany jest spock

    Testy powinny znajdować się w folderze:
    src/test/groovy/com/example/initproject/{package}

    Nie przesadzaj z parametryzacją – testy powinny być czytelne. W razie potrzeby rozdzielaj przypadki.

    W Spocku należy porównywać typy (np. double i BigDecimal) w sposób właściwy – unikaj == w takich przypadkach. Wtedy uzywaj Math

    Kod w Spocku (Groovy) musi być zgodny z zasadami języka Java – w szczególności jeśli chodzi o konstruktory i parametry.

	(BARDZO WAŻNE) Jeśli logika działania jest niejednoznaczna lub sprzeczna – wymuszaj oczekiwane zachowanie. Nie dopuszczaj do sytuacji, w której test „przechodzi”, mimo że zachowanie nie jest jasne.

    Uwazaj z porownywaniem po referencji, jesli porownywane sa obiekty

    Staraj się wychwycić wszystkie nieobsłużone przypadki oraz błędne zachowania.

    Uwzględniaj testy brzegowe.

    Jeśli jakiś scenariusz nie został uwzgledniony, stworz test (w tym null checki) – dodaj komentarz w wygenerowanym teście z informacją, że ten przypadek nie został jeszcze uwzględniony.
    
    Uwzglednij w testach sprawdzanie czy moze poleciec NPE

    Unikaj duplikacji testów/scenariuszy.

    wygeneruj kompletne testy jednostkowe dla poniższej metody, obejmujące:
    Scenariusze pozytywne (poprawne dane wejściowe).
    Scenariusze negatywne (błędne dane wejściowe).
    Przypadki brzegowe.
    Scenariusze, które mogą prowadzić do NullPointerException (np. przekazanie null jako argumentu, niezainicjalizowane pola).
    Upewnij się, że każdy przypadek testowy zawiera:
    Opis testowanego scenariusza.
    Oczekiwany wynik.
    Odpowiednie asercje

    (BARDZO WAŻNE) Nigdy nie twórz testów, w których oczekiwanym rezultatem jest throw(NullPointerException).
    Zamiast tego, w przypadku braku obsługi danej sytuacji, test powinien zakończyć się fail(), aby wyraźnie zasygnalizować niezaimplementowany przypadek.
    ❌ Niepoprawnie: then: "An exception is thrown" thrown(NullPointerException) - to nie jest oczekiwany rezultat
    ✅ Poprawnie: then: fail("This case is currently not handled.")

    Opis w testach:
    Nigdy nie pisz opisu typu "Should fail for...". Zamiast tego, opisuj przypadek, który nie powinien mieć miejsca.
    Nie zakładaj, że kod jest poprawny – kwestionuj jego logikę.

    I want to test the <functionality name> functionality of my web application. Are the following test cases/scenarios enough for 100% test coverage?

🛠️ DODATKOWE UWAGI I STANDARDY

    Jeśli ograniczenia długości odpowiedzi uniemożliwiają przedstawienie pełnej odpowiedzi, poinformuj mnie o tym wyraźnie. W takim przypadku skup się na konkretnych elementach i kontynuuj odpowiedź w kolejnych wiadomościach.

    Unikaj komentarzy w kodzie, z wyjątkiem sytuacji wyjątkowych (np. możliwe błędy, TODO).

    Używaj terminologii angielskiej.

    Utrzymuj ton techniczny i precyzyjny, skupiając się na jasnym przedstawieniu przypadków testowych i oczekiwanych wyników

    Cały kod powinien być pisany w języku angielskim.

    Maksymalna długość linii: 121 znaków – przestrzegaj tego limitu.

    Kwestionuj, krytykuj i proś o dodatkowy kontekst, jeśli jest to potrzebne.