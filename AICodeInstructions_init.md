VERSION: 1.02
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

    Trzymaj ise zasad KISS/YAGNI/DRY/SOLID

API:

    Tworzone jest Backend-BFF (Backend for Frontend) API, które będzie wykorzystywane przez aplikację frontendową.
    Powinno być dostepnę za pomocą swaggera, który będzie generował dokumentację API.
    Swagger powinien nie byc dostepny na srodowisku produkcyjnym.
    Endpointy powinny być zabezpieczone przed atakami CSRF i XSS.
    Bazuj na najlepszych praktykach definiowania API RESTful.

    | Zasób                        | Endpoint                                    |
    | ---------------------------- | ------------------------------------------- |
    | **Swagger UI**               | `http://localhost:8080/swagger-ui.html`     |
    | **OpenAPI JSON**             | `http://localhost:8080/v3/api-docs`         |
    | **OpenAPI JSON (per group)** | `http://localhost:8080/v3/api-docs/{group}` |



    Uwzgledniaj wersje w API, np. /api/v1/...

    /api/v1/
    │
    ├── dictionaries/                        # 🔁 Słowniki / dane wspólne dla wszystkich kontekstów
    │   ├── vehicles/
    │   │   ├── makes                    # Lista marek pojazdów
    │   │   ├── models?make=Toyota      # Lista modeli dla danej marki
    │   │   └── versions?model=Yaris    # Wersje modelu
    │   ├── fuels                       # Typy paliw
    │   ├── colors                      # Dostępne kolory
    │   ├── countries                   # Lista krajów
    │   ├── postal-codes/{code}        # Sprawdzenie kodu pocztowego
    │   └── insurance-types             # Typy ubezpieczeń
    │
    ├── new-business/                    # 🧾 Proces nowej polisy
    │   ├── policies/
    │   │   ├── {policyId}              # Pobierz polisę
    │   │   └──                         # Stwórz nową polisę
    │   ├── quote                       # Wycena
    │   ├── eligibility                 # Sprawdzenie kwalifikacji
    │   └── questions                   # Pytania underwritingowe
    │
    ├── renewal/                         # 🔁 Proces odnowienia
    │   ├── policies/
    │   │   ├── {policyId}
    │   │   └──
    │   ├── quote
    │   ├── eligibility
    │   └── questions
    │
    ├── aftersales/                      # 🛠️ Obsługa posprzedażowa
    │   ├── policies/
    │   │   ├── {policyId}
    │   │   └──
    │   ├── modify                      # Zmiana danych
    │   ├── cancel                      # Rezygnacja
    │   └── documents                   # Dokumenty klienta
    │
    └── session/                         # 🔐 Kontekst sesji / logowania / śledzenia
    ├── init                        # Inicjalizacja sesji
    ├── current                     # Aktualna sesja
    └── logout

    

Biznesowe zasady:

    Tworze aplikacje do wykonywania zadań w obszarze ubezpieczeń.
    Aplikacja słuzy do obsługi polis jako Renewal (odnownie polisy), new Business (nowa polisa) lub Aftersales (obsługa posprzedażowa).
    Aplikacja może być uzywana przez agenta lub konsultanta call center
    Aplikacja jest podzielona na moduły, które odpowiadają za różne aspekty procesu ubezpieczeniowego.

Tech stack:

    Java 21
    Angular 18
    Spring Boot 3.4
    GRADLE


Moduły:

    Checkout
    Customerjourney
    DataProcurment
    Offering
    Session
    Underwritingcase

    Customer Journey
    Główny proces zarządzający całą ścieżką sprzedażową, utrzymujący stan procesu i umożliwiający jego wznowienie w dowolnym momencie i kanale sprzedaży (agent, klient bezpośredni, call center). W DDD pełni rolę agregatu nadrzędnego dla pozostałych podprocesów.
    
    Data Procurement
    Moduł odpowiedzialny za zbieranie wszystkich danych potrzebnych do kalkulacji oferty u zewnętrznego silnika wyceniającego. Dane te gromadzi w obiekcie Calculation Context, który udostępnia innym modułom.
    
    Forms
    Obsługuje formularze wypełniane przez użytkownika, zawierające pytania niezbędne do oceny ryzyka, dynamicznie dostosowując ich zawartość na podstawie kontekstu. Formularze przekazują dane do dalszych transformacji.
    
    Data Enrichment / Data Transformation
    Zestaw transformatorów danych działających jak czyste funkcje – przetwarzają dane wejściowe (np. VIN) i zwracają wzbogacone dane (np. historia pojazdu), które trafiają do Calculation Context. Umożliwia warunkowe pytania w formularzach w zależności od uzyskanych danych.
    
    Vehicle Specification
    Dedykowany moduł do określania konfiguracji pojazdu (marka, model, wersja itd.), wielokrotnego użytku np. w ubezpieczeniach flot. Jego wynik może być bezpośrednio użyty w Calculation Context.
    
    Offering
    Serce procesu sprzedażowego – generuje propozycje ubezpieczeń, warianty rozszerzeń i rekomendacje na bazie reguł lub danych statystycznych. Umożliwia symulacje zmian w zakresie ochrony i wspiera agentów lub klientów w wyborze optymalnego pakietu.
    
    Checkout
    Końcowy etap sprzedaży, w którym klient finalizuje transakcję poprzez płatność i otrzymuje dokumenty. Dodatkowo zbierane są dane do obsługi posprzedażowej i zgody marketingowe.

🛠️ DODATKOWE UWAGI I STANDARDY


    Unikaj komentarzy w kodzie, z wyjątkiem sytuacji wyjątkowych (np. możliwe błędy, TODO).

    Używaj terminologii angielskiej.

    Cały kod powinien być pisany w języku angielskim.



    Kwestionuj, krytykuj i proś o dodatkowy kontekst, jeśli jest to potrzebne.