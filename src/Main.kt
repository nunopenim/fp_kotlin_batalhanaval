import java.io.*
import java.util.*

fun processaNomeJogador(texto: String): String? {
    val nome = texto
    var nomeValido = true
    var posicao = 0
    var spaceOnly = true
    if (nome.isEmpty()){
        nomeValido = false
    }
    else {
        var inicio = 0
        while (inicio < nome.length) {
            if (nome[inicio] != ' ') {
                inicio = nome.length
                spaceOnly = false
            }
            inicio++
        }
        inicio = 0
        if (!spaceOnly) {
            while (nome[inicio] == ' ') inicio++
            posicao = inicio
        }
        if (spaceOnly || nome.length < 2 || nome.isEmpty() || nome[posicao].isLowerCase()) {
            nomeValido = false
        }else if(nome[posicao].isDigit() || !nome[posicao].isLetter()){
            nomeValido = false
        } else if ((nome.length == 2 && nome[inicio].isUpperCase() && nome[inicio + 1].toUpperCase() == nome[inicio])) {
            nomeValido = false //mudar para false caso não aceite nomes de duas letras iguais
        }else if((nome[inicio].isUpperCase() && (nome.length - inicio < 3) && nome[inicio + 1].toUpperCase() == nome[inicio])){
            nomeValido = false
        } else {
            posicao++
            do {
                if (nome[posicao] != ' ') {
                    if (((nome[posicao] in 'A'..'Z') && (nome[posicao - 1] != ' ')) || nome[posicao].isDigit() || !nome[posicao].isLetter()){
                        nomeValido = false
                    }
                }
                posicao++
            } while (nomeValido && posicao < nome.length && (nome[posicao - 1] != ' '))
        }
    }
    if (!nomeValido) {
        return null
    }
    var count = 0
    var nomeJogador = ""
    while (nome[count] == ' ') {
        count++
    }
    while (count < nome.length && nome[count] != ' ') {
        val letra = nome[count]
        nomeJogador = "$nomeJogador$letra"
        count++
    }
    return nomeJogador
} //feita, works

fun insereNavio(tabuleiro: Array<Char?>, posicaoNavio: Int, dimensaoNavio: Int): Boolean {
    val posicao = posicaoNavio - 1
    if (dimensaoNavio < 1 || dimensaoNavio > 3) return false
    if (posicao < 0 || posicao > tabuleiro.size) return false
    if (dimensaoNavio == 3) {
        if (posicao > tabuleiro.size - 3 || tabuleiro[posicao] != null && tabuleiro[posicao] != '~' || tabuleiro[posicao + 1] != null && tabuleiro[posicao + 1] != '~') {return false}
        else if((posicao < tabuleiro.size - 3 && (tabuleiro[posicao + 3] != null && tabuleiro[posicao + 3] != '~'))){
            return false
        }
        else if((posicao > 1 && tabuleiro[posicao - 1] != null && tabuleiro[posicao - 1] != '~')){
            return false
        }
        else if(tabuleiro[posicao + 2] != null && tabuleiro[posicao + 2] != '~'){
            return false
        }
        return true
    }
    if (dimensaoNavio == 2) {
        if (posicao > tabuleiro.size - 2 || tabuleiro[posicao] != null && tabuleiro[posicao] != '~' || tabuleiro[posicao + 1] != null && tabuleiro[posicao + 1] != '~'){ return false}
        else if((posicao > 1 && tabuleiro[posicao - 1] != null && tabuleiro[posicao - 1] != '~')){
            return false
        }
        else if((posicao < tabuleiro.size - 2 && (tabuleiro[posicao + 2] != null && tabuleiro[posicao + 2] != '~'))){
            return false
        }
        return true
    }
    if (dimensaoNavio == 1) {
        if (posicao > tabuleiro.size - 1 || tabuleiro[posicao] != null && tabuleiro[posicao] != '~') {return false}
        else if ((posicao > 0 && tabuleiro[posicao - 1] != null && tabuleiro[posicao - 1] != '~')){
            return false
        }
        else if ((posicao < tabuleiro.size - 1 && (tabuleiro[posicao + 1] != null && tabuleiro[posicao + 1] != '~'))){
            return false
        }
        return true
    }
    return false
} //feita, works

