package sunset.type

object BlockSide {
    const val right=0;
    const val top=1;
    const val left=2;
    const val bottom=3;
    @JvmStatic
    fun sideForAngle(angle:Float)= ((angle + 45 + 360f) % 360f / 90).toInt();
}