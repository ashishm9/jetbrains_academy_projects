package tictactoe

const val GAME_INCOMPLETE = 2
const val WIN = 1
const val DRAW = 0

fun isGameOn(gameBoard: MutableList<MutableList<String>>): Boolean {
    for(row in gameBoard) if("_" in row) return true
    return false
}

fun getPosition(gameBoard: MutableList<MutableList<String>>): MutableList<String> {
    val result = mutableListOf<String>()
    val check = mutableListOf("X", "O")
    if(gameBoard[0][1] in check && gameBoard[0][1] == gameBoard[0][2] && gameBoard[0][1] == gameBoard[0][3])
        result += gameBoard[0][1]
    if(gameBoard[1][1] in check && gameBoard[1][1] == gameBoard[1][2] && gameBoard[1][1] == gameBoard[1][3])
        result += gameBoard[1][1]
    if(gameBoard[2][1] in check && gameBoard[2][1] == gameBoard[2][2] && gameBoard[2][1] == gameBoard[2][3])
        result += gameBoard[2][1]
    if(gameBoard[0][1] in check && gameBoard[0][1] == gameBoard[1][1] && gameBoard[0][1] == gameBoard[2][1])
        result += gameBoard[0][1]
    if(gameBoard[0][2] in check && gameBoard[0][2] == gameBoard[1][2] && gameBoard[0][2] == gameBoard[2][2])
        result += gameBoard[0][2]
    if(gameBoard[0][3] in check && gameBoard[0][3] == gameBoard[1][3] && gameBoard[0][3] == gameBoard[2][3])
        result += gameBoard[0][3]
    if(gameBoard[0][1] in check && gameBoard[0][1] == gameBoard[1][2] && gameBoard[0][1] == gameBoard[2][3])
        result += gameBoard[0][1]
    if(gameBoard[0][3] in check && gameBoard[0][3] == gameBoard[1][2] && gameBoard[0][3] == gameBoard[2][1])
        result +=  gameBoard[0][3]
    return result
}

fun gameState(gameBoard: MutableList<MutableList<String>>): Int {
    val result = getPosition(gameBoard)
    if(result.isEmpty() && !isGameOn(gameBoard)) {
        println("Draw")
        return DRAW
    }
    else if(result.size == 1) {
        println("${result[0]} wins")
        return WIN
    }
    return GAME_INCOMPLETE
}


fun displayBoard(gameBoard: MutableList<MutableList<String>>) {
    println("---------")
    for(row in 0..2) {
        for(col in 0..4) print("${gameBoard[row][col]} ")
        println()
    }
    println("---------")
}

fun buildBoard(): MutableList<MutableList<String>> {
    val row = mutableListOf("|", "_", "_", "_", "|")
    return MutableList(3) { row.toMutableList() }
}

fun setPosition(gameBoard: MutableList<MutableList<String>>, player: String): Int {
    while(true) {
        print("Enter the coordinates: ")
        try {
            val (x, y) = readln().split(" ")
            val row = x.toInt() - 1
            val col = y.toInt()
            if(row > 2 || col > 3) println("Coordinates should be from 1 to 3!")
            else if(gameBoard[row][col] == "_") {
                gameBoard[row][col] = player
                return if(player == "X") 1 else 0
            }
            else println("This cell is occupied! Choose another one!")
        } catch(e: Exception) {
            println("You should enter numbers!")
        }
    }
}

fun startGame() {
    val player = mutableListOf("X", "O")
    var playerIndex = 0
    val gameBoard = buildBoard()
    displayBoard(gameBoard)
    while(gameState(gameBoard) == GAME_INCOMPLETE) {
        playerIndex = setPosition(gameBoard, player[playerIndex])
        displayBoard(gameBoard)
    }
}

fun main() {
    startGame()
}