fun geraMapaComputador(tabuleiro: Array<Char?>, numNaviosTanque: Int, numContratorpedeiros: Int, numSubmarinos: Int){
    val tamanhoTabuleiro = tabuleiro.size
    //var tabuleiroPC: Array<Char?>
    var computadorValido = false //para já
    if(tamanhoTabuleiro<=10){
        var tanqueComppos = 0
        var torpedeiroComPos = 0
        var submarinoCompPos = 0
        while (!computadorValido) {
            var numeroChecks = 0
            tanqueComppos = Random().nextInt(tamanhoTabuleiro)
            while ((tanqueComppos < 1 || tanqueComppos > (tamanhoTabuleiro - 2)) && numeroChecks < 20) {
                tanqueComppos = Random().nextInt(tamanhoTabuleiro)
                numeroChecks++
            }
            torpedeiroComPos = Random().nextInt(tamanhoTabuleiro)
            while ((torpedeiroComPos < 1 || torpedeiroComPos > (tamanhoTabuleiro - 1) || torpedeiroComPos == tanqueComppos || torpedeiroComPos == tanqueComppos - 1 || torpedeiroComPos == tanqueComppos - 2 || torpedeiroComPos == tanqueComppos + 1 || torpedeiroComPos == tanqueComppos + 2 || torpedeiroComPos == tanqueComppos + 3) && numeroChecks < 20) {
                torpedeiroComPos = Random().nextInt(tamanhoTabuleiro)
                numeroChecks++
            }
            submarinoCompPos = Random().nextInt(tamanhoTabuleiro)
            while ((submarinoCompPos < 1 || submarinoCompPos > tamanhoTabuleiro || submarinoCompPos == tanqueComppos || submarinoCompPos == tanqueComppos - 1 || submarinoCompPos == tanqueComppos + 1 || submarinoCompPos == tanqueComppos + 2 || submarinoCompPos == tanqueComppos + 3 || submarinoCompPos == torpedeiroComPos || submarinoCompPos == torpedeiroComPos - 1 || submarinoCompPos == torpedeiroComPos + 1 || submarinoCompPos == torpedeiroComPos + 2) && numeroChecks < 20) {
                submarinoCompPos = Random().nextInt(tamanhoTabuleiro)
                numeroChecks++
            }
            if (numeroChecks < 20){
                computadorValido = true
            }
        }
        tabuleiro[tanqueComppos-1]='3'
        tabuleiro[tanqueComppos]='3'
        tabuleiro[tanqueComppos+1]='3'
        tabuleiro[torpedeiroComPos-1]='2'
        tabuleiro[torpedeiroComPos]='2'
        tabuleiro[submarinoCompPos-1]='1'

    }
    else{
        do {
            //tabuleiroPC = arrayOfNulls<Char?>(tamanhoTabuleiro)
            var posicao = Random().nextInt((tabuleiro.size - 1)) + 1
            var checkCounter = 0
            var navioValido: Boolean
            for (countTanques in 1..numNaviosTanque) {
                do {
                    navioValido=false
                    navioValido = insereNavio(tabuleiro, posicao, 3)
                    if (!navioValido){
                        posicao = Random().nextInt((tabuleiro.size - 1)) + 1
                    }
                    else if (navioValido) {
                        for (i in -1..1) {
                            tabuleiro[posicao + i] = '3'
                        }
                    }
                    checkCounter++
                } while (!navioValido && checkCounter < 20)
            }
            checkCounter = 0
            posicao = Random().nextInt((tabuleiro.size - 1)) + 1
            for (countTorpedeiros in 1..numContratorpedeiros) {
                do {
                    navioValido=false
                    navioValido = insereNavio(tabuleiro, posicao, 2)
                    if (!navioValido){
                        posicao = Random().nextInt((tabuleiro.size - 1)) + 1
                    }
                    else if (navioValido) {
                        for (i in -1..0) {
                            tabuleiro[posicao + i] = '2'
                        }
                    }
                    checkCounter++
                } while (!navioValido && checkCounter < 20)
            }
            checkCounter = 0
            posicao = Random().nextInt((tabuleiro.size - 1)) + 1
            for (countSubmarinos in 1..numSubmarinos) {
                do {
                    navioValido=false
                    navioValido = insereNavio(tabuleiro, posicao, 1)
                    if (!navioValido){
                        posicao = Random().nextInt((tabuleiro.size - 1)) + 1
                    }
                    else if (navioValido) tabuleiro[posicao - 1] = '1'
                    checkCounter++
                } while (!navioValido && checkCounter < 20)
            }
            if (checkCounter < 20) computadorValido = true
        } while (!computadorValido)
    }
} //feita, works

fun obtemMapa(tabuleiro: Array<Char?>, real: Boolean): Array<String> {
    var count = 1
    var number = 1
    var stringTabuleiro = ""
    var stringUnidades = ""
    var stringDezenas = ""
    if (real) {
        for (i in 0..tabuleiro.size - 1) {
            if (tabuleiro[i] == null){
                tabuleiro[i] = '~'
            }
        }
        for (i in 0..tabuleiro.size - 1) {
            val simboloNoTabuleiro: Char? = tabuleiro[i]
            stringTabuleiro = "$stringTabuleiro$simboloNoTabuleiro"
        }
        while (count <= tabuleiro.size) {
            if (number <= 10) {
                if (number == 10) {
                    stringUnidades = "${stringUnidades}0"
                    number = 0
                } else stringUnidades = "$stringUnidades$number"
                number++
                count++
            }
        }
        count = 1
        number = 1
        if (tabuleiro.size +1 >= 10) {
            while (count <= tabuleiro.size) {
                if (count % 10 == 0) {
                    stringDezenas = "$stringDezenas$number"
                    number++
                } else stringDezenas = "$stringDezenas "
                count++
            }
        }
    }
    if(!real){
        for (i in 0..tabuleiro.size - 1) {
            if (tabuleiro[i] == null){
                tabuleiro[i] = '?'
            }
        }
        for (i in 0..tabuleiro.size-1){
            if(tabuleiro[i]=='3'){
                tabuleiro[i]='\u2083'
            }
            else if(tabuleiro[i]=='2'){
                tabuleiro[i]='\u2082'
            }
        }
        for(i in 0..tabuleiro.size-3){
            if(tabuleiro[i]=='\u2083'&&tabuleiro[i+1]=='\u2083'&&tabuleiro[i+2]=='\u2083'){
                tabuleiro[i]='3'
                tabuleiro[i+1]='3'
                tabuleiro[i+2]='3'
            }
        }
        for(i in 0..tabuleiro.size-2){
            if(tabuleiro[i]=='\u2082'&&tabuleiro[i+1]=='\u2082'){
                tabuleiro[i]='2'
                tabuleiro[i+1]='2'
            }
        }
        for (i in 0..tabuleiro.size - 1) {
            val simboloNoTabuleiro: Char? = tabuleiro[i]
            stringTabuleiro = "$stringTabuleiro$simboloNoTabuleiro"
        }
        while (count <= tabuleiro.size) {
            if (number <= 10) {
                if (number == 10) {
                    stringUnidades = "${stringUnidades}0"
                    number = 0
                } else stringUnidades = "$stringUnidades$number"
                number++
                count++
            }
        }
        count = 1
        number = 1
        if (tabuleiro.size + 1 >= 10) {
            while (count <= tabuleiro.size) {
                if (count % 10 == 0) {
                    stringDezenas = "$stringDezenas$number"
                    number++
                } else stringDezenas = "$stringDezenas "
                count++
            }
        }
    }
    val arrayRetorno = arrayOf(stringTabuleiro, stringUnidades, stringDezenas)
    return arrayRetorno
} //feita, works

