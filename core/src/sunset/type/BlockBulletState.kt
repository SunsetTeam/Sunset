package sunset.type

@Suppress("EnumEntryName")
enum class BlockBulletState {
    reloading,//first time after attack
    ready,//ready for attack
    waitingForExitPoint,//moving to launch position
    attack, //attack
    backHome;

    fun next() = all[(ordinal + 1) % all.size];
    fun previous() = all[(ordinal - 1 + all.size) % all.size];


    /*fun waiting() = this == waiting
    fun ready() = this == ready
    fun waitingForExitPosition() = this == waitingForExitPosition
    fun attack() = this == attack
    fun afterAttack() = this == afterAttack*/

    companion object {
        @JvmField
        val all = values();
    }
}