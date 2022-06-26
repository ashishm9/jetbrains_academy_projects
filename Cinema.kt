package cinema

const val NEW_TICKET = 1
const val NO_TICKET = 0
const val BOOKED_SEAT = "B"
const val EMPTY_SEAT = "S"
const val SMALL_THEATER_SEATS = 60
const val REGULAR_TICKET_PRICE = 10
const val BACKSEAT_TICKET_PRICE = 8

fun buildTheater(rows: Int, seatsPerRow: Int): MutableList<MutableList<String>> {
    return MutableList(rows){MutableList(seatsPerRow){EMPTY_SEAT}}
}

fun showSeats(theater: MutableList<MutableList<String>>, rows: Int, seatsPerRow: Int) {
    println("\nCinema:")
    print("  ")

    for(seat in 1..seatsPerRow) {
        print("$seat ")
    }
    println()
    for(row in 0 until rows) {
        print("${row + 1} ")
        for(seat in 0 until seatsPerRow) {
            print("${theater[row][seat]} ")
        }
        println()
    }
    println()
}

fun computeTicketPrice(rowNumber: Int, rows: Int, totalSeats: Int): Int {
    return if(totalSeats <= SMALL_THEATER_SEATS) {
        REGULAR_TICKET_PRICE
    }
    else {
        if(rowNumber <= rows / 2) REGULAR_TICKET_PRICE else BACKSEAT_TICKET_PRICE
    }
}

fun buyTicket(theater: MutableList<MutableList<String>>, rows: Int, seatsPerRow: Int, totalSeats: Int): MutableList<Int> {
    while(true) {
        println("\nEnter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()
        if (rowNumber <= rows && seatNumber <= seatsPerRow) {
            if (theater[rowNumber - 1][seatNumber - 1] != BOOKED_SEAT) {
                val ticketPrice = computeTicketPrice(rowNumber, rows, totalSeats)
                println("\nTicket Price: $$ticketPrice\n")
                theater[rowNumber - 1] = theater[rowNumber - 1].toMutableList()
                theater[rowNumber - 1][seatNumber - 1] = BOOKED_SEAT
                return mutableListOf(NEW_TICKET, ticketPrice)
            } else println("That ticket has already been purchased!\n")
        } else println("Wrong input!\n")
    }
}

fun showStats(rows: Int,seatsPerRow: Int, tickets: Int, totalSeats: Int, income: Int) {
    println("\nNumber of purchased tickets: $tickets")
    val percentage = (tickets.toFloat() / totalSeats) * 100
    val formatPercentage = "%.2f".format(percentage)
    val totalIncome = if(totalSeats <= SMALL_THEATER_SEATS) {
        totalSeats * REGULAR_TICKET_PRICE
    } else ((rows / 2) * seatsPerRow * REGULAR_TICKET_PRICE) + ((rows - (rows / 2)) * seatsPerRow * BACKSEAT_TICKET_PRICE)
    println("Percentage: $formatPercentage%")
    println("Current income: $$income")
    println("Total income: $$totalIncome\n")
}

fun menu() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    val totalSeats = rows * seatsPerRow
    var tickets = NO_TICKET
    var income = NO_TICKET
    val theater = buildTheater(rows, seatsPerRow)
    println()
    while (true) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        when (readln().toInt()) {
            1 -> showSeats(theater, rows, seatsPerRow)
            2 -> {
                val ticketAndIncome = buyTicket(theater, rows, seatsPerRow, totalSeats)
                tickets += ticketAndIncome[0]
                income += ticketAndIncome[1]
            }
            3 -> showStats(rows, seatsPerRow, tickets, totalSeats, income)
            0 -> break
            else -> println("Wrong option.\n")
        }
    }
}

fun main() {
    menu()
}