fun calculaNumNavios(tamanhoTabuleiro: Int): Array<Int> {
    var numNaviosTanque = -1
    var numTorpedeiros = -1
    var numSubmarinos = -1
    if (tamanhoTabuleiro in 1..30) {
        numNaviosTanque = 1
        numTorpedeiros = 1
        numSubmarinos = 1
    } else if (tamanhoTabuleiro in 31..50) {
        numNaviosTanque = 1
        numTorpedeiros = 1
        numSubmarinos = 3
    } else if (tamanhoTabuleiro in 51..80) {
        numNaviosTanque = 1
        numTorpedeiros = 2
        numSubmarinos = 5
    } else if (tamanhoTabuleiro in 81..99) {
        numNaviosTanque = 2
        numTorpedeiros = 3
        numSubmarinos = 6
    }
    val arrayNavios = arrayOf(numNaviosTanque, numTorpedeiros, numSubmarinos)
    return arrayNavios
} //feita, works

fun lancarTiro(tabuleiroReal: Array<Char?>, tabuleiroPalpites: Array<Char?>, posicaoTiro: Int): String {
    val agua = "Agua."
    val tanque = "Tiro num navio-tanque."
    val torpedeiro = "Tiro num contratorpedeiro."
    val submarino = "Tiro num submarino."
    if(posicaoTiro == -1) return ""
    if(posicaoTiro<1 || posicaoTiro>tabuleiroPalpites.size) return ">>> Posicao invalida. Tente novamente"
    when (tabuleiroReal[posicaoTiro-1]) {
        '3' -> {
            tabuleiroPalpites[posicaoTiro-1] = '3'
            return tanque
        }
        '2' -> {
            tabuleiroPalpites[posicaoTiro-1] = '2'
            return torpedeiro
        }
        '1' -> {
            tabuleiroPalpites[posicaoTiro-1] = '1'
            return submarino
        }
        else -> {
            tabuleiroPalpites[posicaoTiro-1] = 'X'
            return agua
        }
    }
}

fun convertMaps(tabuleiroChars: Array<Char?>){
    for (i in 0..tabuleiroChars.size-1){
        if (tabuleiroChars[i]=='\u2083' || tabuleiroChars[i]=='3'){
            tabuleiroChars[i]='3'
        }
        if (tabuleiroChars[i]=='\u2082'|| tabuleiroChars[i]=='2'){
            tabuleiroChars[i]='2'
        }
        if (tabuleiroChars[i]=='?'||tabuleiroChars[i]=='~'){
            tabuleiroChars[i]=null
        }
    }
}

fun stringToArray(stringAConverter: String): Array<Char?>{
    val stringSize = stringAConverter.length
    val arrayDeRetorno = arrayOfNulls<Char?>(stringSize)
    var count = 0
    while(count<stringSize){
        arrayDeRetorno[count]=stringAConverter[count]
        count++
    }
    return arrayDeRetorno
}

fun lancarTiroPossible(tabuleiroPalpites: Array<Char?>, posicaoTiro: Int): Boolean {
    if(posicaoTiro<1 || posicaoTiro>tabuleiroPalpites.size){
        return false
    }
    return true
}

fun lancarTiroPossiblePC(tabuleiroPalpites: Array<Char?>, posicaoTiro: Int): Boolean {
    if(posicaoTiro<1 || posicaoTiro>tabuleiroPalpites.size || (tabuleiroPalpites[posicaoTiro-1] != '?' && tabuleiroPalpites[posicaoTiro-1] != null)){
        return false
    }
    return true
}

fun geraJogadaComputador(tabuleiroPalpites: Array<Char?>): Int {
    var valido = false
    var valorValido = 0
    do {
        val jogada = Random().nextInt(tabuleiroPalpites.size - 1)
        if(lancarTiroPossiblePC(tabuleiroPalpites,jogada)){
            valido=true
            valorValido = jogada
        }
    } while (!valido)
    return valorValido
}

