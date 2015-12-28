import java.security.MessageDigest

fun strToBytes(s : String) : ByteArray {
    val result = ByteArray(size=s.length)
    var i = 0
    for (c in s) {
        result[i++] = c.toByte()
    }
    return result
}

val hexValues = "0123456789abcdef"
fun bytesToStr(bytes : ByteArray) : String {
    var result = ""
    for (b in bytes) {
        val value = b.toInt()
        val first = (value and 0xf0) shr 4
        val second = value and 0xf
        result += hexValues[first].toString() + hexValues[second].toString()
    }
    return result
}

fun isDigestGood(digest: ByteArray) : Boolean {
    if (digest.size < 3) {
        return false
    }

    return digest[0].toInt() == 0 && digest[1].toInt() == 0 && ((digest[2].toInt() and 0xf0) == 0)
}

fun getDigest(msg : String) : ByteArray {
    println(msg)
    val d = MessageDigest.getInstance("MD5")
    d.reset()
    d.update(strToBytes(msg))
    return d.digest()
}

fun main(args: Array<String>) {
    val msg = "bgvyzdsv"
    for (i in 1..100000000) {
        val digest = getDigest(msg + i)
        if (isDigestGood(digest)) {
            println("FOUND ONE!")
            println(i)
            break
        }
    }
}
