package pl.edu.pwr.wojciech.okonski.lab3.lab3

fun toTrue(func: () -> Unit): Boolean {
    func()
    return true
}