fun afundado(tabuleiroReal: Array<Char?>, tabuleiroPalpites: Array<Char?>, posicaoTiro: Int): String{
    val afunda = " Navio ao fundo!"
    if(posicaoTiro == -1) return ""
    if(posicaoTiro<1 || posicaoTiro>tabuleiroPalpites.size) return ""
    when (tabuleiroReal[posicaoTiro-1]) {
        '3' -> {
            if(posicaoTiro-1>=2&&posicaoTiro-1<=tabuleiroPalpites.size-3){
                if(tabuleiroPalpites[posicaoTiro-1] == '3' && tabuleiroPalpites[posicaoTiro-2]=='3' && tabuleiroPalpites[posicaoTiro-3]=='3'){
                    return afunda
                }
            }
            if(posicaoTiro-1>1&&posicaoTiro-1<=tabuleiroPalpites.size-3){
                if(tabuleiroPalpites[posicaoTiro-1]=='3' && tabuleiroPalpites[posicaoTiro-2]=='3'&&tabuleiroPalpites[posicaoTiro]=='3'){
                    return afunda
                }
            }
            if(posicaoTiro-1>=0&&posicaoTiro-1<tabuleiroPalpites.size-3){
                if(tabuleiroPalpites[posicaoTiro-1]=='3'&&tabuleiroPalpites[posicaoTiro]=='3'&&tabuleiroPalpites[posicaoTiro+1]=='3'){
                    return afunda
                }
            }
            if(posicaoTiro-1>=0&&posicaoTiro-1>=tabuleiroPalpites.size-3){
                if(tabuleiroPalpites[tabuleiroPalpites.size-2]=='3' &&tabuleiroPalpites[tabuleiroPalpites.size-3]=='3'&&tabuleiroPalpites[tabuleiroPalpites.size-1]=='3'){
                    return afunda
                }
                if(tabuleiroPalpites[tabuleiroPalpites.size-2]=='3' &&tabuleiroPalpites[tabuleiroPalpites.size-3]=='3'&&tabuleiroPalpites[tabuleiroPalpites.size-4]=='3'){
                    return afunda
                }
            }
            return ""
        }
        '2' -> {
            if(posicaoTiro-1>=1 && posicaoTiro-1<=tabuleiroPalpites.size-2){
                if(tabuleiroPalpites[posicaoTiro-1]=='2'&&tabuleiroPalpites[posicaoTiro]=='2'){
                    return afunda
                }
                if(tabuleiroPalpites[posicaoTiro-1]=='2'&&tabuleiroPalpites[posicaoTiro-2]=='2'){
                    return afunda
                }
            }
            if(posicaoTiro-1>=0 && posicaoTiro-1<=tabuleiroPalpites.size-2){
                if(tabuleiroPalpites[posicaoTiro-1]=='2'&&tabuleiroPalpites[posicaoTiro]=='2'){
                    return afunda
                }
            }
            if(posicaoTiro-1>=0 && posicaoTiro-1>=tabuleiroPalpites.size-2){
                if(tabuleiroPalpites[posicaoTiro-1]=='2'&&tabuleiroPalpites[posicaoTiro-2]=='2'){
                    return afunda
                }
            }
            return ""

        }
        '1' -> {
            return " Navio ao fundo!"
        }
        else -> {
            return ""
        }
    }
}

fun venceu(tabuleiroPalpites: Array<Char?>): Boolean {
    val navios = calculaNumNavios(tabuleiroPalpites.size)
    var numeroTanquesDivididos = 0
    var numeroTorpedeirosDivididos = 0
    var numeroSubmarinosDivididos = 0
    var tanquesOk = false
    var torpsOk = false
    var subsOk = false
    for (i in tabuleiroPalpites){
        if(i=='3') numeroTanquesDivididos++
        if(i=='2') numeroTorpedeirosDivididos++
        if(i=='1') numeroSubmarinosDivididos++
    }
    if((navios[0]==1 && numeroTanquesDivididos>=3)||(navios[0]==2 && numeroTanquesDivididos>=6)){
        tanquesOk=true
    }
    if((navios[1]==1 && numeroTorpedeirosDivididos>=2)||(navios[1]==2 && numeroTorpedeirosDivididos>=4)){
        torpsOk=true
    }
    else if((navios[1]==3 && numeroTorpedeirosDivididos>=6)){
        torpsOk = true
    }
    if((navios[2]==1 && numeroSubmarinosDivididos>=1)||(navios[2]==3 && numeroSubmarinosDivididos>=3)){
        subsOk = true
    }
    else if((navios[2]==5 && numeroSubmarinosDivididos>=5)||(navios[2]==6 && numeroSubmarinosDivididos>=6)){
        subsOk = true
    }
    return (tanquesOk&&torpsOk&&subsOk)
} //esta tem que ser mudada

fun leMapaDoFicheiro(nomeFicheiro: String, tipoMapa: Int): Array<Char?>{
    try {
        val lines = File(nomeFicheiro).readLines()
        if(tipoMapa==1){
            val arrayReturn=stringToArray(lines[2])
            convertMaps(arrayReturn)
            return arrayReturn
        }
        else if(tipoMapa==2){
            val arrayReturn=stringToArray(lines[4])
            convertMaps(arrayReturn)
            return arrayReturn
        }
        else if(tipoMapa==3){
            val arrayReturn=stringToArray(lines[8])
            convertMaps(arrayReturn)
            return arrayReturn
        }
        else if(tipoMapa==4){
            val arrayReturn=stringToArray(lines[10])
            convertMaps(arrayReturn)
            return arrayReturn
        }
        else{
            return emptyArray()
        }
    } catch (e: FileNotFoundException) {
        return emptyArray()
    }
}

fun leNomeJogadorDoFicheiro(nomeFicheiro: String): String{
    try {
        val lines = File(nomeFicheiro).readLines()
        return lines[0]

    } catch (e: FileNotFoundException) {
        return ""
    }
}

