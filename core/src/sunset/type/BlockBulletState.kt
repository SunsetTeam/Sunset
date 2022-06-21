package sunset.type

@Suppress("EnumEntryName")
enum class BlockBulletState {
    waiting,//first time after attack
    ready,//ready for attack
    waitingForExitPosition,//moving to launch position
    attack //attack
}