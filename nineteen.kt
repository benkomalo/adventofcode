import kotlin.text.Regex

val rules = arrayListOf(
    "Al" to "ThF",
    "Al" to "ThRnFAr",
    "B" to "BCa",
    "B" to "TiB",
    "B" to "TiRnFAr",
    "Ca" to "CaCa",
    "Ca" to "PB",
    "Ca" to "PRnFAr",
    "Ca" to "SiRnFYFAr",
    "Ca" to "SiRnMgAr",
    "Ca" to "SiTh",
    "F" to "CaF",
    "F" to "PMg",
    "F" to "SiAl",
    "H" to "CRnAlAr",
    "H" to "CRnFYFYFAr",
    "H" to "CRnFYMgAr",
    "H" to "CRnMgYFAr",
    "H" to "HCa",
    "H" to "NRnFYFAr",
    "H" to "NRnMgAr",
    "H" to "NTh",
    "H" to "OB",
    "H" to "ORnFAr",
    "Mg" to "BF",
    "Mg" to "TiMg",
    "N" to "CRnFAr",
    "N" to "HSi",
    "O" to "CRnFYFAr",
    "O" to "CRnMgAr",
    "O" to "HP",
    "O" to "NRnFAr",
    "O" to "OTi",
    "P" to "CaP",
    "P" to "PTi",
    "P" to "SiRnFAr",
    "Si" to "CaSi",
    "Th" to "ThCa",
    "Ti" to "BP",
    "Ti" to "TiTi",
    "e" to "HF",
    "e" to "NAl",
    "e" to "OMg"
)

//val rules = arrayListOf(
//    "H" to "HO",
//    "H" to "OH",
//    "O" to "HH"
//)

val molecule = "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"
//val molecule = "HOH"

fun main(args: Array<String>) {
    val results = hashSetOf<String>()

    for (rule in rules) {
        val from = Regex(rule.first)
        val to = rule.second
//        println("applying ${rule.first} to ${to}")

        for (match in from.findAll(molecule)) {
//            println("Match at ${match.range.start}")
            val start = match.range.start
            val end = match.range.endInclusive + 1
            val mutated = molecule.substring(0, start) + to + molecule.substring(end)
            results.add(mutated)
        }
    }

    println(results)
    println("There are ${results.size} distinct ones")
}