fun gravaJogoEmFicheiro(nomeFicheiro: String, nomeJogador: String, tabuleiroRealHumano: Array<Char?>, tabuleiroPalpitesHumano: Array<Char?>, tabuleiroRealComputador: Array<Char?>, tabuleiroPalpitesComputador: Array<Char?>){
    var tabuleiroRealHumanoString = ""
    var tabuleiroPalpitesHumanoString = ""
    var tabuleiroRealComputadorString = ""
    var tabuleiroPalpitesComputadorString = ""
    convertMaps(tabuleiroPalpitesHumano)
    convertMaps(tabuleiroPalpitesComputador)
    for(i in tabuleiroRealHumano){
        if(i == null){
            tabuleiroRealHumanoString = "$tabuleiroRealHumanoString~"
        }
        else{
            tabuleiroRealHumanoString = "$tabuleiroRealHumanoString$i"
        }
    }
    for(i in tabuleiroPalpitesHumano){
        if(i == null){
            tabuleiroPalpitesHumanoString = "$tabuleiroPalpitesHumanoString?"
        }
        else{
            tabuleiroPalpitesHumanoString = "$tabuleiroPalpitesHumanoString$i"
        }
    }
    for(i in tabuleiroRealComputador){
        if(i == null){
            tabuleiroRealComputadorString = "$tabuleiroRealComputadorString~"
        }
        else{
            tabuleiroRealComputadorString = "$tabuleiroRealComputadorString$i"
        }
    }
    for(i in tabuleiroPalpitesComputador){
        if(i == null){
            tabuleiroPalpitesComputadorString = "$tabuleiroPalpitesComputadorString?"
        }
        else{
            tabuleiroPalpitesComputadorString = "$tabuleiroPalpitesComputadorString$i"
        }
    }
    val arrayEscrita = arrayOf(nomeJogador,"Real",tabuleiroRealHumanoString,"Palpites",tabuleiroPalpitesHumanoString,"","Computador","Real",tabuleiroRealComputadorString,"Palpites",tabuleiroPalpitesComputadorString)
    val writer =  File(nomeFicheiro).printWriter()
    for (linha in arrayEscrita){
        writer.println(linha)
    }
    writer.close()
}

fun calculaEstatisticas(tabuleiroPalpites: Array<Char?>): Array<Int>{
    var tanquesAfundados=0
    var torpedeirosAfundados=0
    var submarinosAfundados=0
    var jogadas=0
    var tirosAcertados=0
    for (i in tabuleiroPalpites){
        if(i=='1'||i=='2'||i=='3'||i=='X'||i=='\u2083'||i=='\u2082'){ //i=='1'||i=='2'||i=='3'||i=='X'||i=='\u2083'||i=='\u2082'
            jogadas++
            if(i=='1'||i=='2'||i=='3'||i=='\u2083'||i=='\u2082'){
                tirosAcertados++
            }
        }
        if(i=='1'){
            submarinosAfundados++
        }
    }
    for(i in 0..tabuleiroPalpites.size-3){
        if((tabuleiroPalpites[i]=='3'&&tabuleiroPalpites[i+1]=='3'&&tabuleiroPalpites[i+2]=='3')||(tabuleiroPalpites[i]=='\u2083'&&tabuleiroPalpites[i+1]=='\u2083'&&tabuleiroPalpites[i+2]=='\u2083')){
            tanquesAfundados++
        }
    }
    for(i in 0..tabuleiroPalpites.size-2){
        if((tabuleiroPalpites[i]=='2'&&tabuleiroPalpites[i+1]=='2')||(tabuleiroPalpites[i]=='\u2082'&&tabuleiroPalpites[i+1]=='\u2082')){
            torpedeirosAfundados++
        }
    }
    val precisao=(tirosAcertados.toDouble()/jogadas)*100
    return arrayOf(tanquesAfundados,torpedeirosAfundados,submarinosAfundados,jogadas,precisao.toInt())
}

