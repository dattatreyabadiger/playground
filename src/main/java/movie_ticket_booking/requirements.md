FR:
1. User able to search movies by name/city/language
2. User able to view details of movie (director, cast, duration)
3. User able to view multiple theaters for a movie and show times
4. User able to see seat layout (available/booked)
5. User can select one or more seat and hold them temporarily
6. User able to book tickets after payment is done
7. User able to cancel a booking

NFR:
1. Lock the seat before initiating payment after seat is selected
2. Lock for a specific time (5 mins) if payment isn't completed
3. On contention for same seat, first request to lock wins; others get immediate failure
4. Booking api must be idempotent under retries
5. System favors consistency over availability for booking path


Entities/workflow:
Map<City, List<Movie>>
Map<Movie, List<Theater>>

Movie - name, duration, ...

Theater - id, name, List<Screen>
Screen - id, theatreId, List<Seat>
Seat - id, screenId, row, seatNumber, category(GOLD/SILVER/PREMIUM)

Show - id, movieId, screenId, start time, end time, List<ShowSeat>
ShowSeat - id, showId, seatId, status(AVAILABLE/LOCKED/BOOKED), lockedBy: userId(nullable), lockExpiresAt(nullable), bookId(nullable)

Booking - id, userId, showId, List<ShowSeat>, status(PENDING|CONFIRMED|CANCELLED|EXPIRED), created_at, updated_at, paymentId
Payment - id, bookingId, amount, method(CASH/CARD/UPI), status(SUCCESS/FAILED/PENDING)