fun main(args: Array<String>) {
    var sair = false //var
    var ranOnce = false
    var gameFinished = false
    var tabuleiroJogo = arrayOf<Char?>(' ') //valor ao calhas
    var tabuleiroComputador = arrayOf<Char?>(' ') //valor ao calhas
    var tabuleiroGuess = arrayOf<Char?>(' ') //same
    var tamanho = 0
    var tabuleiroTirosPC = arrayOf<Char?>(' ') //same
    var segundaOpcao = "Iniciar"
    val tanque = '3' //val
    val torpedeiro = '2' //val
    val submarino = '1' //val
    var nome: String? = ""//var
    var tanques = 0
    var torpedeiros = 0
    var submarinos = 0
    while (!sair) {
        println()
        println("*** BATALHA NAVAL ***")
        println()
        println("1 - Definir mapa Humano vs Computador")
        println("2 - $segundaOpcao jogo")
        println("3 - Gravar ficheiro")
        println("4 - Ler ficheiro")
        println("5 - Estatisticas")
        println("0 - Sair")
        println()
        var escolhido: Int? = readLine()!!.toIntOrNull()
        while (escolhido == null || escolhido < -1 || escolhido > 5 || ((escolhido == 2 || escolhido ==3 || escolhido==5)&&!(ranOnce||gameFinished))) {
            if(escolhido == 2 || escolhido ==3){ println(">>> Mapas dos jogadores ainda nao definidos, tente novamente")}
            else if(escolhido==5){ println(">>> Jogo nao iniciado, tente novamente")}
            else{ println(">>> Dados de entrada invalidos, tente novamente")}
            escolhido = readLine()!!.toIntOrNull()
        }
        if (escolhido == 0){ sair = true}
        else if (escolhido == -1){ sair = false}
        else if (escolhido == 1) {
            segundaOpcao = "Iniciar"
            sair = false
            println()
            println("*** BATALHA NAVAL ***")
            println()
            println("Escolha o tamanho do tabuleiro:")
            var leitura: String? = readLine()
            while (((leitura?.toIntOrNull() == null) && leitura!!.toIntOrNull() != -1) || ((leitura.toInt() < 8 || leitura.toInt() > 99) && leitura.toIntOrNull() != -1)) {
                if (leitura.toIntOrNull() == null) {
                    println(">>> Dados de entrada invalidos, tente novamente")
                    leitura = readLine()
                } else if ((leitura.toInt() < 8 || leitura.toInt() > 99) && leitura.toIntOrNull() != -1) {
                    println(">>> Tamanho de tabuleiro invalido, tente novamente")
                    leitura = readLine()
                } else {
                    println(">>> Dados de entrada invalidos, tente novamente")
                    leitura = readLine()
                }
            }
            if (leitura.toIntOrNull() == -1) {
                sair = false
            } else {
                tamanho = leitura.toInt()
                val arrayNavios = calculaNumNavios(tamanho)
                tanques = arrayNavios[0]
                torpedeiros = arrayNavios[1]
                submarinos = arrayNavios[2]
                tabuleiroJogo = arrayOfNulls<Char?>(tamanho)
                //println(insereNavio(tabuleiroJogo,1,3))
                println("Introduza o nome do jogador(a):")
                nome = readLine().orEmpty()
                var nomeJogador = processaNomeJogador(nome)
                while (nomeJogador == null && nome != "-1") {
                    println(">>> Nome invalido, tente novamente")
                    nome = readLine().orEmpty()
                    nomeJogador = processaNomeJogador(nome)
                }
                if (nome != "-1") {
                    println()
                    println("*** BATALHA NAVAL ***")
                    println()
                    println("Mapa do $nomeJogador:")
                    var count = 0
                    var tabuleiroDesenhado = obtemMapa(tabuleiroJogo, true)
                    if (tamanho >= 10) {
                        for (i in 0..2) {
                            println(tabuleiroDesenhado[i])
                        }
                    } else if (tamanho < 10) {
                        for (i in 0..1) {
                            println(tabuleiroDesenhado[i])
                        }
                    }
                    //navios do jogador
                    var tanquepos: Int
                    var torpedeiropos: Int
                    var counterNavios = 1
                    while (counterNavios in 1..tanques) {
                        println()
                        println("$nomeJogador, introduz a posicao para 1 navio-tanque:")
                        leitura = readLine()
                        while (leitura?.toIntOrNull() == null || (!insereNavio(tabuleiroJogo, leitura.toInt(), 3) && leitura.toInt() != -1)) {
                            if (leitura?.toIntOrNull() == null) {
                                println(">>> Dados de entrada invalidos, tente novamente")
                                leitura = readLine()
                            } else if (!insereNavio(tabuleiroJogo, leitura.toInt(), 3)) {
                                println(">>> Posicao do navio invalida, tente novamente")
                                leitura = readLine()
                            } else { //caso me tenha esquecido de alguma condição, faz um fallback para aqui. Reza para que não atrofie dados honestos
                                println(">>> Dados de entrada invalidos, tente novamente")
                                leitura = readLine()
                            }
                        }
                        if (leitura.toInt()==-1) {counterNavios = tanques}
                        if (leitura.toInt() != -1) {
                            println()
                            println("Mapa do $nomeJogador:")
                            tanquepos = leitura.toInt()
                            tanquepos--
                            while (count <= tamanho) {
                                if (count == tanquepos || count == tanquepos + 1 || count == tanquepos + 2) {
                                    tabuleiroJogo[count] = tanque
                                }
                                count++
                            }
                            tabuleiroDesenhado = obtemMapa(tabuleiroJogo, true)
                            if (tamanho >= 10) {
                                for (i in 0..2) {
                                    println(tabuleiroDesenhado[i])
                                }
                            } else if (tamanho < 10) {
                                for (i in 0..1) {
                                    println(tabuleiroDesenhado[i])
                                }
                            }
                            count = 0
                        }
                        counterNavios++
                    }
                    if (leitura?.toInt() != -1) {
                        counterNavios=1
                        while (counterNavios in 1..torpedeiros) {
                            println()
                            println("$nomeJogador, introduz a posicao para 1 contratorpedeiro:")
                            leitura = readLine()
                            while (leitura?.toIntOrNull() == null || (!insereNavio(tabuleiroJogo, leitura.toInt(), 2) && leitura.toInt() != -1)) {
                                if (leitura?.toIntOrNull() == null) {
                                    println(">>> Dados de entrada invalidos, tente novamente")
                                    leitura = readLine()
                                } else if (!insereNavio(tabuleiroJogo, leitura.toInt(), 2)) {
                                    println(">>> Posicao do navio invalida, tente novamente")
                                    leitura = readLine()
                                } else {
                                    println(">>> Dados de entrada invalidos, tente novamente")
                                    leitura = readLine()
                                }
                            }
                            if (leitura.toInt() == -1){counterNavios = torpedeiros}
                            if (leitura.toInt() != -1) {
                                println()
                                println("Mapa do $nomeJogador:")
                                torpedeiropos = leitura.toInt()
                                torpedeiropos--
                                while (count <= tamanho) {
                                    if (count == torpedeiropos || count == torpedeiropos + 1) {
                                        tabuleiroJogo[count] = torpedeiro
                                    }
                                    count++
                                }
                                tabuleiroDesenhado = obtemMapa(tabuleiroJogo, true)
                                if (tamanho >= 10) {
                                    for (i in 0..2) {
                                        println(tabuleiroDesenhado[i])
                                    }
                                } else if (tamanho < 10) {
                                    for (i in 0..1) {
                                        println(tabuleiroDesenhado[i])
                                    }
                                }
                                count = 0
                            }
                            counterNavios++
                        }
                        if (leitura?.toInt() != -1) {
                            counterNavios = 1
                            while (counterNavios in 1..submarinos) {
                                println()
                                println("$nomeJogador, introduz a posicao para 1 submarino:")
                                leitura = readLine()
                                while (leitura?.toIntOrNull() == null || (!insereNavio(tabuleiroJogo, leitura.toInt(), 1) && leitura.toInt() != -1)) {
                                    if (leitura?.toIntOrNull() == null) {
                                        println(">>> Dados de entrada invalidos, tente novamente")
                                        leitura = readLine()
                                    } else if (!insereNavio(tabuleiroJogo, leitura.toInt(), 1)) {
                                        println(">>> Posicao do navio invalida, tente novamente")
                                        leitura = readLine()
                                    } else {
                                        println(">>> Dados de entrada invalidos, tente novamente")
                                        leitura = readLine()
                                    }
                                }
                                if (leitura.toInt() == -1){ counterNavios = submarinos}
                                if (leitura.toInt() != -1) {
                                    println()
                                    println("Mapa do $nomeJogador:")
                                    var submarinopos = leitura.toInt()
                                    submarinopos--
                                    while (count <= tamanho) {
                                        if (count == submarinopos) {
                                            tabuleiroJogo[count] = submarino
                                        }
                                        count++
                                    }
                                    tabuleiroDesenhado = obtemMapa(tabuleiroJogo, true)
                                    if (tamanho >= 10) {
                                        for (i in 0..2) {
                                            println(tabuleiroDesenhado[i])
                                        }
                                    } else if (tamanho < 10) {
                                        for (i in 0..1) {
                                            println(tabuleiroDesenhado[i])
                                        }
                                    }
                                }
                                count = 1
                                counterNavios++
                            }
                            if (leitura?.toInt() != -1) {
                                tabuleiroComputador = arrayOfNulls<Char?>(tamanho)
                                geraMapaComputador(tabuleiroComputador, tanques, torpedeiros, submarinos)
                                val tabuleiroDesenhadoComputador = obtemMapa(tabuleiroComputador, true)
                                println()
                                println("*** BATALHA NAVAL ***")
                                println()
                                println("Pretende ver o mapa gerado para o Computador? (S/N)")
                                ranOnce=true
                                leitura = readLine()
                                while (leitura.isNullOrEmpty() || (!(leitura == "S") && !(leitura == "N") && !(leitura == "-1"))) {
                                    println(">>> Dados de entrada invalidos, tente novamente")
                                    leitura = readLine()
                                }
                                if (leitura == "-1") {
                                    sair = false
                                } else {
                                    val escolha: Char? = leitura[0]
                                    if (escolha == 'S') {
                                        println("Mapa do Computador:")
                                        if (tamanho >= 10) {
                                            for (i in 0..2) {
                                                println(tabuleiroDesenhadoComputador[i])
                                            }
                                        } else if (tamanho < 10) {
                                            for (i in 0..1) {
                                                println(tabuleiroDesenhadoComputador[i])
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tabuleiroGuess = arrayOfNulls<Char?>(tamanho)
            tabuleiroTirosPC = arrayOfNulls<Char?>(tamanho)
        }
        else if (escolhido == 2 && ranOnce) {
            var playerWon = false
            var computerWon = false
            var exit = false
            segundaOpcao = "Retomar"
            do{
                val tabuleiroDesenhadoComputador = obtemMapa(tabuleiroGuess,false)
                println()
                println("Mapa do computador:")
                if (tamanho >= 10) {
                    for (i in 0..2) {
                        println(tabuleiroDesenhadoComputador[i])
                    }
                } else if (tamanho < 10) {
                    for (i in 0..1) {
                        println(tabuleiroDesenhadoComputador[i])
                    }
                }
                println("$nome, indique a posicao onde pretende atirar")
                var leitura = readLine()
                while(leitura == null || leitura.toIntOrNull() == null || (!lancarTiroPossible(tabuleiroGuess,leitura.toInt()) && leitura.toInt() != -1)){
                    println(">>> Posicao invalida. Tente novamente")
                    leitura = readLine()
                }
                if(leitura.toInt() == -1){
                    exit = true
                }
                else{
                    print(">>> ${lancarTiro(tabuleiroComputador,tabuleiroGuess,leitura.toInt())}")
                    obtemMapa(tabuleiroGuess,false)
                    print(afundado(tabuleiroComputador,tabuleiroGuess,leitura.toInt()))
                    println()
                    if(venceu(tabuleiroGuess)){
                        playerWon=true
                    }
                    else{
                        var tiroPC = geraJogadaComputador(tabuleiroTirosPC)
                        tiroPC++
                        println()
                        println("Computador lancou tiro para a posicao $tiroPC")
                        print(">>> ${lancarTiro(tabuleiroJogo,tabuleiroTirosPC,tiroPC)}")
                        obtemMapa(tabuleiroTirosPC,false)
                        print(afundado(tabuleiroJogo,tabuleiroTirosPC,tiroPC))
                        println()
                        if(venceu(tabuleiroTirosPC)) computerWon = true
                        println("Prima enter para continuar")
                        leitura = readLine()
                        if(leitura == "-1"){
                            exit = true
                        }
                    }
                }
            } while(!playerWon && !computerWon && !exit)
            if(playerWon){
                println("PARABENS! $nome venceu o jogo!")
                ranOnce = false
                gameFinished=true
                segundaOpcao = "Iniciar"
                println("Prima enter para voltar ao menu principal")
                readLine()
            }
            if(computerWon){
                println("OPS! O computador venceu o jogo!")
                ranOnce = false
                gameFinished=true
                segundaOpcao = "Iniciar"
                println("Prima enter para voltar ao menu principal")
                readLine()
                println()
            }
        }
        else if (escolhido == 3 && ranOnce) {
            println()
            println("*** BATALHA NAVAL ***")
            println()
            println("Indique o nome do ficheiro onde pretende gravar")
            var ficheiroParaGravar = readLine().toString()
            while(ficheiroParaGravar == ""){
                println(">>>Dados inválidos, tente novamente")
                ficheiroParaGravar = readLine().toString()
            }
            gravaJogoEmFicheiro(ficheiroParaGravar,nome.toString(),tabuleiroJogo,tabuleiroGuess,tabuleiroComputador,tabuleiroTirosPC)
            println(">>> Ficheiro gravado com sucesso. Prima enter para voltar ao menu.")
            readLine()
        }
        else if (escolhido == 4) {
            println()
            println("*** BATALHA NAVAL ***")
            println()
            println("Indique o nome do ficheiro que pretende ler")
            val ficheiroLido = readLine().toString()
            val boolNome = processaNomeJogador(leNomeJogadorDoFicheiro(ficheiroLido)) == null
            val boolTabuleiroJogo = leMapaDoFicheiro(ficheiroLido,1).size<8
            val boolTabuleiroGuess = leMapaDoFicheiro(ficheiroLido,2).size<8
            val boolTabuleiroComputador = leMapaDoFicheiro(ficheiroLido,3).size<8
            val boolTabuleiroTirosPC = leMapaDoFicheiro(ficheiroLido,4).size<8
            if(boolNome||boolTabuleiroJogo||boolTabuleiroComputador||boolTabuleiroGuess||boolTabuleiroTirosPC){
                println(">>> Ocorreu um erro na leitura do ficheiro. Prima enter para voltar ao menu.")
                readLine()
            }
            else{ //substituir por else
                nome = leNomeJogadorDoFicheiro(ficheiroLido)
                tabuleiroJogo=leMapaDoFicheiro(ficheiroLido,1)
                tabuleiroGuess=leMapaDoFicheiro(ficheiroLido,2)
                tabuleiroComputador=leMapaDoFicheiro(ficheiroLido,3)
                tabuleiroTirosPC=leMapaDoFicheiro(ficheiroLido,4)
                obtemMapa(tabuleiroJogo,true)
                obtemMapa(tabuleiroGuess,false)
                obtemMapa(tabuleiroComputador,true)
                obtemMapa(tabuleiroTirosPC,false)
                tamanho=tabuleiroJogo.size
                val jogadas = calculaEstatisticas(tabuleiroGuess)[3]
                if(jogadas==0){
                    segundaOpcao = "Iniciar"
                }
                if(jogadas>0){
                    segundaOpcao = "Retomar"
                }
                ranOnce = true
                println(">>> Ficheiro lido com sucesso. Tamanho do tabuleiro: 10. Prima enter para voltar ao menu.")
                readLine()

            }
        }
        else if (escolhido == 5 && (ranOnce||gameFinished)) {
            obtemMapa(tabuleiroGuess,false) //refresh aos mapas, às vezes fica bugado, ver antes de entregar
            obtemMapa(tabuleiroTirosPC,false)
            val arrayEstatisticasJogador=calculaEstatisticas(tabuleiroGuess)
            val arrayEstatisticasPC=calculaEstatisticas(tabuleiroTirosPC)
            println()
            println("*** BATALHA NAVAL ***")
            println()
            println("Tamanho do tabuleiro: $tamanho")
            println()
            println(nome)
            println("Numero de jogadas: ${arrayEstatisticasJogador[3]}")
            println("Navios-tanque:     ${arrayEstatisticasJogador[0]} de $tanques")
            println("Contratorpedeiros: ${arrayEstatisticasJogador[1]} de $torpedeiros")
            println("Submarinos:        ${arrayEstatisticasJogador[2]} de $submarinos")
            println("Precisao tiro:     ${arrayEstatisticasJogador[4]}%")
            println()
            println("Computador")
            println("Numero de jogadas: ${arrayEstatisticasPC[3]}")
            println("Navios-tanque:     ${arrayEstatisticasPC[0]} de $tanques")
            println("Contratorpedeiros: ${arrayEstatisticasPC[1]} de $torpedeiros")
            println("Submarinos:        ${arrayEstatisticasPC[2]} de $submarinos")
            println("Precisao tiro:     ${arrayEstatisticasPC[4]}%")
            println()
            println("Prima enter para voltar ao menu principal")
            readLine()
        }
    }
}